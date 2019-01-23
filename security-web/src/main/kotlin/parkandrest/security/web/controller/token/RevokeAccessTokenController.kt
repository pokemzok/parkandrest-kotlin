package parkandrest.security.web.controller.token

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import javax.annotation.Resource
import javax.servlet.http.HttpServletRequest

@Controller
@PreAuthorize("isAuthenticated()")
class RevokeAccessTokenController(
        @Resource(name = "tokenServices")
        private val tokenServices: ConsumerTokenServices
) {

    @RequestMapping(method = [RequestMethod.DELETE], value = ["/revoke/token"])
    @ResponseBody
    fun revokeToken(request: HttpServletRequest) {
        val authorization = request.getHeader(AUTHORIZATION_HEADER)
        if (authorization != null && authorization.contains(BEARER, true)) {
            val tokenId = authorization.substring(BEARER.length + 1)
            tokenServices.revokeToken(tokenId)
        }
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER = "bearer"
    }
}
