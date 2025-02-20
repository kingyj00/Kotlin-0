package kotlinProject.controller

import kotlinProject.entity.Role
import kotlinProject.entity.User
import kotlinProject.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
class AuthController(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/create-admin")
    fun createAdmin(@RequestParam username: String, @RequestParam password: String): User {
        val hashedPassword = passwordEncoder.encode(password)
        val adminUser = User(username = username, password = hashedPassword, role = Role.ADMIN)
        return userRepository.save(adminUser)
    }
}