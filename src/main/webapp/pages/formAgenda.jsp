<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="br.com.reserve_seu_baba.models.Agenda" %>

<% 
    // Recupera como Object e verifica se é nulo antes de converter
    Object objCampo = request.getAttribute("idCampo");
    Object objReserva = request.getAttribute("idReserva");    

    // Se forem nulos, inicializamos com 0 para evitar erro de exibição
    int idCampo = (objCampo != null) ? (int) objCampo : 0;
    int idReserva = (objReserva != null) ? (int) objReserva : 0;
%>

<form id="formAgenda" method="post" action="${pageContext.request.contextPath}/AgendaController">
    <input type="hidden" name="action" value="agendar">
    
    <input type="hidden" name="idCampo" value="<%= idCampo %>">
    <input type="hidden" name="idReserva" value="<%= idReserva %>">

    <h3>Agendar Horário do Baba</h3>

    <div class="form-group">
        <label for="dataInicio">Data e Hora de Início:</label>
        <input type="datetime-local" id="dataInicio" name="dataInicio" required>
    </div>

    <div class="form-group">
        <label for="dataFim">Data e Hora de Término:</label>
        <input type="datetime-local" id="dataFim" name="dataFim" required>
        <small id="erroData" style="color: red; display: none;">
            A reserva deve ter duração mínima de 1 hora.
        </small>
    </div>
	
    <button type="submit" id="btnSalvar">Confirmar Agendamento</button>
</form>
<br>
<a href="javascript:history.back()" class="btn-reserva">Voltar</a>

<script>
    const form = document.getElementById('formAgenda');
    const dataInicio = document.getElementById('dataInicio');
    const dataFim = document.getElementById('dataFim');
    const erroMsg = document.getElementById('erroData');

    form.addEventListener('submit', function(e) {
        const inicio = new Date(dataInicio.value);
        const fim = new Date(dataFim.value);
        
        // Diferença em milissegundos (1 hora = 3600000 ms)
        const diff = fim - inicio;

        if (diff < 3600000) {
            e.preventDefault(); // Impede o envio do formulário
            erroMsg.style.display = 'block';
            dataFim.style.borderColor = 'red';
        } else {
            erroMsg.style.display = 'none';
        }
    });
</script>