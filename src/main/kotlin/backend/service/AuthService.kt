package backend.service

import backend.entity.Role
import backend.entity.User
import backend.repository.UserRepository
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