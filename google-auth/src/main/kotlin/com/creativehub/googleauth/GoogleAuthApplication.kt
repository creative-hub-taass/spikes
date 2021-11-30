package com.creativehub.googleauth

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*


@SpringBootApplication
@RestController
class GoogleAuthApplication : WebSecurityConfigurerAdapter() {

	@GetMapping("/user")
	fun user(@AuthenticationPrincipal principal: OAuth2User): Map<String, Any?>? {
		return Collections.singletonMap("name", principal.getAttribute("name"))
	}

	override fun configure(http: HttpSecurity) {
		http.authorizeRequests {
			it.antMatchers("/", "/error", "/webjars/**")
				.permitAll()
				.anyRequest()
				.authenticated()
		}.exceptionHandling {
			it.authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
		}.logout {
			it.logoutSuccessUrl("/").permitAll()
		}.csrf {
			it.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		}.oauth2Login()
	}
}

fun main(args: Array<String>) {
	runApplication<GoogleAuthApplication>(*args)
}
