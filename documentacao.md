# Documentação e decisões — Cuidar+ / Zelariam

Este arquivo registra o que foi decidido, quem decidiu e o que ainda aguarda escolha. Decisões do proprietário prevalecem sobre suposições do protótipo. Para progresso por entrega, veja [`roadmap.md`](roadmap.md).

## Registro de decisões

### D-001 — Plataforma e linguagem

- **Data:** 27/10/2026
- **Decisão:** criar um aplicativo Android nativo escrito em Kotlin.
- **Origem:** decisão explícita do proprietário.
- **Consequência:** o HTML será referência; a interface será portada para código Android.

### D-002 — Fidelidade do front-end

- **Data:** 27/10/2026
- **Decisão:** implementar o front-end seguindo estritamente e fielmente [`wireframe-zeraliam.html`](wireframe-zeraliam.html).
- **Origem:** decisão explícita do proprietário.
- **Consequência:** preservar as 15 telas, textos, cores, tipografia, hierarquia, cartões, estados e marca do wireframe; mudanças de estilo precisam de instrução do proprietário.

### D-003 — Trabalho autônomo após a UI

- **Data:** 27/10/2026
- **Decisão:** ao concluir o front-end, avançar no código do roadmap que não dependa de revisão do proprietário.
- **Origem:** autorização explícita do proprietário.
- **Limite:** isso não aprova decisões de marca, escopo, clínica, tratamento de dados, credenciais, serviços externos ou publicação.

### D-004 — Histórico de decisões e commits

