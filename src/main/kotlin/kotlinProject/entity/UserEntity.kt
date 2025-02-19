package kotlinProject.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,  // 기본값 설정
    @Column(nullable = false, unique = true)
    val username: String,
    @Column(nullable = false)
    val password: String
)