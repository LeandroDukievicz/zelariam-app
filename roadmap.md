# Roadmap — Cuidar+ / Zelariam

**Atualizado em:** 27 de outubro de 2026  
**Estado atual:** começo da implementação Android; o workspace contém o wireframe standalone, sem aplicativo nativo, backend ou dados persistidos.  
**Próxima entrega:** portar para Kotlin/Jetpack Compose as 15 telas do wireframe, com navegação local e dados demonstrativos.

## Levantamento do ponto de partida

### Evidência no workspace

- [`wireframe-zeraliam.html`](wireframe-zeraliam.html) é um HTML standalone de aproximadamente 1,5 MB. Ele embute React, ReactDOM, Babel, fontes e código do canvas de apresentação.
- A referência contém 15 telas de celular, mais um artboard de identidade visual: onboarding; cadastro da pessoa cuidada; Hoje; rotina; medicamentos; registro de dose; ocorrências; sinais vitais; agenda; cuidadores; família e permissões; alertas; relatório médico; modo cuidador; modo idoso.
- A composição usa artboards de 390 × 844 px, tema escuro selecionado por padrão, cartões arredondados, ícones lineares, tons terrosos e as famílias DM Sans e DM Serif Display.
- O código de tela é predominantemente estático. As interações do arquivo original pertencem sobretudo ao canvas de apresentação e seus ajustes visuais; não constituem navegação, persistência ou regras funcionais do app.
- O protótipo usa uma pessoa e exemplos de saúde com nomes, endereço, telefone, medicamentos, ocorrências e medições. Esses dados devem permanecer somente como exemplos fictícios até serem substituídos por fixtures neutras.
- Não existe projeto Android, configuração Gradle, API, autenticação, banco, política de acesso, sincronização, canal de notificação ou processo de publicação neste workspace.

### Convenções aproveitadas de outros projetos

A organização em fases, bloqueios, critérios de saída e separação entre direção visual e arquitetura foi usada como referência nos roadmaps de `SintomIA`, `ninou-app` e `Economiz.AI`. O conteúdo deste roadmap foi refeito para este projeto e não presume que as decisões desses outros aplicativos se apliquem aqui.

## Decisões já registradas

- Kotlin para o aplicativo Android — solicitado pelo proprietário.
- Implementação nativa e front-end fiel ao wireframe — solicitado pelo proprietário.
- Depois da UI, implementar o código local do roadmap que não dependa de revisão — autorizado pelo proprietário.
- Manter `documentacao.md` como histórico de decisões e fazer um commit por feature — solicitado pelo proprietário.
- Jetpack Compose como tecnologia de UI — escolha técnica de implementação para portar os artboards em Kotlin; registrada em [`documentacao.md`](documentacao.md).
- Até a escolha final de marca, manter “Cuidar+” na UI porque é o nome escrito no wireframe.

## Fase 0 — Definição do produto e limites

**Estado:** parcial. Kotlin, Android nativo e fidelidade ao wireframe estão definidos. As decisões abaixo continuam com o proprietário.

- [x] Confirmar Kotlin e app Android nativo.
- [x] Confirmar o wireframe como referência visual estrita.
- [ ] Escolher o nome do produto: “Cuidar+” ou “Zelariam”; harmonizar também o nome do arquivo, pacote Android e materiais.
- [ ] Definir o perfil principal que precisa ter sucesso no primeiro piloto e quem inicia/coordena o grupo de cuidado.
- [ ] Confirmar quais fluxos, além do front-end das 15 telas, fazem parte do MVP funcional.
- [ ] Aprovar o limite clínico e revisar os textos que envolvam medicamentos, alertas, sinais vitais e urgência.
- [ ] Definir se e quando dados reais serão tratados; registrar finalidade, acesso, retenção e exclusão após revisão apropriada de privacidade/jurídica.
- [ ] Definir recrutamento, suporte, métrica e critérios de sucesso/parada antes de qualquer piloto.
- [ ] Decidir backend, contas, sincronização, notificações reais e hospedagem quando houver escopo aprovado.
- [ ] Confirmar o repositório remoto de publicação. O único repositório correspondente encontrado até agora chama-se `Zelariam-Landing-Page`; não presumir que o app deve ser publicado nele.

**Critério de saída:** decisões de produto e piloto documentadas sem depender de inferências do código.

## Fase 1 — Fundação Android

**Estado:** parcial. O módulo `app` já abre em Kotlin/Compose e compila; o tema e os componentes da referência ainda serão portados.

