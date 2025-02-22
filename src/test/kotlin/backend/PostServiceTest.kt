package backend

import backend.entity.Post
import backend.repository.PostRepository
import backend.service.PostService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class PostServiceTest {

    @Mock
    private lateinit var postRepository: PostRepository

    @InjectMocks
    private lateinit var postService: PostService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `게시글 생성 테스트`() {
        // Given
        val title = "테스트 제목"
        val content = "테스트 내용"
        val author = "테스트 작성자"
        val post = Post(id = 1L, title = title, content = content, author = author)

        `when`(postRepository.save(any(Post::class.java))).thenReturn(post)

        // When
        val savedPost = postService.createPost(title, content, author)

        // Then
        assertNotNull(savedPost)
        assertEquals(title, savedPost.title)
        assertEquals(content, savedPost.content)
        assertEquals(author, savedPost.author)
    }

    @Test
    fun `게시글 단건 조회 테스트`() {
        // Given
        val post = Post(id = 1L, title = "테스트", content = "테스트 내용", author = "작성자")
        `when`(postRepository.findById(1L)).thenReturn(Optional.of(post))

        // When
        val foundPost = postService.getPostById(1L)

        // Then
        assertNotNull(foundPost)
        assertEquals("테스트", foundPost.title)
    }

    @Test
    fun `게시글 삭제 테스트`() {
        // Given
        val post = Post(id = 1L, title = "테스트", content = "테스트 내용", author = "작성자")
        `when`(postRepository.findById(1L)).thenReturn(Optional.of(post))
        doNothing().`when`(postRepository).deleteById(1L)  // deleteById()를 모킹

        // When
        postService.deletePost(1L)

        // Then
        verify(postRepository, times(1)).deleteById(1L)  // deleteById()가 호출되었는지 검증
    }
}