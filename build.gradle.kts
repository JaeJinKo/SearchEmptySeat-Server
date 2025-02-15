plugins {
	java
	id("org.springframework.boot") version "3.4.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.BubbleWrap"
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
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	runtimeOnly("org.postgresql:postgresql")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation("org.springframework.boot:spring-boot-starter-security") // Spring Security
	implementation("io.jsonwebtoken:jjwt-api:0.11.5") // JWT 라이브러리
	implementation("org.springframework.boot:spring-boot-starter-data-jpa") // JPA
	implementation("org.springframework.boot:spring-boot-starter-validation") // 입력 검증
	implementation("com.fasterxml.jackson.core:jackson-databind") // JSON 처리
	runtimeOnly("org.postgresql:postgresql") // PostgreSQL (DB에 따라 변경)
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("org.flywaydb:flyway-core:9.22.0")
	implementation("org.postgresql:postgresql")
	testCompileOnly("org.projectlombok:lombok")
	testAnnotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	implementation("io.github.cdimascio:dotenv-java:3.0.0")
	implementation("org.springframework.boot:spring-boot-starter-mail")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
