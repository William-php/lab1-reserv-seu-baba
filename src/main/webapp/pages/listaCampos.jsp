<%@page import="br.com.reserve_seu_baba.models.Usuario"%>
<%@page import="br.com.reserve_seu_baba.models.Campo"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<section class="dashboard-cards">
    <%        
        ArrayList<Campo> listaCampos = (ArrayList<Campo>) request.getAttribute("listaCampos");
        Usuario usuarioLogado = (Usuario) request.getAttribute("usuarioLogado");
        if (listaCampos != null && !listaCampos.isEmpty()) {
            // 2. O loop deve envolver todo o bloco HTML do card
            for (Campo campo : listaCampos) { 
    %>
        <div class="card">
            <div class="card-img" style="background-color: #ddd;">
            	<img alt="Campo de futebol" src="assets/imgs/fute-lab1.jpeg">
            </div>
           	<div class="card-body">
			    <h3><%= campo.getCodCampo() %></h3>
			    <p><%= campo.getTipoGramadoCampo() %> • <%= campo.getLarguraCampo() %>x<%= campo.getComprimentoCampo() %></p>
			    <span class="price">R$ <%= campo.getPrecoCampo() %> / hora</span>
			
			    <form action="${pageContext.request.contextPath}/ReservaController" method="post" style="margin-bottom: 10px;">
			        <input type="hidden" name="action" value="reserva">
			        <input type="hidden" name="idCampo" value="<%=campo.getIdCampo()%>">
			        <button type="submit" class="btn-reserva">Reservar Agora</button>
			    </form>
			
			    <% if (usuarioLogado != null && usuarioLogado.isAdmUsuario()) { %>
			        <form action="${pageContext.request.contextPath}/CampoController" method="post" 
			              onsubmit="return confirm('Deseja realmente excluir este campo?');"
			              style="background:none; padding:0; box-shadow:none; width:auto; display:block; text-align:center;">
			            
			            <input type="hidden" name="action" value="delete">
			            <input type="hidden" name="idCampo" value="<%=campo.getIdCampo()%>">
			            
			            <button type="submit" class="btn-delete" title="Excluir Campo">🗑️</button>
			        </form>
			    <% } %>
			</div>
        </div>	
    <% 
            } // Fim do for
        } else { 
    %>
        <div class="empty-state">
            <p>Nenhum campo disponível para reserva no momento.</p>
        </div>
    <% } %>
</section>

	