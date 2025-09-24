package com.colosseum.codequality

import org.gradle.api.Plugin
import org.gradle.api.Project

/*
**
*
* 이 플러그인은 실제로 아무 동작도 하지 않습니다.
*
* Gradle 플러그인을 배포하기 위해서는 `implementationClass`가 반드시 필요하기 때문에
* 플러그인 id와 실제 JAR 아티팩트를 연결하는 "껍데기" 역할만 합니다.
*
* 즉, Spotless 등 구체적인 규칙 설정은 플러그인의 `build.gradle.kts` (DSL) 안에서 정의되고,
* 이 클래스는 소비자가 `plugins { id("com.colosseum.code-quality") }` 로 적용할 수 있도록
* marker 역할만 수행합니다.
*/
class EmptyPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        // 아무 동작도 하지 않음 (Spotless 설정은 DSL에서 정의)
    }
}
