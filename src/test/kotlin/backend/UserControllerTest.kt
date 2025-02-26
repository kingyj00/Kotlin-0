package backend.controller

import backend.dto.UserRequest
import backend.dto.UserResponse
import backend.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders


@ExtendWith(MockitoExtension::class) // 최신 Mockito 확장 사용
class UserControllerTest {

    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var userService: UserService

    @InjectMocks
    private lateinit var userController: UserController

    private val objectMapper = ObjectMapper()

    private lateinit var userRequest: UserRequest
    private lateinit var userResponse: UserResponse

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build()

        // 테스트용 데이터 설정
        userRequest = UserRequest(
            username = "testuser",
            password = "password123"
        )

        userResponse = UserResponse(
            id = 1L,
            username = "testuser"
        )
    }

    @Test
    fun `회원가입 성공 - 200 OK 응답`() {
        `when`(userService.registerUser(any())).thenReturn(userResponse)

        mockMvc.perform(
            post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.username").value("testuser"))

        verify(userService, times(1)).registerUser(any())
    }

    @Test
    fun `회원가입 실패 - 요청 본문 누락 (400 Bad Request)`() {
        val invalidRequest = """{"password": "password123"}""" // username 없음

        mockMvc.perform(
            post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(invalidRequest)
        )
            .andExpect(status().isBadRequest)
    }
}