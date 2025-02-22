package backend.controller

import backend.dto.PostRequest
import backend.dto.PostResponse
import backend.service.PostService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostService
) {

    @PostMapping
    fun createPost(@RequestBody request: PostRequest): ResponseEntity<PostResponse> {
        val post = postService.createPost(request.title, request.content, request.author)
        return ResponseEntity.ok(PostResponse(post.id, post.title, post.content, post.author))
    }

    @GetMapping
    fun getAllPosts(): ResponseEntity<List<PostResponse>> {
        val posts = postService.getAllPosts().map { PostResponse(it.id, it.title, it.content, it.author) }
        return ResponseEntity.ok(posts)
    }

    @GetMapping("/{id}")
    fun getPostById(@PathVariable id: Long): ResponseEntity<PostResponse> {
        val post = postService.getPostById(id)
        return ResponseEntity.ok(PostResponse(post.id, post.title, post.content, post.author))
    }

    @PutMapping("/{id}")
    fun updatePost(@PathVariable id: Long, @RequestBody request: PostRequest): ResponseEntity<PostResponse> {
        val updatedPost = postService.updatePost(id, request.title, request.content)
        return ResponseEntity.ok(PostResponse(updatedPost.id, updatedPost.title, updatedPost.content, updatedPost.author))
    }

    @DeleteMapping("/{id}")
    fun deletePost(@PathVariable id: Long): ResponseEntity<Void> {
        postService.deletePost(id)
        return ResponseEntity.noContent().build()
    }
}