# Reserve Seu Babá — Documentação técnica

Este documento descreve as *views* (JSP), *controllers* (servlets) e *models* (POJOs) do projeto Reserve Seu Babá. O foco é oferecer um guia prático para desenvolvedores: assinaturas, parâmetros, formulários, fluxo de requisições e pontos de atenção.

## Sumário
- Introdução
- Estrutura do projeto
- Models (campos e métodos principais)
- Controllers (endpoints, `action`s, assinaturas e fluxo)
- Views (JSPs): formulários e campos
- DAOs: métodos principais
- Utilitários e configuração do BD
- Fluxos de exemplo (Reserva → Agenda → Pagamento)
- Problemas conhecidos e melhorias sugeridas

---

## Introdução
Aplicação web simples para reservar campos, agendar horários, adicionar participantes e confirmar pagamento. Implementada com Servlets/JSP e JDBC (MySQL).

## Estrutura principal
- `src/main/java/br/com/reserve_seu_baba/controllers` — Servlets/Controllers
- `src/main/java/br/com/reserve_seu_baba/models` — POJOs do domínio
- `src/main/java/br/com/reserve_seu_baba/dao` — Acesso ao banco (DAOs)
- `src/main/java/br/com/reserve_seu_baba/utils` — Utilitários (`ConnectionSQL`, `DataUtils`)
- `src/main/webapp/pages` — Views JSP (forms e listas)

Links rápidos (arquivos importantes):
- `src/main/java/br/com/reserve_seu_baba/controllers/UsuarioController.java`
- `src/main/java/br/com/reserve_seu_baba/controllers/ReservaController.java`
- `src/main/java/br/com/reserve_seu_baba/controllers/AgendaController.java`
- `src/main/java/br/com/reserve_seu_baba/controllers/CampoController.java`
- `src/main/java/br/com/reserve_seu_baba/controllers/ParticipanteController.java`
- `src/main/java/br/com/reserve_seu_baba/models/Usuario.java`
- `src/main/java/br/com/reserve_seu_baba/models/Reserva.java`
- `src/main/java/br/com/reserve_seu_baba/models/Campo.java`
- `src/main/java/br/com/reserve_seu_baba/models/Agenda.java`
- `src/main/java/br/com/reserve_seu_baba/models/ListaParticipante.java`
- `src/main/webapp/pages/login.jsp`
- `src/main/webapp/pages/listaCampos.jsp`
- `src/main/webapp/pages/formAgenda.jsp`
- `src/main/webapp/pages/formPagamento.jsp`

## Models (resumo)
Para cada model abaixo listamos os campos principais e observações.

- `Usuario` (`src/main/java/.../models/Usuario.java`)
  - Campos: `int idUsuario`, `String nomeUsuario`, `String emailUsuario`, `String senhaUsuario`, `Timestamp dataNascimentoUsuario`, `boolean admUsuario`
  - Métodos: construtores, getters/setters. Usado para autenticação e identificação do dono da reserva.

- `Reserva` (`src/main/java/.../models/Reserva.java`)
  - Campos: `int idReserva`, `Usuario usuario`, `int pagamentoReserva` (status/flag), `Timestamp dataHoraPagamentoReserva`, `Timestamp dataHoraRegistroReserva`, `float valorTotalReserva`
  - Métodos: construtores, getters/setters. `createReserva` em DAO retorna `idReserva` gerado.

- `Campo` (`src/main/java/.../models/Campo.java`)
  - Campos: `int idCampo`, `String tipoGramadoCampo`, `float larguraCampo`, `float comprimentoCampo`, `String codCampo`, `float precoCampo`
  - Métodos: construtores, getters/setters.

- `Agenda` (`src/main/java/.../models/Agenda.java`)
  - Campos: `Timestamp dataHoraInicioAgenda`, `Timestamp dataHoraFimAgenda`, `Campo campo`, `Reserva reserva`
  - Métodos: construtores, getters/setters. Representa o agendamento gerado após criar a `Reserva`.

