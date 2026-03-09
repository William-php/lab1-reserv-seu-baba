<%@page import="br.com.reserve_seu_baba.models.Reserva"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    // Supondo que você passou a reserva selecionada para esta página
    Reserva reserva = (Reserva) request.getAttribute("reserva");
    // Se não houver reserva no request, podemos pegar um ID via parâmetro para testes
    String idReserva = (reserva != null) ? String.valueOf(reserva.getIdReserva()) : "0";
%>

<div class="main-content">
    <div class="top-bar">
        <h2>Lista de Participantes</h2>
    </div>

    <form method="post" action="${pageContext.request.contextPath}/ParticipanteController">
        <input type="hidden" name="action" value="salvarParticipantes">
        
        <input type="hidden" name="idReserva" value="<%= idReserva %>"/>

        <h3>Adicionar Jogadores</h3>
        <p style="font-size: 0.8rem; color: #666; margin-bottom: 15px;">
            Reserva ID: #<%= idReserva %>
        </p>

        <div id="container-participantes">
            <label>Nomes dos Participantes:</label>
            <div class="input-group" style="display: flex; gap: 10px;">
                <input type="text" name="nomes" placeholder="Nome do jogador" required>
            </div>
        </div>

        <button type="button" id="btn-add-nome"  style="margin-bottom: 20px; width: auto;">
            + Adicionar outro nome
        </button>

        <button type="submit">Salvar Lista</button>        
    </form>
</div>

<script>
    // Script para adicionar novos campos de input sem recarregar a página
    document.getElementById('btn-add-nome').addEventListener('click', () => {
        const container = document.getElementById('container-participantes');
        
        const div = document.createElement('div');
        div.className = 'input-group';
        div.style.display = 'flex';
        div.style.gap = '10px';
        div.style.marginBottom = '5px';

        div.innerHTML = `
            <input type="text" name="nomes" placeholder="Nome do jogador" required>
            <button type="button" onclick="this.parentElement.remove()" 
                    style="background-color: #dc3545; padding: 0 15px; margin-bottom: 20px;">X</button>
        `;
        
        container.appendChild(div);
    });
</script>