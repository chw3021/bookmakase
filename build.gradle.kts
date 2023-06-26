plugins {
	java
	id("org.springframework.boot") version "3.0.2"
	id("io.spring.dependency-management") version "1.1.0"
}

group = "hello"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf");
	implementation("org.springframework.boot:spring-boot-starter-web");
	implementation('org.springframework.boot:spring-boot-starter-jdbc');
	testImplementation("org.springframework.boot:spring-boot-starter-test");

	runtimeOnly 'com.h2database:h2'

}

tasks.withType<Test> {
	useJUnitPlatform()
}
