package kotlinProject.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var title: String,

    var content: String,

    var author: String,

    val createdAt: LocalDateTime = LocalDateTime.now()
)