- **Data:** 27/10/2026
- **Decisão:** manter este arquivo e fazer um commit a cada feature concluída.
- **Origem:** instrução explícita do proprietário.
- **Estado do remoto:** após confirmação explícita do proprietário, o repositório público `LeandroDukievicz/Zelariam-Landing-Page` foi renomeado para [`LeandroDukievicz/zelariam-app`](https://github.com/LeandroDukievicz/zelariam-app). A landing page foi preservada junto do aplicativo Android; os cinco commits de implementação foram enviados ao branch `main`.

### T-001 — Framework de interface

- **Data:** 27/10/2026
- **Decisão técnica de implementação:** usar Jetpack Compose para construir a interface nativa Kotlin.
- **Origem:** escolha de implementação compatível com o pedido do proprietário e com a natureza declarativa das telas de referência.
- **Impacto:** centralizar tokens e componentes compartilhados; manter as telas organizadas por fluxo.
- **Revisão:** pode ser reavaliada se o proprietário pedir outra abordagem ou surgir requisito técnico que Compose não atenda.

### T-002 — Dados na etapa de UI

- **Data:** 27/10/2026
- **Decisão técnica provisória:** usar somente dados locais demonstrativos; não criar chamadas de rede, backend ou persistência de saúde nesta etapa.
- **Origem:** estado observado no wireframe e ausência de decisões de produção.
- **Revisão:** implementação persistente depende de decisão de escopo, privacidade e arquitetura.

### T-003 — Fundação técnica Android

- **Data:** 27/10/2026
- **Decisão técnica de implementação:** Android Gradle Plugin 9.4.1, Gradle Wrapper 9.6.0, Kotlin 2.4.21, Compose BOM 2026.09.00, Activity Compose 1.13.0, Navigation Compose 2.10.2, `compileSdk`/`targetSdk` 37 e `minSdk` 26.
- **Origem:** configuração inicial baseada na documentação oficial consultada e nos SDKs instalados no ambiente.
- **Motivo:** começar com uma configuração Android atual, versionada e compatível com a fundação Compose.
- **Verificação:** `./gradlew :app:assembleDebug --no-daemon` concluiu com sucesso em 27/10/2026.
- **Revisão:** revalidar versões e requisitos de distribuição antes de publicar na loja.

### T-004 — Tokens e fontes da referência

- **Data:** 27/10/2026
- **Decisão técnica de implementação:** extrair do wireframe as paletas clara/escura e as fontes DM Sans e DM Serif Display; expor componentes Compose compartilhados para texto, cartão, botão, badge, avatar e ícone.
- **Origem:** inspeção direta do HTML fornecido.
- **Licenças:** fontes incluídas em TTF com as respectivas licenças SIL Open Font License em `app/src/main/assets/fonts-licenses/`.
- **Estado:** tokens, fontes, componentes-base e telas Compose implementados; build debug concluído com sucesso. A revisão visual em dispositivo permanece pendente porque não havia dispositivo/emulador conectado no ambiente.

### T-005 — Navegação e telas Compose

- **Data:** 27/10/2026
- **Decisão técnica de implementação:** organizar as 15 telas do wireframe por fluxo Compose e usar Navigation Compose para ligações locais, com onboarding como início e barra inferior nas áreas principais.
- **Origem:** decisão técnica para atender ao port nativo solicitado sem backend.
- **Estado:** implementado; confirmação visual final depende de rodar em emulador ou aparelho.

### T-006 — Interações locais de demonstração

- **Data:** 27/10/2026
- **Decisão técnica de implementação:** manter um `CareDemoState` compartilhado em memória para tarefas da rotina, estado demonstrativo da dose de Atenolol e ocorrências. Permitir criar, filtrar por categoria e desfazer a última ocorrência adicionada.
- **Origem:** fase 3 do roadmap, autorizada pelo proprietário após a implementação da UI.
- **Limites:** não há persistência local, chamadas de rede ou integração. Os exemplos do wireframe continuam sendo fictícios. A confirmação temporária informa que as mudanças valem somente durante a sessão.
- **Reflexos visuais:** Hoje, rotina, lista de medicamentos, alertas e modos cuidador/idoso refletem o estado compartilhado da dose e das tarefas.
- **Verificação:** `./gradlew :app:assembleDebug --no-daemon` concluiu com `BUILD SUCCESSFUL` em 27/10/2026.

## Questões pendentes do proprietário

### P-001 — Nome definitivo

O wireframe apresenta “Cuidar+”; a pasta se chama `zelariam` e o arquivo `wireframe-zeraliam.html`. A UI mantém “Cuidar+” até o proprietário escolher a marca e confirmar a grafia definitiva.

### P-002 — Público principal e coordenação

As telas incluem familiar, cuidador contratado e pessoa idosa. Falta definir qual perfil será o principal no primeiro piloto e quem cria/coordena o grupo.

### P-003 — Escopo funcional do MVP

As 15 telas serão implementadas no front-end conforme pedido. Isso não define, por si só, quais fluxos terão persistência ou entram no produto piloto.

### P-004 — Limite clínico

Definir se o produto ficará restrito a organizar e registrar informações e quem revisará linguagem sobre medicação, sinais vitais, alertas e urgência. Não inferir orientação médica a partir dos exemplos do wireframe.

### P-005 — Dados pessoais e de saúde

Antes de qualquer dado real, definir finalidade, minimização, pessoas com acesso, retenção, exportação, exclusão e revisão jurídica/de privacidade adequada. Nesta etapa usar somente fixtures fictícias e evitar dados identificáveis.

### P-006 — Backend e integrações

Definir se haverá conta, API, banco, sincronização, notificações, analytics e distribuição em loja, bem como responsáveis por custo e manutenção.

### P-007 — Pesquisa e piloto

Definir recrutamento, suporte, métrica de ativação, critérios de sucesso/parada e duração antes de iniciar qualquer piloto.

### P-008 — Repositório remoto (resolvido)

O proprietário escolheu o repositório público [`LeandroDukievicz/zelariam-app`](https://github.com/LeandroDukievicz/zelariam-app), renomeado de `Zelariam-Landing-Page` em 27/10/2026. A landing page Astro foi preservada e o histórico do app foi unido ao branch `main` sem sobrescrever os arquivos remotos.

## Referência técnica consultada

- [Configuração do Compose Compiler e dependências](https://developer.android.com/develop/ui/compose/setup-compose-dependencies-and-compiler)
- [Notas do Android Gradle Plugin 9.4](https://developer.android.com/build/releases/agp-9-4-0-release-notes)
- [Processo de versões Kotlin](https://kotlinlang.org/docs/releases.html)
- Roadmaps consultados como exemplos de organização: `SintomIA/roadmap.md`, `ninou-app/roadmap.md` e `Economiz.AI/docs/ROADMAP.md`.
