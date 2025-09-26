import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.colosseum"
version = "0.0.2"

plugins {
    kotlin("jvm")
    kotlin("kapt")
    `java-library`
    `maven-publish`
    `java-test-fixtures`
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.assertj:assertj-core:3.26.3")

    testFixturesImplementation(project(":colo-util"))
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Jar> {
    enabled = true
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        freeCompilerArgs.set(listOf("-Xjsr305=strict"))
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/yoonseonlee-colosseum/colosseum-libs-backend")
            credentials {
                username = project.findProperty("gpr.user") as String
                password = project.findProperty("gpr.key") as String
            }
        }
    }
    publications {
        create<MavenPublication>("gpr") {
            from(components["java"]) // 어떤 빌드 결과물을 아티팩트로 삼을지 지정
            groupId = project.group.toString()
            version = project.version.toString()
        }
    }
}
