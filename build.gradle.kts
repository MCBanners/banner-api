plugins {
	id("org.springframework.boot") version "2.7.9"
	id("io.spring.dependency-management") version "1.1.0"
	id("java")
}

group = "com.mcbanners"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(17))
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(platform("org.springframework.cloud:spring-cloud-dependencies:2021.0.4"))

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("com.github.ben-manes.caffeine:caffeine:3.1.1")
	implementation("org.mariadb.jdbc:mariadb-java-client:3.0.7")
	implementation("org.flywaydb:flyway-mysql:9.3.0")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")

	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
}

tasks {
	bootJar {
		archiveFileName.set("bannerapi.jar")
	}
}
