package parkandrest.parkingmanagement.api.parking.account

import java.time.LocalDate

data class FinancialReportRequest(var parkingId: Long, var reportDate: LocalDate)
