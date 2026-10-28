# Instruções do projeto Zelariam

## Fonte visual e escopo atual

- A referência visual oficial é [`wireframe-zeraliam.html`](wireframe-zeraliam.html). Preserve composição, hierarquia, cores, tipografia, textos, estados e os 15 artboards. Não redesenhe, simplifique ou acrescente uma linguagem visual própria sem decisão do proprietário.
- A implementação deve ser um aplicativo Android nativo em Kotlin. A interface está sendo portada para Jetpack Compose; HTML/WebView não substitui a implementação nativa.
- O wireframe usa a marca **Cuidar+**; a pasta se chama `zelariam` e o arquivo contém `zeraliam`. Preserve a marca do wireframe na interface até a escolha do nome definitivo.
- A primeira entrega é o front-end fiel e navegável. Depois dela, avance no código local previsto no roadmap quando não depender de decisão do proprietário, validação profissional, credenciais ou serviço externo.

## Limites de produto e dados

- Os nomes, contatos, medicamentos, horários, ocorrências e medições do wireframe são exemplos de demonstração. Trate-os como fictícios, não os reutilize como dados de pessoas reais e mantenha essa distinção explícita no código.
- Não implemente diagnóstico, interpretação clínica, recomendação de dose ou conduta médica. O escopo técnico atual é organizar e registrar informações conforme o wireframe.
- Não crie backend, autenticação, persistência de dados de saúde, sincronização, notificações reais, analytics, integrações ou publicação sem que as decisões correspondentes estejam resolvidas no [`roadmap.md`](roadmap.md) e registradas em [`documentacao.md`](documentacao.md).
- Não apresente conteúdo fictício como histórico real, alerta clínico ou garantia de entrega de lembrete.

## Implementação

- Mantenha a UI em português do Brasil.
- Centralize tokens visuais, tipografia, componentes e dados demonstrativos. Evite duplicar cores, estilos e conteúdo entre telas.
- Organize telas por fluxo/feature e mantenha a arquitetura pequena enquanto o app for apenas UI local. Não crie módulos, camadas ou dependências sem necessidade concreta.
- Preserve a proporção de referência do telefone (390 × 844 dp como base de composição), permitindo rolagem e adaptação em telas menores sem cortar conteúdo essencial.
- Represente os controles com componentes nativos acessíveis e áreas de toque adequadas; fidelidade visual não deve remover rótulos ou tornar ações inacessíveis.
- Não altere o arquivo HTML de referência para acomodar a implementação.

## Decisões, progresso e Git

- Registre toda decisão de produto ou arquitetura e sua origem em [`documentacao.md`](documentacao.md), distinguindo escolhas do proprietário de escolhas técnicas de implementação e de questões pendentes.
- Atualize [`roadmap.md`](roadmap.md) quando uma entrega mudar de estado, registrando dependências e critérios objetivos de conclusão.
- Faça um commit focado a cada feature concluída, como solicitado pelo proprietário; não misture features independentes no mesmo commit.
- O repositório remoto autorizado é `LeandroDukievicz/zelariam-app`, público. A landing page Astro existente foi preservada na raiz ao adicionar o app Android; não apague nem substitua esses arquivos sem instrução explícita.
- O proprietário autorizou publicar os commits de feature no branch `main` desse repositório.
- Nunca inclua credenciais, dados reais de saúde ou arquivos locais de configuração no Git.
