package parkandrest.security.web.config

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.TokenEnhancer
import parkandrest.security.core.usermanagement.GrantedAuthorityCollection
import java.util.HashMap

class CustomTokenEnhancer : TokenEnhancer {

    override fun enhance(accessToken: OAuth2AccessToken, authentication: OAuth2Authentication): OAuth2AccessToken {
        val additionalInfo = HashMap<String, Any>()
        val authorityCollection = GrantedAuthorityCollection(authentication.authorities)
        additionalInfo[AUTHORITIES] = authorityCollection.toString()
        (accessToken as DefaultOAuth2AccessToken).additionalInformation = additionalInfo
        return accessToken
    }

    companion object {
        val AUTHORITIES = "authorities"
    }
}