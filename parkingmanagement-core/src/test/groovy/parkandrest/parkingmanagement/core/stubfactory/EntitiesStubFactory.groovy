package parkandrest.parkingmanagement.core.stubfactory

import com.google.common.collect.Sets
import parkandrest.parkingmanagement.core.monetary.Monetary
import parkandrest.parkingmanagement.core.parking.account.CalculatedCharge
import parkandrest.parkingmanagement.core.parking.account.Payment
import parkandrest.parkingmanagement.core.parking.space.ParkingSpaceStatus
import parkandrest.parkingmanagement.core.tariff.RuleOrder
import parkandrest.parkingmanagement.core.tariff.TarifficationRule
import parkandrest.parkingmanagement.core.monetary.Currency
import parkandrest.parkingmanagement.core.parking.Parking
import parkandrest.parkingmanagement.core.parking.account.ParkingAccount
import parkandrest.parkingmanagement.core.parking.space.ParkingSpace
import parkandrest.parkingmanagement.core.tariff.Period
import parkandrest.parkingmanagement.core.tariff.Tariff
import parkandrest.parkingmanagement.core.tariff.TariffType
import parkandrest.parkingmanagement.core.vehicle.VehicleRegistry

import java.math.RoundingMode
import java.time.LocalDateTime

class EntitiesStubFactory {

    /**
     * Creates Parking with three parking spaces (FREE, OCCUPIED, MAINTENANCE)
     * @return Parking
     */
    static Parking parking() {
        def parking = new Parking(
                "name",
                "address",
                new HashSet<>(),
                new HashSet<>(),
                new ParkingAccount(
                        "iban"
                )
        )
        parking.addParkingSpace(
                new ParkingSpace(ParkingSpaceStatus.FREE, parking),
                new ParkingSpace(ParkingSpaceStatus.OCCUPIED, parking),
                new ParkingSpace(ParkingSpaceStatus.MAINTENANCE, parking)
        )
        return parking
    }

    /**
     * Creates Parking with three parking spaces (FREE, OCCUPIED, MAINTENANCE) and regular and VIP Tariff
     * @return Parking
     */
    static Parking parkingWithAllTariffs(){
        def parking = parking()
        parking.tariffsCollection.addTariff(
                regularTariffWithAllRules(parking),
                vipTariffWithAllRules(parking)
        )
        return parking
    }

    /**
     * Creates Tariff for a regular driver with HOUR periods (start amount is 1 PLN)
     * @param parking Parking
     * @return Tariff
     */
    static Tariff regularTariff(Parking parking) {
        new Tariff(
                parking,
                Period.HOUR,
                new Monetary(
                        new BigDecimal(1).setScale(2, RoundingMode.HALF_DOWN),
                        Currency.PLN
                ),
                Sets.newHashSet(),
                4L,
                TariffType.REGULAR,
        )
    }

    /**
     * Creates Tariff for a regular driver with HOUR periods (start amount is 1 PLN) and all three tariffication rules
     * @param parking Parking
     * @return Tariff
     */
    static Tariff regularTariffWithAllRules(Parking parking){
        def tariff = regularTariff(parking)
        tariff.tarifficationRulesCollection.addTarifficationRule(
                firstTarifficationRule(tariff),
                secondRegularTarifficationRule(tariff),
                recursiveRegularTarifficationRule(tariff)
        )
        return tariff
    }

    /**
     * Creates Tariff for a VIP driver with HOUR periods (start amount is 0 PLN)
     * @param parking Parking
     * @return Tariff
     */
    static Tariff vipTariff(Parking parking) {
        new Tariff(
                parking,
                Period.HOUR,
                new Monetary(
                        BigDecimal.ZERO,
                        Currency.PLN
                ),
                Sets.newHashSet(

                ),
                5L,
                TariffType.VIP
        )
    }

