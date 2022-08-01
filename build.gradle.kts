plugins {
	id("org.springframework.boot") version "2.3.9.RELEASE"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	id("java")
}

group = "com.mcbanners"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(8))
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation(platform("org.springframework.cloud:spring-cloud-dependencies:Hoxton.SR10"))

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("com.github.ben-manes.caffeine:caffeine:2.9.0")
	implementation("org.mariadb.jdbc:mariadb-java-client:2.7.2")
	implementation("org.flywaydb:flyway-core:9.0.4")
	implementation("io.jsonwebtoken:jjwt-api:0.11.2")

	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
}

tasks {
	bootJar {
		archiveFileName.set("bannerapi.jar")
	}
}
