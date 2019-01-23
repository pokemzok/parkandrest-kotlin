package parkandrest.parkingmanagement.web.controller.account

import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import parkandrest.parkingmanagement.web.response.Response
import parkandrest.parkingmanagement.api.parking.account.FinancialReportRequest
import parkandrest.parkingmanagement.api.parking.account.FinancialReportResponse
import parkandrest.parkingmanagement.core.parking.account.AccountMonitorService


@RestController
@RequestMapping(value = ["/account/monitor/"])
class AccountMonitorController (private val accountMonitorService: AccountMonitorService) {

    companion object {
        private val log = LoggerFactory.getLogger(AccountMonitorController::class.java)
    }

    /**
     * U.S.5 As a parking owner, I want to know how much money was earned during a given day.
     * @param request - parkandrest.parkingmanagement.api.parking.account.FinancialReportRequest with given date and parking id
     * @return parkandrest.parkingmanagement.web.response.Response with parkandrest.parkingmanagement.api.parking.account.FinancialReportResponse or BusinessException
     */
    @PreAuthorize("hasAuthority('OWNER')")
    @RequestMapping(value = ["financialReport"], method = [RequestMethod.POST], consumes = ["application/json"])
    fun financialReport(@RequestBody request: FinancialReportRequest): Response<FinancialReportResponse> {
        log.info("Received financialReport request for parking Id: {}.", request.parkingId)
        return Response(
                accountMonitorService.financialReportForGivenDay(request)
        )
    }
}