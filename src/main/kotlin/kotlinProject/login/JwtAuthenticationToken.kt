package kotlinProject.login

import org.springframework.security.authentication.AbstractAuthenticationToken

class JwtAuthenticationToken(
    private val username: String
) : AbstractAuthenticationToken(null) {

    override fun getCredentials(): Any? = null

    override fun getPrincipal(): Any = username

    init {
        isAuthenticated = true // 인증이 완료되었음을 표시
    }
}