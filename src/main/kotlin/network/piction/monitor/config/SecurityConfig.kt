package network.piction.monitor.config

import de.codecentric.boot.admin.server.config.AdminServerProperties
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.csrf.CookieCsrfTokenRepository

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
open class SecurityConfig(val adminServerProperties: AdminServerProperties) : WebSecurityConfigurerAdapter() {

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        val successHandler = SavedRequestAwareAuthenticationSuccessHandler()
        successHandler.setTargetUrlParameter("redirectTo")
        successHandler.setDefaultTargetUrl(adminServerProperties.contextPath + "/")

        http.authorizeRequests()
            .antMatchers(adminServerProperties.contextPath + "/assets/**").permitAll()
            .antMatchers(adminServerProperties.contextPath + "/login").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage(adminServerProperties.contextPath + "/login").successHandler(successHandler).and()
            .logout().logoutUrl(adminServerProperties.contextPath + "/logout").and()
            .httpBasic().and()
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .ignoringAntMatchers(
                adminServerProperties.contextPath + "/instances",
                adminServerProperties.contextPath + "/actuator/**"
            )
    }
}