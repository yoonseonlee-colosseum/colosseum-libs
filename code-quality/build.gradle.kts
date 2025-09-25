group = "com.colosseum"
version = "1.0.8"

plugins {
    id("com.diffplug.spotless") version "6.25.0"

    `kotlin-dsl`
    `maven-publish`
    `java-gradle-plugin`
}

repositories {
    gradlePluginPortal()
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("colosseumCodeQuality") {
            id = "com.colosseum.code-quality" // 소비자 리포에서 쓸 플러그인 ID
            implementationClass = "com.colosseum.codequality.CodeQualityPlugin"
        }
    }
}

dependencies {
    compileOnly("com.diffplug.spotless:spotless-plugin-gradle:6.25.0")
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
}
