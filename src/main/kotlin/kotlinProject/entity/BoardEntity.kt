package kotlinProject.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "posts")
class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,  // ✅ 기본값 설정 (nullable 제거)

    var title: String,

    var content: String,

    var author: String,

    val createdAt: LocalDateTime = LocalDateTime.now()
)