- `ListaParticipante` (`src/main/java/.../models/ListaParticipante.java`)
  - Campos: `int idListaParticipante`, `Reserva reserva`, `ArrayList<String> nomesListaParticipante`
  - Métodos: adicionar participante, getters/setters.

## Controllers — endpoints e `action`s
Os controllers recebem formulários e query params. Padrão: `POST` para ações que alteram estado; `GET` para exibir formulários/listas.

- `UsuarioController` (`UsuarioController.java`)
  - Métodos: `init()`, `doGet(HttpServletRequest, HttpServletResponse)`, `doPost(HttpServletRequest, HttpServletResponse)`
  - Actions comuns (via parâmetro `action`/query): `login` (autenticar), `add`/`edit` (criar ou editar usuário), `delete` (remover), `listarUsuarios` (ADM).

- `ReservaController` (`ReservaController.java`)
  - Métodos: `doGet`, `doPost`
  - Actions:
    - `reserva` — cria reserva básica: precisa de `idCampo` e usuário logado; chama `ReservaDAO.createReserva` e retorna `idReserva`.
    - `confirmarPagamento` — recebe `idReserva`, `valorTotal`, `pagamento_reserva` e atualiza reserva (pagamento efetuado).

- `AgendaController` (`AgendaController.java`)
  - Actions:
    - `agendar` — espera `idCampo`, `idReserva`, `dataInicio`, `dataFim` (campos do `formAgenda.jsp`). Cria `Agenda` e grava via `AgendaDAO.createAgenda`. Calcula horas/duração e `valorTotal` usando `DataUtils`.

- `CampoController` (`CampoController.java`)
  - Actions: `add` (criar), `update` (atualizar), `delete` (remover), `listarCampos` (exibir lista). Formulário: `formCampo.jsp`.

- `ParticipanteController` (`ParticipanteController.java`)
  - Actions: `salvarParticipantes` — recebe `idReserva` e múltiplos `nomes` (campo de formulário `name="nomes"` repetido). Persiste via `ListaParticipanteDAO`.

Observação: controllers frequentemente leem `action` via `request.getParameter("action")` ou via query string; recomenda-se usar `request.getParameter(...)` sempre para robustez.

## Views (JSP) — formulários e campos importantes
- `login.jsp`
  - Form: `POST` para `UsuarioController` com `action=login`
  - Campos: `email`, `senha`

- `criarUsuarios.jsp`
  - Form: `POST` para `UsuarioController` (`action=add` ou `action=edit`)
  - Campos: `nome`, `email`, `senha`, `dataNascimento`

- `listaCampos.jsp`
  - Mostra tabela de `Campo` com botão para reservar; botão faz `POST` para `ReservaController?action=reserva` com `idCampo` oculto.

- `formCampo.jsp`
  - Form: `POST` para `CampoController` com `action=add` ou `action=update`
  - Campos: `codCampo`, `tipoGramado`, `largura`, `comprimento`, `preco`, (hidden `idCampo` em edição)

- `formAgenda.jsp`
  - Form: `POST` para `AgendaController?action=agendar`
  - Campos: hidden `idCampo`, hidden `idReserva`, `dataInicio` (`datetime-local`), `dataFim` (`datetime-local`)

- `formPagamento.jsp`
  - Form: `POST` para `ReservaController?action=confirmarPagamento`
  - Campos: hidden `idReserva`, hidden `valorTotal`, select `pagamento_reserva` (opção de pagamento)

- `formParticipante.jsp`
  - Form: `POST` para `ParticipanteController?action=salvarParticipantes`
  - Campos: hidden `idReserva`, múltiplos inputs `name="nomes"`

- `listaParticipantes.jsp`, `listaReservaAgendadas.jsp`, `listaUsuarios.jsp` — listas que exibem dados conforme variáveis em `request`/`session`.

