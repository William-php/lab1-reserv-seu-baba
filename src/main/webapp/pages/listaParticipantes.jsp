<%@page import="br.com.reserve_seu_baba.models.ListaParticipante"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<div class="container">
    <%
        // Recupera o objeto populado pelo seu DAO/Controller
        ListaParticipante lp = (ListaParticipante) request.getAttribute("listaParticipantes");
        
        if (lp != null) {
    %>
        <header class="header-section">
            <h2>Lista de Participantes</h2>
            <p>Reserva: <strong>#<%= lp.getReserva().getIdReserva() %></strong></p>
            <p>Responsável: <strong><%= lp.getReserva().getUsuario().getNomeUsuario() %></strong></p>
        </header>

        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th class="text-center">#</th>
                        <th>Nome do Jogador</th>
                    </tr>
                </thead>
                <tbody>
                    <% 
                        List<String> nomes = lp.getNomesListaParticipante();
                        if (nomes != null && !nomes.isEmpty()) {
                            int contador = 1;
                            for (String nome : nomes) { 
                    %>
                        <tr>
                            <td class="text-center"><%= contador++ %></td>
                            <td class="font-bold"><%= nome %></td>
                        </tr>
                    <% 
                            } 
                        } else { 
                    %>
                        <tr>
                            <td colspan="2" class="text-center">
                                <p>Nenhum participante confirmado para esta reserva.</p>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
            
            
        </div>
        <br>
        <a href="javascript:history.back()" class="btn-reserva">Voltar</a>
    <% 
        } else { 
    %>
        <div class="empty-state">
            <header class="header-section">
                <h2>Lista não encontrada</h2>
                <p>Não foi possível carregar os dados desta reserva.</p>
            </header>
            <br>
            <a href="javascript:history.back()" class="btn-reserva">Voltar</a>
        </div>
    <% } %>
</div>