<%@page import="br.com.reserve_seu_baba.models.Agenda"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<section class="dashboard-cards">
    <%        
        // Recupera a lista de agendas enviada pelo seu controlador
        ArrayList<Agenda> listaAgendas = (ArrayList<Agenda>) session.getAttribute("reservasAgendadas");
        
        if (listaAgendas != null && !listaAgendas.isEmpty()) {
            for (Agenda agenda : listaAgendas) { 
                // Lógica de Status: Se houver uma reserva vinculada, o status é "Reservado"
                boolean isReservado = (agenda.getReserva() != null);
                String status = isReservado ? "Reservado" : "Disponível";
                String statusClass = isReservado ? "status-reserved" : "status-available";
    %>
        <div class="card">
            
            <div class="card-body">
                <h3>Campo: <%= agenda.getCampo().getCodCampo() %></h3>
                
                <p class="status-label">
                    Status: <span class="<%= statusClass %>"><strong><%= status %></strong></span>
                </p>
                <p class="status-label">
                    Id: <span class="<%= statusClass %>"><strong><%= agenda.getReserva().getIdReserva() %></strong></span>
                </p>
                
                <p><small>Início: <%= new java.text.SimpleDateFormat("dd/MM HH:mm").format(agenda.getDataHoraInicioAgenda()) %></small></p>

                <form action="${pageContext.request.contextPath}/ParticipanteController" method="post">
                	<input type="hidden" name="action" id="action" value="adicionarParticipante"/>
                    <input type="hidden" name="idAgenda"  value="<%= agenda.getReserva() != null ? agenda.getReserva().getIdReserva() : "" %>">
                    <input type="hidden" name="idReserva"  value="<%= agenda.getReserva().getIdReserva() %>"/>
                    <button type="submit" class="btn-reserva" <%= !isReservado ? "disabled style='background-color: #ccc; cursor: not-allowed;'" : "" %>>
                        Adicionar Participantes
                    </button>
                    <br>
                    <button type="submit" class="btn-reserva btn-ver"   <%= !isReservado ? "disabled style='background-color: #ccc; cursor: not-allowed;'" : "" %>>
                        Ver Participantes
                    </button>
                </form>
            </div>
        </div>	
    <% 
            } 
        } else { 
    %>
        <div class="empty-state">
            <p>Nenhuma agenda encontrada para os critérios selecionados.</p>
        </div>
    <% } %>
</section>
<script>
    // Usamos querySelectorAll para pegar todos os botões "Ver"
    document.querySelectorAll(".btn-ver").forEach(botao => {
        botao.addEventListener("click", (event) => {
            // 1. Localiza o formulário pai do botão clicado
            event.preventDefault();                       
            const form = event.target.closest("form");            
            // 2. Localiza o input hidden dentro DESTE formulário
            const inputAction = document.getElementById("action");            
            
            
            // 3. Altera o valor
            form[0].value = "verParticipantes";
            
            // 4. Envia manualmente após a alteração
            form.submit();
        });
    });
</script>	
<style>
    .status-available { color: #27ae60; }
    .status-reserved { color: #e74c3c; }
    .status-label { margin-bottom: 15px; font-size: 1.1em; }
</style>