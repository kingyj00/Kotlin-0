plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
	kotlin("plugin.jpa") version "1.9.25"
}

group = "kotlinProject"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Spring Boot 기본 스타터
	implementation("org.springframework.boot:spring-boot-starter-data-jpa") // JPA (MySQL과 연결)
	implementation("org.springframework.boot:spring-boot-starter-mail") // 이메일 기능
	implementation("org.springframework.boot:spring-boot-starter-security") // 보안 (로그인, 인증)
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf") // Thymeleaf 템플릿 엔진
	implementation("org.springframework.boot:spring-boot-starter-validation") // 유효성 검사
	implementation("org.springframework.boot:spring-boot-starter-web") // 웹 서버 지원

	// 기타 라이브러리
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin") // JSON 변환 지원
	implementation("org.jetbrains.kotlin:kotlin-reflect") // Kotlin 리플렉션
	implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6") // Thymeleaf + Spring Security

	// JWT (토큰 기반 인증)
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")

	// Lombok (코드 간소화)
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// 개발용 도구 (핫 리로드)
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// MySQL 연결 (도커 사용)
	runtimeOnly("com.mysql:mysql-connector-j")

	// 테스트 관련 라이브러리
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
