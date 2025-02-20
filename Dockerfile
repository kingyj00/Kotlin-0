# 1. OpenJDK 17 기반 이미지 사용
FROM openjdk:17-jdk

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. JAR 파일을 컨테이너에 복사
COPY build/libs/*.jar app.jar

# 4. 컨테이너 실행 시 JAR 파일 실행
ENTRYPOINT ["java", "-jar", "app.jar"]