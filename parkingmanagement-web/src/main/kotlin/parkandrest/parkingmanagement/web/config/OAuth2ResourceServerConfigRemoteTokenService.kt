package parkandrest.parkingmanagement.web.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.provider.token.RemoteTokenServices

@Configuration
@EnableResourceServer
class OAuth2ResourceServerConfigRemoteTokenService(
        @Value("\${oauth2.token-endpoint.url}") private val tokenEndpointUrl: String,
        @Value("\${oauth2.client-id}") private val clientId: String,
        @Value("\${oauth2.client-secret}") private val clientSecret: String
) : ResourceServerConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        // @formatter:off
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authorizeRequests().anyRequest().permitAll()
        // @formatter:on
    }

    @Primary
    @Bean
    fun tokenServices(): RemoteTokenServices {
        val tokenService = RemoteTokenServices()
        tokenService.setCheckTokenEndpointUrl(tokenEndpointUrl)
        tokenService.setClientId(clientId)
        tokenService.setClientSecret(clientSecret)
        return tokenService
    }

}