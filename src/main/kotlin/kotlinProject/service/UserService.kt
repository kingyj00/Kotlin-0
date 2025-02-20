package kotlinProject.service

import kotlinProject.dto.UserRequest
import kotlinProject.dto.UserResponse
import kotlinProject.entity.User
import kotlinProject.repository.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) {
    fun registerUser(request: UserRequest): UserResponse {
        // 중복 체크
        if (userRepository.findByUsername(request.username).isPresent) {
            throw IllegalArgumentException("이미 존재하는 사용자입니다.")
        }

        // 비밀번호 암호화 후 저장
        val encodedPassword = passwordEncoder.encode(request.password)
        val user = userRepository.save(User(username = request.username, password = encodedPassword))

        return UserResponse(user.id, user.username) // 🔹 user.id가 nullable이므로 그대로 전달 가능
    }
}