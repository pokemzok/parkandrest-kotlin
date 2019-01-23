package parkandrest.parkingmanagement.core.parking.account

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import parkandrest.parkingmanagement.api.parking.account.FinancialReportRequest
import parkandrest.parkingmanagement.api.parking.account.FinancialReportResponse
import parkandrest.parkingmanagement.core.monetary.Monetary
import parkandrest.parkingmanagement.core.parking.TariffsCollection
import parkandrest.parkingmanagement.core.tariff.TariffRepository
import java.time.LocalDate
import java.time.LocalTime

/**
 * Service for financial monitoring on the account (mostly for payments and charges)
 */
@Service
class AccountMonitorService @Autowired constructor(
        private val calculatedChargeRepository: CalculatedChargeRepository,
        private val paymentRepository: PaymentRepository,
        private val tariffRepository: TariffRepository
) {
    /**
     * U.S.5 As a parking owner, I want to know how much money was earned during a given day.
     * @param request - parkandrest.parkingmanagement.api.parking.account.FinancialReportRequest with given date and parking id
     * @return parkandrest.parkingmanagement.api.parking.account.FinancialReportResponse
     */
    fun financialReportForGivenDay(request: FinancialReportRequest): FinancialReportResponse {
        val tariffsCollection = TariffsCollection(tariffRepository.findByParkingId(request.parkingId))
        val chargesSum = sumChargesForDate(tariffsCollection, request.reportDate)
        val paymentsSum = sumPaymentsForDate(tariffsCollection, request.reportDate)
        return FinancialReportResponse(
                chargesSum.amount,
                chargesSum.currency.name,
                paymentsSum.amount,
                paymentsSum.currency.name
        )
    }

    private fun sumChargesForDate(tariffsCollection: TariffsCollection, givenDate: LocalDate): Monetary {
        val charges = calculatedChargeRepository.getAllByCalculationDateTimeBetweenAndSelectedTariffIn(
                givenDate.atStartOfDay(),
                givenDate.atTime(LocalTime.MAX),
                tariffsCollection.tariffs)
        return CalculatedChargesCollection(charges)
                .sum(tariffsCollection.collectionCurrency)
    }

    private fun sumPaymentsForDate(tariffsCollection: TariffsCollection, givenDate: LocalDate): Monetary {
        val payments = paymentRepository
                .getAllByPaymentDateTimeBetweenAndSelectedTariffIn(
                        givenDate.atStartOfDay(),
                        givenDate.atTime(LocalTime.MAX),
                        tariffsCollection.tariffs
                )
        return PaymentsCollection(payments)
                .sum(tariffsCollection.collectionCurrency)
    }

}
