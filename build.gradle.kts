plugins {
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
	id("com.github.ben-manes.versions") version "0.48.0"
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
	maven("https://jitpack.io")
}

dependencies {
	implementation(platform("org.springframework.cloud:spring-cloud-dependencies:2022.0.4"))

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-cache")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	implementation("com.github.ben-manes.caffeine:caffeine:3.1.8")
	implementation("org.mariadb.jdbc:mariadb-java-client:3.1.4")
	implementation("org.flywaydb:flyway-mysql:9.22.3")
	implementation("org.flywaydb:flyway-core:9.17.0")
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("com.github.xMrAfonso:Hangar4J:1.2.2") {
		exclude(group = "com.google.code.gson", module = "gson")
	}
	implementation("com.google.code.gson:gson:2.10.1")
	implementation("com.github.usefulness:webp-imageio:0.5.0")

	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.5")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.5")

	developmentOnly("org.springframework.boot:spring-boot-devtools")
}

tasks {
	bootJar {
		archiveFileName.set("bannerapi.jar")
	}
}
