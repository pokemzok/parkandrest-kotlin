package parkandrest.parkingmanagement.core.parking.account

import com.google.common.collect.Sets
import spock.lang.Specification
import parkandrest.parkingmanagement.api.monetary.CurrencyMismatchException
import parkandrest.parkingmanagement.api.parking.account.FinancialReportRequest
import parkandrest.parkingmanagement.api.parking.account.FinancialReportResponse
import parkandrest.parkingmanagement.api.tariff.NoTariffPresentException
import parkandrest.parkingmanagement.core.monetary.Currency
import parkandrest.parkingmanagement.core.stubfactory.EntitiesStubFactory
import parkandrest.parkingmanagement.core.stubfactory.MonetaryStubFactory
import parkandrest.parkingmanagement.core.tariff.TariffRepository

import java.math.RoundingMode
import java.time.LocalDate
import java.time.LocalTime

class AccountMonitorServiceSpec extends Specification {

    def chargeRepository = Mock(CalculatedChargeRepository.class)
    def paymentRepository = Mock(PaymentRepository.class)
    def tariffRepository = Mock(TariffRepository.class)
    def testedService = new AccountMonitorService(chargeRepository, paymentRepository, tariffRepository)

    def "should successfully response"() {
        given:
        def currentDate = LocalDate.now()
        def parkingId = 1l
        def request = new FinancialReportRequest(parkingId, currentDate)
        def tariffs = EntitiesStubFactory.parkingWithAllTariffs().tariffsCollection.tariffs
        def calculatedCharge = EntitiesStubFactory.calculatedCharge(MonetaryStubFactory.ONE_PLN)
        and:
        tariffRepository.findByParkingId(parkingId) >> tariffs
        and:
        chargeRepository.
                getAllByCalculationDateTimeBetweenAndSelectedTariffIn(
                        currentDate.atStartOfDay(),
                        currentDate.atTime(LocalTime.MAX),
                        tariffs
                ) >> Sets.newHashSet(calculatedCharge)

        and:
        paymentRepository.
                getAllByPaymentDateTimeBetweenAndSelectedTariffIn(
                        currentDate.atStartOfDay(),
                        currentDate.atTime(LocalTime.MAX),
                        tariffs
                ) >> Sets.newHashSet(EntitiesStubFactory.payment(MonetaryStubFactory.ZERO_PLN, calculatedCharge))
        when:
        def response = testedService.financialReportForGivenDay(request)
        then:
        response == new FinancialReportResponse(
                BigDecimal.ONE.setScale(2, RoundingMode.HALF_DOWN),
                Currency.PLN.name(),
                BigDecimal.ZERO.setScale(2, RoundingMode.HALF_DOWN),
                Currency.PLN.name()
        )
    }

    def "should not find any payments and charges and still successfully response"() {
        given:
        def currentDate = LocalDate.now()
        def parkingId = 1l
        def request = new FinancialReportRequest(parkingId, currentDate)
        def tariffs = EntitiesStubFactory.parkingWithAllTariffs().tariffsCollection.tariffs
        and:
        tariffRepository.findByParkingId(parkingId) >> tariffs
        and:
        chargeRepository.
                getAllByCalculationDateTimeBetweenAndSelectedTariffIn(
                        currentDate.atStartOfDay(),
                        currentDate.atTime(LocalTime.MAX),
                        tariffs
                ) >> Sets.newHashSet()

        and:
        paymentRepository.
                getAllByPaymentDateTimeBetweenAndSelectedTariffIn(
                        currentDate.atStartOfDay(),
                        currentDate.atTime(LocalTime.MAX),
                        tariffs
                ) >> Sets.newHashSet()
        when:
        def response = testedService.financialReportForGivenDay(request)
        then:
        response == new FinancialReportResponse(
                BigDecimal.ZERO.setScale(2, RoundingMode.HALF_DOWN),
                Currency.PLN.name(),
                BigDecimal.ZERO.setScale(2, RoundingMode.HALF_DOWN),
                Currency.PLN.name()
        )
    }

    def "should not find any tariffs which triggers NoTariffPresentException"() {
        given:
        def currentDate = LocalDate.now()
        def parkingId = 1l
        def request = new FinancialReportRequest(parkingId, currentDate)
        def tariffs = Sets.newHashSet()
        and:
        tariffRepository.findByParkingId(parkingId) >> tariffs
        and:
        chargeRepository.
                getAllByCalculationDateTimeBetweenAndSelectedTariffIn(
                        currentDate.atStartOfDay(),
                        currentDate.atTime(LocalTime.MAX),
                        tariffs
                ) >> Sets.newHashSet()

        and:
        paymentRepository.
                getAllByPaymentDateTimeBetweenAndSelectedTariffIn(
                        currentDate.atStartOfDay(),
                        currentDate.atTime(LocalTime.MAX),
                        tariffs
                ) >> Sets.newHashSet()
        when:
        testedService.financialReportForGivenDay(request)
        then:
        thrown NoTariffPresentException
    }

    def "should throw CurrencyMismatchException because Database is inconsistent (tariff currency and payment/charge currency must be equal)"() {
        given:
        def currentDate = LocalDate.now()
        def parkingId = 1l
        def request = new FinancialReportRequest(parkingId, currentDate)
        def tariffs = EntitiesStubFactory.parkingWithAllTariffs().tariffsCollection.tariffs
        def calculatedCharge = EntitiesStubFactory.calculatedCharge(MonetaryStubFactory.ONE_EU)
        and:
        tariffRepository.findByParkingId(parkingId) >> tariffs
        and:
        chargeRepository.
                getAllByCalculationDateTimeBetweenAndSelectedTariffIn(
                        currentDate.atStartOfDay(),
                        currentDate.atTime(LocalTime.MAX),
                        tariffs
                ) >> Sets.newHashSet(calculatedCharge)

        and:
        paymentRepository.
                getAllByPaymentDateTimeBetweenAndSelectedTariffIn(
                        currentDate.atStartOfDay(),
                        currentDate.atTime(LocalTime.MAX),
                        tariffs
                ) >> Sets.newHashSet(EntitiesStubFactory.payment(MonetaryStubFactory.ZERO_EU, calculatedCharge))
        when:
        testedService.financialReportForGivenDay(request)
        then:
        thrown CurrencyMismatchException
    }
}
