package kotlinProject.service

import kotlinProject.entity.Role
import kotlinProject.entity.User
import kotlinProject.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun register(username: String, password: String): User {
        val hashedPassword = passwordEncoder.encode(password)
        val user = User(username = username, password = hashedPassword, role = Role.USER) // 기본 ROLE_USER
        return userRepository.save(user)
    }
}