package kotlinProject.controller

import kotlinProject.entity.Post
import kotlinProject.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostService
) {

    @PostMapping
    fun createPost(@RequestBody request: PostRequest): ResponseEntity<Post> {
        val post = postService.createPost(request.title, request.content, request.author)
        return ResponseEntity.ok(post)
    }

    @GetMapping
    fun getAllPosts(): ResponseEntity<List<Post>> {
        return ResponseEntity.ok(postService.getAllPosts())
    }

    @GetMapping("/{id}")
    fun getPostById(@PathVariable id: Long): ResponseEntity<Post> {
        return ResponseEntity.ok(postService.getPostById(id))
    }

    @PutMapping("/{id}")
    fun updatePost(@PathVariable id: Long, @RequestBody request: PostRequest): ResponseEntity<Post> {
        val updatedPost = postService.updatePost(id, request.title, request.content)
        return ResponseEntity.ok(updatedPost)
    }

    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: Long): ResponseEntity<Void> {
        postService.deletePost(id)
        return ResponseEntity.noContent().build()
    }
}

data class PostRequest(
    val title: String,
    val content: String,
    val author: String
)