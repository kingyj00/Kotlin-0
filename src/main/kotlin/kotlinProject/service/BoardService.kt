package kotlinProject.service

import kotlinProject.entity.Post
import kotlinProject.repository.PostRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PostService(
    private val postRepository: PostRepository
) {

    fun createPost(title: String, content: String, author: String): Post {
        val post = Post(title = title, content = content, author = author)
        return postRepository.save(post)
    }

    fun getAllPosts(): List<Post> {
        return postRepository.findAll()
    }

    fun getPostById(id: Long): Post {
        return postRepository.findById(id).orElseThrow { IllegalArgumentException("게시글을 찾을 수 없습니다.") }
    }

    @Transactional
    fun updatePost(id: Long, title: String, content: String): Post {
        val post = getPostById(id)
        post.title = title
        post.content = content
        return post
    }

    fun deletePost(id: Long) {
        postRepository.deleteById(id)
    }
}