## DAOs (resumo dos métodos principais)
- `ReservaDAO`
  - `int createReserva(Reserva reserva)` — cria e retorna `idReserva` gerado.
  - `Reserva getReservaById(int reservaId, int usuarioId)`
  - `int updateReserva(Reserva, int id)`
- `AgendaDAO`
  - `int createAgenda(Agenda agenda)`
  - `ArrayList<Agenda> getAllAgenda(int idUsuario)`
  - Observação: `getAgendaById(int)` não implementado — atenção.
- `CampoDAO`
  - CRUD: `createCampo`, `getCampoById`, `getAllCampos`, `updateCampo`, `deleteCampo`
- `ListaParticipanteDAO`
  - `int createListaParticipante(ListaParticipante, int idReserva)` (batch insert), `getListaByReservaId`, `deleteListaByReservaId`
- `UsuarioDAO`
  - CRUD e `Usuario login(String email,String senha)`

## Utilitários
- `ConnectionSQL` — cria conexão JDBC para MySQL. Observação: credenciais hard-coded (`jdbc:mysql://localhost:3306/reserve_seu_baba_2`, usuário/senha no código). Externalizar em arquivo `.properties` ou variáveis de ambiente.
- `DataUtils` — parsing de timestamp e cálculo de diferença de horas (usa `LocalDateTime` e `Duration`). Utilizado por `AgendaController` para calcular horas e valor.

## Fluxo de exemplo: criar Reserva → Agendar → Pagamento
1) Usuário clica "Reservar" em `listaCampos.jsp` → formulário POST para `/ReservaController?action=reserva` com `idCampo`.
2) `ReservaController` cria `Reserva` via `ReservaDAO.createReserva` e obtém `idReserva`; redireciona para `home.jsp?param=reservaAgenda` (inclui `formAgenda.jsp`).
3) Usuário preenche `formAgenda.jsp` (`dataInicio`, `dataFim`) e envia POST para `/AgendaController?action=agendar` com `idCampo` e `idReserva`.
4) `AgendaController` cria `Agenda`, calcula duração e `valorTotal` usando `DataUtils`, grava via `AgendaDAO.createAgenda` e encaminha para `home.jsp?param=pagamento` (inclui `formPagamento.jsp`) com `valorTotal` e `idReserva`.
5) `formPagamento.jsp` envia POST para `/ReservaController?action=confirmarPagamento` com `idReserva`, `valorTotal`, `pagamento_reserva`.
6) `ReservaController` atualiza a `Reserva` (pagamento) via `ReservaDAO.updateReserva`.

## Problemas conhecidos e melhorias
- Evitar comparar `String` com `==` em DAOs; usar `equals()`.
- Substituir uso de `request.getQueryString().substring(...)` por `request.getParameter(...)`.
- Implementar `AgendaDAO.getAgendaById(int)` se necessário.
- Externalizar credenciais do BD (`ConnectionSQL`) para `.properties` ou variáveis de ambiente.
- Adicionar validação de inputs nos forms (server-side) e tratamento de erros/flash messages.

## Como contribuir / testar localmente
1. Configure o banco MySQL e crie o schema (tabelas conforme DAOs). (Não há script SQL no repositório — crie conforme mapeamento dos DAOs.)
2. Ajuste `ConnectionSQL` para usar credenciais locais (ou atualize para ler `db.properties`).
3. Construa e rode a aplicação no Tomcat/Payara/Jetty conforme seu fluxo habitual.

Comandos git sugeridos para adicionar esta documentação:
```bash
git add README.md
git commit -m "docs: documentação técnica de Models, Controllers e Views"
git push
```

---
Se quiser, eu: (a) gero arquivos separados em `docs/` (models.md, controllers.md, views.md); (b) extraio exemplos de payloads completos; ou (c) abro um PR com este README. Qual você prefere agora?
