package kotlinProject

import kotlinProject.entity.Post
import kotlinProject.service.PostService
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import kotlinProject.controller.PostController

@WebMvcTest(PostController::class)
class PostControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc // MockMvc는 @WebMvcTest에서 제공됨

    @MockBean
    private lateinit var postService: PostService // @MockBean을 사용하여 서비스 모킹

    private val objectMapper = jacksonObjectMapper()

    @Test
    fun `게시글 생성 API 테스트`() {
        // Given
        val post = Post(1L, "테스트 제목", "테스트 내용", "테스트 작성자")
        given(postService.createPost("테스트 제목", "테스트 내용", "테스트 작성자")).willReturn(post)

        val requestJson = objectMapper.writeValueAsString(
            mapOf("title" to "테스트 제목", "content" to "테스트 내용", "author" to "테스트 작성자")
        )

        // When & Then
        mockMvc.perform(post("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("테스트 제목"))
            .andExpect(jsonPath("$.content").value("테스트 내용"))
            .andExpect(jsonPath("$.author").value("테스트 작성자"))
    }

    @Test
    fun `게시글 목록 조회 API 테스트`() {
        // Given
        val posts = listOf(
            Post(1L, "제목1", "내용1", "작성자1"),
            Post(2L, "제목2", "내용2", "작성자2")
        )
        given(postService.getAllPosts()).willReturn(posts)

        // When & Then
        mockMvc.perform(get("/api/posts"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[0].title").value("제목1"))
            .andExpect(jsonPath("$[1].title").value("제목2"))
    }

    @Test
    fun `게시글 단건 조회 API 테스트`() {
        // Given
        val post = Post(1L, "테스트 제목", "테스트 내용", "테스트 작성자")
        given(postService.getPostById(1L)).willReturn(post)

        // When & Then
        mockMvc.perform(get("/api/posts/1"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("테스트 제목"))
    }

    @Test
    fun `게시글 수정 API 테스트`() {
        // Given
        val updatedPost = Post(1L, "수정된 제목", "수정된 내용", "테스트 작성자")
        given(postService.updatePost(1L, "수정된 제목", "수정된 내용")).willReturn(updatedPost)

        val requestJson = objectMapper.writeValueAsString(
            mapOf("title" to "수정된 제목", "content" to "수정된 내용", "author" to "테스트 작성자")
        )

        // When & Then
        mockMvc.perform(put("/api/posts/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(requestJson))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("수정된 제목"))
    }

    @Test
    fun `게시글 삭제 API 테스트`() {
        // Given
        willDoNothing().given(postService).deletePost(1L)

        // When & Then
        mockMvc.perform(delete("/api/posts/1"))
            .andExpect(status().isNoContent)
    }
}