<%@page import="br.com.reserve_seu_baba.models.Usuario"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

    <div class="container">
        <header class="header-section">
            <h2>Gestão de Usuários</h2>
            <p>Visualize e gerencie os jogadores cadastrados no sistema.</p>
        </header>

        <div class="table-container">
            <%
                ArrayList<Usuario> listaUsuario = (ArrayList<Usuario>) request.getAttribute("listaUsuarios");
                if (listaUsuario != null && !listaUsuario.isEmpty()) {
            %>
                <table>
                    <thead>
                        <tr>
                            <th>Nome do Usuário</th>
                            <th>E-mail</th>
                            <th>Data de Nascimento</th>
                            <th class="text-center">Tipo</th>
                            <th>Excluir</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for (Usuario u : listaUsuario) { %>
                            <tr>	
                                <td class="font-bold"><%= u.getNomeUsuario() %></td>
                                <td><%=u.getEmailUsuario() %></td>
                                <td><%= u.getDataNascimentoUsuario() != null ? u.getDataNascimentoUsuario() : "Não informada" %></td>
                                <td class="text-center">
                                    <span class="badge <%= u.isAdmUsuario() ? "admin" : "player" %>">
                                        <%= u.isAdmUsuario() ? "ADM" : "Jogador" %>
                                    </span>
                                </td>
                                <td>
                                	<form method="post" action="${pageContext.request.contextPath}/UsuarioController">
                                		<input type="hidden" name="action" value="delete"/>
                                		<input type="hidden" name="idUsuario" value="<%=u.getIdUsuario()%>"/>
                                		<button type="submit" class="btn-delete">
                                			🗑️
                                		</button>
                                	</form>
                                </td>
                                
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% 
                } else { 
            %>
                <div class="empty-state">
                    <p>Nenhum usuário encontrado na base de dados.</p>
                </div>
            <% } %>
        </div>
    </div>
