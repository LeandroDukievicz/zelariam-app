# Zelariam

Repositório público do Zelariam. Ele reúne a landing page em Astro e o aplicativo Android nativo Cuidar+, escrito em Kotlin e Jetpack Compose. A referência visual do app é [`wireframe-zeraliam.html`](wireframe-zeraliam.html).

## Aplicativo Android

Abra este repositório no Android Studio ou compile pelo Gradle Wrapper com JDK e Android SDK instalados:

```bash
./gradlew :app:assembleDebug
```

Para instalar em um aparelho/emulador conectado:

```bash
./gradlew :app:installDebug
```

O APK de desenvolvimento fica em `app/build/outputs/apk/debug/app-debug.apk`. Rotinas, doses e ocorrências são exemplos fictícios e as alterações ficam somente em memória durante a sessão.

## Landing page

A landing page Astro está na raiz junto do `package.json`:

```bash
npm install
npm run dev
```

## Documentação do projeto

- [`roadmap.md`](roadmap.md) — fases, escopo e progresso.
- [`documentacao.md`](documentacao.md) — decisões técnicas e pendências.
- [`AGENTS.md`](AGENTS.md) — instruções de contribuição e limites da implementação.

O nome final do produto ainda está pendente; o app mantém “Cuidar+” para corresponder ao wireframe. Não use dados reais de saúde nesta etapa.
