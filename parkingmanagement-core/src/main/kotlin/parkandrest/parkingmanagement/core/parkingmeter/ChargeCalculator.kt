package parkandrest.parkingmanagement.core.parkingmeter


import org.springframework.stereotype.Component
import parkandrest.parkingmanagement.api.parking.account.CalculatedChargeDto
import parkandrest.parkingmanagement.core.monetary.Monetary
import parkandrest.parkingmanagement.core.parking.account.CalculatedCharge
import parkandrest.parkingmanagement.core.parking.account.CalculatedChargeRepository
import parkandrest.parkingmanagement.core.tariff.CalculationContext
import parkandrest.parkingmanagement.core.tariff.Tariff
import parkandrest.parkingmanagement.core.vehicle.VehicleRegistry
import parkandrest.timemanagement.SystemTimeManager
import java.util.*

/**
 * Calculates charge for parking
 * Public because of Spring (otherwise framework will use reflection), should be package private
 * Is not transactional because it should be inner service called from transactional service
 */
@Component
class ChargeCalculator(private val calculatedChargeRepository: CalculatedChargeRepository) {

    /**
     * Precalculates charge for customer to see
     * @param vehicle customers vehicle registry
     * @param calcQuantity quantity of calculations ( remembered in Tariff)
     * @return calculated charges list of size = calcQuantity (parkandrest.parkingmanagement.api.parking.account.CalculatedChargeDto)
     */
    fun preCalculate(vehicle: VehicleRegistry, calcQuantity: Long): List<CalculatedChargeDto> {
        val calculations = ArrayList<CalculatedChargeDto>()
        for (i in 1..calcQuantity) {
            val charge = calculateMonetaryCharge(vehicle.selectedTariff, i)
            calculations.add(
                    CalculatedChargeDto(
                            vehicle.registration,
                            charge.amount,
                            charge.currency.name,
                            SystemTimeManager.systemDateTime,
                            i,
                            vehicle.selectedTariff.period.name
                    ))
        }
        return calculations
    }

    /**
     * Calculates charge for custome and saves the result to db
     * @param vehicle customers vehicle registry
     * @return CalculatedCharge
     */
    fun calculateAndSave(vehicle: VehicleRegistry): CalculatedCharge {
        val tariff = vehicle.selectedTariff
        return calculatedChargeRepository.save(CalculatedCharge(
                vehicle,
                calculateMonetaryCharge(
                        tariff,
                        tariff.period.toPeriodQuantity(vehicle.parkingDuration)),
                vehicle.selectedTariff,
                SystemTimeManager.systemDateTime
        ))
    }

    private fun calculateMonetaryCharge(tariff: Tariff, periods: Long): Monetary {
        var result = Monetary.zero(tariff.tariffCurrency)
        val context = CalculationContext()
        for (i in 1..periods) {
            result = result.add(
                    tariff
                            .tarifficationRulesCollection
                            .getRuleByOrder(i)
                            .formula()
                            .calculate(context)
            )
        }
        return result.roundToMoneyPrecision()
    }
}