- [x] Criar projeto Android Kotlin com Gradle Wrapper, aplicação mínima e configuração local reproduzível.
- [x] Configurar versões fixas em catálogo Gradle; manter Java toolchain e SDK compatíveis com o Android SDK disponível.
- [x] Criar tema Compose, tokens de cor, escala tipográfica, cartões, botões, badges, avatares e ícones compartilhados.
- [x] Incorporar DM Sans e DM Serif Display do material de referência e incluir suas licenças OFL.
- [ ] Criar navegação local para todos os artboards sem serviço externo.

**Critério de saída:** o app instala/abre localmente e os elementos básicos reproduzem o sistema visual do wireframe.

**Evidência atual:** `./gradlew :app:assembleDebug --no-daemon` concluiu com `BUILD SUCCESSFUL`; APK de desenvolvimento em `app/build/outputs/apk/debug/app-debug.apk`. O launcher ainda é somente uma tela temporária.

## Fase 2 — Portar o front-end fiel

**Estado:** não iniciado.

- [ ] Onboarding familiar e cadastro da pessoa cuidada.
- [ ] Hoje e rotina diária.
- [ ] Medicamentos e registro de dose.
- [ ] Ocorrências e sinais vitais.
- [ ] Agenda e consultas.
- [ ] Cuidadores/plantões e família/permissões.
- [ ] Central de alertas e relatório médico.
- [ ] Modo cuidador e modo idoso simplificado.
- [ ] Navegação inferior e ligações entre telas conforme a referência.
- [ ] Preservar as duas paletas apresentadas pelo sistema visual, mantendo o modo escuro inicial conforme o wireframe.

**Critério de saída:** as 15 telas têm correspondência navegável; textos, cartões, cores, hierarquia e estados visuais foram portados, sem conteúdo de produção inventado.

## Fase 3 — Interações locais de demonstração

**Estado:** pode iniciar após a Fase 2; não depende de conta ou serviço.

- [ ] Criar um único estado demonstrativo em memória para rotina, doses e ocorrências.
- [ ] Permitir marcar/desmarcar tarefa e registrar, adiar ou corrigir uma dose fictícia.
- [ ] Permitir criar, filtrar e desfazer ocorrência demonstrativa.
- [ ] Refletir os estados em Hoje, rotina, medicamentos, alertas e modos especiais.
- [ ] Indicar que alterações só duram na sessão e reiniciam ao reabrir/recarregar.

**Critério de saída:** as ações simuladas têm resultado visual coerente e continuam claramente separadas de registro real de saúde.

## Fase 4 — Preparação técnica para decisões futuras

**Estado:** futuro; avançar apenas nos itens que não exijam as decisões da Fase 0.

- [ ] Separar modelos de demonstração das composables e centralizar fixtures.
- [ ] Definir estados de carregamento, vazio, erro e confirmação para fluxos futuros sem ligá-los a serviços reais.
- [ ] Documentar limites de acessibilidade e adaptação em telas menores.
- [ ] Reavaliar arquitetura depois da escolha de MVP, público e plataforma de distribuição.
- [ ] Fazer uma revisão de fidelidade visual no emulador/dispositivo antes de declarar a UI pronta.

## Fase 5 — MVP funcional, privacidade e piloto

**Estado:** futuro e dependente de decisões de produto e revisão apropriada; este roadmap não autoriza coleta real.

- [ ] Aprovar escopo de MVP e arquitetura de dados.
- [ ] Definir identidade, autenticação, grupos, papéis e trilha de alterações.
- [ ] Definir base legal aplicável, minimização, retenção, exportação e exclusão de dados.
- [ ] Implementar armazenamento e sincronização somente com contrato aprovado.
- [ ] Implementar lembretes reais somente após definir plataforma, estados de entrega e recuperação de falha.
- [ ] Fazer revisão clínica, jurídica, de segurança e privacidade apropriada antes de qualquer dado de saúde real.
- [ ] Recrutar participantes e iniciar piloto somente após pré-condições, suporte e critérios de parada aprovados.

## Fora do escopo imediato

- Backend, API, login, armazenamento persistente, convites reais e sincronização.
- Permissões e notificações reais, mensagens externas, analytics ou publicação em loja.
- Recomendações clínicas, interpretação de sinais vitais ou cálculo/alteração de doses.
- Uso de dados reais de saúde no app em desenvolvimento ou em commits.
