package kotlinProject

import kotlinProject.login.JwtFilter
import kotlinProject.login.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtUtil: JwtUtil,
    private val userDetailsService: UserDetailsService
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): HttpSecurity {
        return http
            .csrf { it.disable() } // CSRF 비활성화
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // JWT 사용 → 세션 사용 안함
            .authorizeHttpRequests {
                it
                    .requestMatchers("/api/auth/**").permitAll() // 회원가입, 로그인은 누구나 가능
                    .requestMatchers("/api/admin/**").hasRole("ADMIN") // 관리자만 접근 가능
                    .anyRequest().authenticated() // 그 외 요청은 인증 필요
            }
            .addFilterBefore(JwtFilter(jwtUtil), UsernamePasswordAuthenticationFilter::class.java)
    }
}