package kotlinProject.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val username: String,

    @Column(nullable = false)
    val password: String,

    @Enumerated(EnumType.STRING) // ENUM 타입 매핑
    @Column(nullable = false)
    val role: Role = Role.USER // 기본값 USER
)