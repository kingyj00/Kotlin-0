package backend.controller

import backend.dto.UserRequest
import backend.dto.UserResponse
import backend.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/register")
    fun registerUser(@RequestBody request: UserRequest): ResponseEntity<UserResponse> {
        return ResponseEntity.ok(userService.registerUser(request))
    }
}