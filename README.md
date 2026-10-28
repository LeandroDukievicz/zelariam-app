# Cuidar+ Android

Aplicativo Android nativo em Kotlin, com interface baseada no wireframe standalone [`wireframe-zeraliam.html`](wireframe-zeraliam.html). O nome definitivo ainda está pendente; a interface mantém “Cuidar+” para corresponder à referência.

O roadmap e o levantamento estão em [`roadmap.md`](roadmap.md); decisões estão em [`documentacao.md`](documentacao.md). O app não deve receber dados reais de saúde nesta etapa.

## Desenvolvimento

Requer Android Studio/Android SDK, JDK compatível e Gradle Wrapper. Compile com `./gradlew :app:assembleDebug`; para instalar em emulador ou dispositivo conectado, use `./gradlew :app:installDebug`.

O APK de desenvolvimento sai em `app/build/outputs/apk/debug/app-debug.apk`.
