package kotlinProject

import kotlinProject.login.JwtFilter
import kotlinProject.login.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtUtil: JwtUtil,
//    private val userDetailsService: UserDetailsService # 실제로 구현된 구현체가 없음
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain { //리턴 타입이 일치하지 않음
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
            .build()
    }
}

// 다중화 (도커를 사용한)
// 도커 볼륨 네트워크에 대한 지식

// MYSQL에 대한
// 쿼리 인덱스 최적화.