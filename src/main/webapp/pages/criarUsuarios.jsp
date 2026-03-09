<%@page import="br.com.reserve_seu_baba.models.Usuario"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<% 
    // Recupera o usuário da sessão para decidir se é EDIÇÃO ou CADASTRO
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    boolean isEdit = (usuarioLogado != null);
%>
<head>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/index.css">
</head>
<div class="login">
	<form method="post" action="${pageContext.request.contextPath}/UsuarioController">
    <input type="hidden" name="action" value="<%= isEdit ? "edit" : "add" %>"/>
    
    <% if (isEdit) { %>
        <input type="hidden" name="id" value="<%= usuarioLogado.getIdUsuario() %>"/>
        <h3>Editar Meu Perfil</h3>
    <% } else { %>
        <h3>Criar Nova Conta</h3>
    <% } %>

    <label for="nome">Nome: </label>
    <input type="text" name="nome" id="nome" 
           value="<%= isEdit ? usuarioLogado.getNomeUsuario() : "" %>" required>

    <label for="email">Email: </label>
    <input type="email" name="email" id="email" 
           value="<%= isEdit ? usuarioLogado.getEmailUsuario() : "" %>" required>
        
    <label for="senha">Senha: </label>
    <input type="password" name="senha" id="senha" 
           placeholder="<%= isEdit ? "Nova senha (deixe vazio para manter)" : "" %>" 
           <%= isEdit ? "" : "required" %>>
        
    <% if (!isEdit) { %>
        <label for="dataNascimento">Data Nascimento: </label>
        <input type="datetime-local" name="dataNascimento" id="dataNascimento" required/>
    <% } %>
        
    <button type="submit"><%= isEdit ? "Salvar Alterações" : "Cadastrar" %></button>    
</form>
	
</div>
