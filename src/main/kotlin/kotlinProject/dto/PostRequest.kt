package kotlinProject.dto

data class PostRequest(
    val title: String,
    val content: String,
    val author: String
)