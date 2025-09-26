package com.colosseum.codequality

import com.diffplug.gradle.spotless.SpotlessExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

@Suppress("unused")
class CodeQualityPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println(">>> [CodeQualityPlugin] 적용됨 on project: ${target.name}")

        // Spotless 플러그인 적용
        target.pluginManager.apply("com.diffplug.spotless")

        // Spotless 확장 가져와서 규칙 설정
        val spotless = target.extensions.getByType(SpotlessExtension::class.java)
        spotless.apply {

            // Kotlin 소스 규칙
            kotlin {
                ktlint("1.4.1").editorConfigOverride(
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

                        // Kotlin-specific
                        "ij_kotlin_imports_layout" to "java.**,|,javax.**,|,jakarta.**,|,kotlin.**,|,org.**,|,com.**,|,*",
                        "ij_kotlin_name_count_to_use_star_import" to 999,
                        "ij_kotlin_name_count_to_use_star_import_for_members" to 999,
                        "ij_kotlin_allow_trailing_comma" to true,
                        "ij_kotlin_allow_trailing_comma_on_call_site" to true,
                    )
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

            // Kotlin Gradle Script 규칙
            kotlinGradle {
                ktlint("1.4.1")
                target("*.gradle.kts")
            }

            // Java 코드 규칙
            java {
                // 캐시 활성화로 증분 포맷팅 지원
                toggleOffOn()

                // Palantir Java Format 사용
                palantirJavaFormat("2.61.0")

                // 특정 파일 제외
                targetExclude("build/**", ".gradle/**")

                // 불필요한 import 정리
                removeUnusedImports()

                // 정렬 규칙 (.editorconfig와 일치)
                importOrder("java", "javax", "jakarta", "org", "com", "")
            }

            // 기타 Misc 파일 규칙
            format("misc") {
                target("**/*.gradle", "**/*.md", "**/.gitignore", "**/.gitattributes")
                trimTrailingWhitespace()
                indentWithSpaces(2)
                endWithNewline()
            }
        }
    }
}
