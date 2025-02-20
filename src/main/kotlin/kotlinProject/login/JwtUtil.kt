package kotlinProject.login

import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey
import kotlinProject.entity.Role

@Component
class JwtUtil {
    private val secretKey: SecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256) // 비밀 키 생성
    private val expiration: Long = 1000 * 60 * 60 // 1시간

    // 🔹 JWT 토큰 생성 (Role 정보 포함)
    fun generateToken(username: String, role: Role): String {
        return Jwts.builder()
            .setSubject(username) // subject 사용법 수정
            .claim("role", role.name) // 역할 추가
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiration))
            .signWith(secretKey, SignatureAlgorithm.HS256) // 🔹 SIG 대신 signWith 사용
            .compact()
    }

    // 🔹 JWT 토큰 검증
    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    // 🔹 토큰에서 사용자 이름 추출
    fun extractUsername(token: String): String? {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body.subject // subject 사용법 수정
        } catch (e: Exception) {
            null
        }
    }

    // 🔹 토큰에서 역할(Role) 추출
    fun extractRole(token: String): String? {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .body["role"] as String
        } catch (e: Exception) {
            null
        }
    }
}