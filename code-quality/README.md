# 📘 Code Quality Plugin

팀 공통 **코드 포맷팅 규칙**을 제공하는 Gradle 플러그인 입니다.  
플러그인을 적용하면 각 프로젝트에서 Spotless 규칙을 직접 작성할 필요 없이, **한 줄 선언으로 팀 표준 규칙**을 자동으로 적용할 수 있습니다.

---

## 🔧 소비자 프로젝트에서 사용하는 방법

### 1. `settings.gradle.kts`

공용 플러그인이 올라간 **GitHub Packages** 를 참조하도록 설정합니다.

```kotlin
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven {
            url = uri("https://maven.pkg.github.com/yoonseonlee-colosseum/colosseum-libs")
            credentials {
                username = {username}
                password = {token 정보}
            }
        }
    }
}
```

### 2. `build.gradle.kts`

플러그인을 선언합니다.

```kotlin
plugins {
    id("com.colosseum.code-quality") version {버전정보}
    id("com.diffplug.spotless") version "6.25.0" apply false
}
```
👉 여기서 apply false 로 Spotless 버전을 명시해야,   
공급자 플러그인이 내부적으로 apply("com.diffplug.spotless") 를 실행할 때 정상 동작합니다.

### 3. 명령어 실행

#### 코드 포맷 검사

```bash
./gradlew spotlessCheck
```

#### 코드 포맷 자동 적용

```bash
./gradlew spotlessApply
```

---

## 📝 내부적으로 하는 일

`com.colosseum.code-quality` 플러그인은 내부적으로 다음 작업을 수행합니다.

- `com.diffplug.spotless` 플러그인을 자동으로 적용
- Kotlin, Kotlin Gradle Script, Java, Markdown, Gitignore 등 여러 파일 포맷에 대한 **팀 공통 규칙** 설정

### 적용 규칙 예시

- **Kotlin**
    - `ktlint` 기반 코드 스타일 검사
    - `indent_size = 4`
    - `max_line_length = 120`
    - 트레일링 콤마 허용
    - 와일드카드 import 금지
    - `build/**`, `bin/**`, `testFixtures/**` 등 특정 경로 제외
    - 끝 공백 제거 및 파일 끝에 개행 추가

- **Kotlin Gradle Script**
    - `*.gradle.kts` 파일에 `ktlint` 적용

- **Java**
    - Palantir Java Format(`2.61.0`) 적용
    - 불필요한 import 제거
    - import 정렬 순서: `java`, `javax`, `jakarta`, `org`, `com`, (기타)
    - `build/**`, `.gradle/**` 파일 제외

- **기타 파일 (misc)**
    - 대상: `*.gradle`, `*.md`, `.gitignore`
    - 공백 제거, space 들여쓰기, 파일 끝 개행 추가

---

## ✅ 장점

1. **규칙 단일화**
    - Spotless DSL을 각 프로젝트에 중복 작성하지 않아도 됩니다.
    - 공통 규칙을 한 번만 정의하고, 모든 소비자 프로젝트에서 공유합니다.

2. **유지보수 용이**
    - 규칙이 바뀌면 플러그인 버전만 올리면 됩니다.
    - 코드 스타일 규칙의 일관성을 손쉽게 보장할 수 있습니다.

3. **팀 문화 강제**
    - CI/CD 파이프라인에서 `spotlessCheck` 를 실행하면,  
      규칙 위반 시 빌드가 실패하도록 강제할 수 있습니다.

4. **확장성**
    - 현재는 Spotless만 포함하지만, 추후 `ktlint`, `detekt`, `checkstyle` 등의 추가 품질 도구를 묶어  
      “팀 표준 코드 품질 플러그인”으로 확장할 수 있습니다.