    /**
     * Creates Tariff for a VIP driver with HOUR periods (start amount is 0 PLN) and all three tariffication rules
     * @param parking Parking
     * @return Tariff
     */
    static Tariff vipTariffWithAllRules(Parking parking){
        def tariff = vipTariff(parking)
        tariff.tarifficationRulesCollection.addTarifficationRule(
                firstTarifficationRule(tariff),
                secondVipTarifficationRule(tariff),
                recursiveVipTarifficationRule(tariff)
        )
        return tariff
    }

    /**
     * Creates a VehicleRegistry about parked six hours ago vehicle
     * @param parking Parking
     * @param tariff Tariff
     * @return VehicleRegistry
     */
    static VehicleRegistry vehicleRegistry(Parking parking, Tariff tariff){
        return new VehicleRegistry(
                new ParkingSpace(
                        ParkingSpaceStatus.FREE,
                        parking
                ),
                tariff
                ,
                "registration",
                LocalDateTime.now().minusHours(6).minusMinutes(1)
        )
    }

    /**
     * Creates FIRST tariffication rule (for regular and VIP driver Tariff) with rawFormula=x
     * @param tariff Tariff
     * @return TarifficationRule
     */
    static TarifficationRule firstTarifficationRule(Tariff tariff){
        new TarifficationRule(
                tariff,
                RuleOrder.FIRST,
                "x"
        )
    }

    /**
     * Creates SECOND tariffication rule (for regular driver Tariff) with rawFormula=x*2
     * @param tariff Tariff
     * @return TarifficationRule
     */
    static TarifficationRule secondRegularTarifficationRule(Tariff tariff){
        new TarifficationRule(
                tariff,
                RuleOrder.SECOND,
                "x*2"
        )
    }

    /**
     * Creates RECURSIVE_NEXT tariffication rule (for regular driver Tariff) with rawFormula=x*2*Math.pow(2,i)
     * @param tariff Tariff
     * @return TarifficationRule
     */
    static TarifficationRule recursiveRegularTarifficationRule(Tariff tariff){
        new TarifficationRule(
                tariff,
                RuleOrder.RECURSIVE_NEXT,
                "x*2*Math.pow(2,i)"
        )
    }


    /**
     * Creates SECOND tariffication rule (for regular driver Tariff) with rawFormula=x+2
     * @param tariff Tariff
     * @return TarifficationRule
     */
    static TarifficationRule secondVipTarifficationRule(Tariff tariff){
        new TarifficationRule(
                tariff,
                RuleOrder.SECOND,
                "x+2"
        )
    }

    /**
     * Creates RECURSIVE_NEXT tariffication rule (for VIP driver Tariff) with rawFormula=(x+2)*1.5*Math.pow(2,i)
     * @param tariff Tariff
     * @return TarifficationRule
     */
    static TarifficationRule recursiveVipTarifficationRule(Tariff tariff){
        new TarifficationRule(
                tariff,
                RuleOrder.RECURSIVE_NEXT,
                "(x+2)*Math.pow(1.5,i)"
        )
    }

    /**
     * Creates CalculatedCharge with default vehicleRegistry and current calculation datetime, but custom charge amount
     * @param charge custom charge amount
     * @return CalculatedCharge
     */
    static CalculatedCharge calculatedCharge(Monetary charge){
        def parking = parking()
        def tariff = regularTariff(parking)
        return new CalculatedCharge(
                vehicleRegistry(parking, tariff),
                charge,
                tariff,
                LocalDateTime.now()
        )
    }

    /**
     * Creates Payment with default ParkingAccount and current payment datetime
     * @param amount custom payment amount
     * @param calculatedCharge necessary to fill other payment params
     * @return Payment
     */
    static Payment payment(Monetary amount, CalculatedCharge calculatedCharge){
        return new Payment(
                new ParkingAccount("iban"),
                amount,
                LocalDateTime.now(),
                calculatedCharge.selectedTariff,
                calculatedCharge
        )
    }
}
