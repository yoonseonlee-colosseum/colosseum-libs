# 🏛️ Colosseum Libs

**Colosseum 공통 라이브러리/플러그인 저장소**  
여러 프로젝트에서 재사용할 수 있는 내부 라이브러리와 Gradle 플러그인을 모아둔 **모노레포(Mono-repo)** 형태입니다.

---

## 📦 포함된 모듈

### `code-quality`
- 팀 공통 **코드 포맷팅/스타일 규칙**을 제공하는 Gradle 플러그인 모듈
- [`com.colosseum.code-quality`](https://github.com/yoonseonlee-colosseum/colosseum-libs/packages) 플러그인으로 배포됨
- 내부적으로 [Spotless](https://github.com/diffplug/spotless) 플러그인을 적용하여, Kotlin/Java/Gradle Script/Markdown 등 다양한 파일 포맷에 **통일된 규칙**을 강제함
- 사용 방법은 [code-quality README](./code-quality/README.md) 참고

---

## 📚 개발자 가이드 (이 저장소 수정 시)

1. 모듈 코드 수정
2. 버전 업데이트 (`build.gradle.kts` → `version = "x.y.z"`)
3. GitHub Packages 퍼블리시
   ```bash
   ./gradlew clean publish
   ```
4. 소비자 프로젝트에서 플러그인 버전 업 → 변경된 규칙 자동 반영
