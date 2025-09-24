plugins {
    kotlin("jvm") version "2.0.20"
    id("com.diffplug.spotless") version "6.25.0"

    `kotlin-dsl` // gradle plugin 개발용
    `maven-publish`
    `java-gradle-plugin`
}

repositories {
    gradlePluginPortal() // 여기서 플러그인 의존성 가져올 수 있음(gradlePlugin)
    mavenCentral()
}

gradlePlugin {
    plugins {
        create("colosseumSpotless") {
            id = "com.colosseum.spotless" // 소비자 리포에서 쓸 플러그인 ID
            implementationClass = "java.lang.Object"
        }
    }
}

spotless {
    // 변경된 파일만 검사하도록 설정하여 속도 향상
//    ratchetFrom("HEAD")

    kotlin {
        ktlint("1.4.1")
            .editorConfigOverride(
                mapOf(
                    "charset" to "utf-8",
                    "end_of_line" to "lf",
                    "insert_final_newline" to true,
                    "indent_style" to "space",
                    "indent_size" to 4,
                    "max_line_length" to 120,
                    "trim_trailing_whitespace" to true,
                    "ij_kotlin_allow_trailing_comma" to true,
                    "ij_kotlin_allow_trailing_comma_on_call_site" to true,
                    "no-wildcard-imports" to true,
                ),
            )
        target("**/*.kt")
        targetExclude(
            "build/**/*.kt",
            "bin/**/*.kt",
            "**/package-info.kt",
            "**/src/testFixtures/**/*.kt",
        )
        trimTrailingWhitespace()
        endWithNewline()
    }

    kotlinGradle {
        ktlint("1.4.1")
        target("*.gradle.kts") // default target for kotlinGradle
    }

    java {
        // 캐시 활성화로 증분 포맷팅 지원
        toggleOffOn()

        // Palantir Java Format 사용
        // https://github.com/palantir/palantir-java-format
        palantirJavaFormat("2.61.0")

        // 특정 파일 제외
        targetExclude("build/**", ".gradle/**")

        // 불필요한 import 정리
        removeUnusedImports()

        // 정렬 규칙 (.editorconfig와 일치)
        importOrder("java", "javax", "jakarta", "org", "com", "")

        // .editorconfig와 동일한 import 설정 적용
        // 실제로는 palantir 포맷터에 이 설정이 직접 적용되지 않을 수 있음
        // 필요한 경우 IntelliJ 설정에서 추가로 구성해야 함
    }

    format("misc") {
        target("*.gradle", "*.md", ".gitignore")
        trimTrailingWhitespace()
        indentWithSpaces()
        endWithNewline()
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/yoonseonlee-colosseum/colosseum-libs")
            credentials {
                username = project.findProperty("gpr.user") as String
                password = project.findProperty("gpr.key") as String
            }
        }
    }
}
