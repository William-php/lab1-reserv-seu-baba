<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>


<div class="login">
	<form method="post" action="${pageContext.request.contextPath}/UsuarioController">
		<h3>Login</h3>
		<input type="hidden" name="action" value="login"/>	
		<label for="email">Email: </label>
		<input type="email" name="email" id="email"/>
		
		<label for="senha">Senha: </label>
		<input type="password" name="senha" id="senha"/>
		<button type="submit">Login</button>
		
	</form>
	<br>
	<a href="pages/criarUsuarios.jsp?param=logout" style="text-decoration: none; color: #666">Criar conta</a>	
</div>	

