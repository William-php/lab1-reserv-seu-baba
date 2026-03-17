<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.reserve_seu_baba.models.Campo" %>

<% 
    // Recupera o objeto do request
    Campo campo = (Campo) request.getAttribute("campo");
    
    // Se for nulo (cadastro novo), inicializamos para evitar NullPointerException nos getters
    if (campo == null) {
        campo = new Campo();
        // Garantimos que valores numéricos iniciem zerados para não quebrar o HTML
        campo.setLarguraCampo(0.0f);
        campo.setComprimentoCampo(0.0f);
        campo.setPrecoCampo(0.0f);
    }
    
    // Define isEdit baseado na existência de um ID (maior que zero)
    boolean isEdit = (campo.getIdCampo() > 0);
%>

<form method="post" action="${pageContext.request.contextPath}/CampoController">
    <input type="hidden" name="action" value="<%= isEdit ? "update" : "add" %>"/>
    
    <% if (isEdit) { %>
        <input type="hidden" name="idCampo" value="<%= campo.getIdCampo() %>"/>
        <h3>Editar Detalhes do Campo</h3>
    <% } else { %>
        <h3>Cadastrar Novo Campo</h3>
    <% } %>

    <label for="codCampo">Código/Identificação do Campo:</label>
    <input type="text" name="codCampo" id="codCampo" 
           placeholder="Ex: Arena-01"
           value="<%= isEdit ? (campo.getCodCampo() != null ? campo.getCodCampo() : "") : "" %>" required
           minLength="6"
           maxLength="6"
    >

    <label for="tipoGramado">Tipo de Gramado:</label>
        
    <select id="tipoGramado" name="tipoGramado">
    	<option value="Gramado Sintético">Gramado Sintético</option>
    	<option value="Gramado Natural">Gramado Natural</option>
    	<option value="Quadra">Quadra</option>
    </select>

    <div style="display: flex; gap: 15px;">
        <div style="flex: 1;">
            <label for="largura">Largura (metros):</label>
            <input type="number" step="0.01" name="largura" id="largura" 
                   value="<%= isEdit ? campo.getLarguraCampo() : "" %>" required>
        </div>
        <div style="flex: 1;">
            <label for="comprimento">Comprimento (metros):</label>
            <input type="number" step="0.01" name="comprimento" id="comprimento" 
                   value="<%= isEdit ? campo.getComprimentoCampo() : "" %>" required>
        </div>
    </div>

    <label for="preco">Preço por Hora (R$):</label>
    <input type="number" step="0.01" name="preco" id="preco" 
           placeholder="0.00"
           value="<%= isEdit ? campo.getPrecoCampo() : "" %>" required>

    <button type="submit">
        <%= isEdit ? "Atualizar Campo" : "Cadastrar Campo" %>
    </button>
</form>