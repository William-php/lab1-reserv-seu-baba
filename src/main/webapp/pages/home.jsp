<%@page import="br.com.reserve_seu_baba.models.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
    // Verifica se o usuário é administrador
    boolean isAdm = (usuario != null && usuario.isAdmUsuario()); 
%>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reserva seu Baba - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css">
</head>
<body>

    <div class="dashboard-container">
        <aside class="sidebar">
            <div class="logo">
                <h2>Reserve Seu Baba</h2>                
                <h3>Bem vindo, <%=usuario.getNomeUsuario()%></h3>
            </div>
            <nav class="menu">
                <a href="${pageContext.request.contextPath}/pages/home.jsp">Início</a>
                
                <% if (isAdm) { // Menu visível apenas para ADM %>
                    <a href="${pageContext.request.contextPath}/CampoController?param=adicionarCampo">Cadastrar Campo</a>                    
                    <a href="${pageContext.request.contextPath}/UsuarioController?param=listaUsuarios">Lista Usuários</a>
                <% } %>
				<a href="${pageContext.request.contextPath}/pages/home.jsp">Ver minhas Reservas</a>
                <a href="${pageContext.request.contextPath}/CampoController?param=listarCampos">Lista Campos</a>
                <a href="${pageContext.request.contextPath}/UsuarioController?param=editarPerfil">Editar Perfil</a>
                
                <%-- Links técnicos ocultos permanecem para o fluxo do sistema --%>
                <a href="${pageContext.request.contextPath}/ReservaController?param=reservaAgenda" hidden="true"></a>
                <a href="${pageContext.request.contextPath}/ReservaController?param=pagamento" hidden="true"></a>
                <a href="${pageContext.request.contextPath}/ParticipanteController?param=adicionarParticipante" hidden="true"></a>
                <a href="${pageContext.request.contextPath}/ParticipanteController?param=listaParticipantes" hidden="true"></a>
            </nav>
            <div class="sidebar-footer">
    			<a href="${pageContext.request.contextPath}/UsuarioController?param=logout">Sair</a>
			</div>
        </aside>

        <main class="main-content">
            <header class="top-bar">
                <h1>Bem-vindo, <%= isAdm ? "Administrador" : "Jogador" %>!</h1>
            </header>
            <%
                String pages = request.getParameter("param");
                if (pages == null) {
                    pages = "inicio";
                }

                switch (pages) {
                    // Rotas restritas ao ADM
                    case "listaUsuarios":
           %>
           				<jsp:include page="/pages/listaUsuarios.jsp"/>
           <%
                    	break;
                    case "adicionarCampo":
                    case "editarCampo":
                        if (isAdm) {
                            String path = pages.equals("listaUsuarios") ? "/pages/listaUsuarios.jsp" : "/pages/formCampo.jsp";
            %>
                            <jsp:include page="<%= path %>"/>
            <%
                        } else {
                            response.sendRedirect("home.jsp"); // Redireciona jogador se tentar acessar via URL
                        }
                        break;

                    // Rotas comuns
                    case "editarPerfil":
            %>
                        <jsp:include page="/pages/criarUsuarios.jsp"/>
            <%
                        break;
                    case "listarCampos":
            %>
                        <jsp:include page="/pages/listaCampos.jsp"/>
            <%
                        break;
                    case "reservaAgenda":
            %>
                        <jsp:include page="/pages/formAgenda.jsp"/>
            <%
                        break;
                    case "pagamento":
            %>
                        <jsp:include page="/pages/formPagamento.jsp"/>
            <%
                        break;
                    case "adicionarParticipante":
            %>
            			<jsp:include page="/pages/formParticipante.jsp"/>
            <%
            			break;
                    case "listaParticipantes":
            %>
            			<jsp:include page="/pages/listaParticipantes.jsp"/>
            <%
            			break;
                    default:
            %> 
                        <jsp:include page="/pages/listaReservaAgendadas.jsp"/>
            <%        
                        break;
                }
            %>
        </main>
    </div>
</body>
</html>