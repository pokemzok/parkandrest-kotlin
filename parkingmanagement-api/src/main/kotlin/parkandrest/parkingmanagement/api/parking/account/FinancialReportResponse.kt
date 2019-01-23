package parkandrest.parkingmanagement.api.parking.account

import java.math.BigDecimal


data class FinancialReportResponse(
        val chargesSum: BigDecimal?,
        val chargesCurrency: String?,
        val paymentsSum: BigDecimal?,
        val paymentsCurrency: String?
)

