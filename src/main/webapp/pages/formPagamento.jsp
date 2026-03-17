<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Recupera o valor total vindo do request como atributo
    Object objValorTotal = request.getAttribute("valorTotal");
    Object objIdReserva =  request.getAttribute("idReserva");
    
    
    
    // Garantia de que o valor não seja nulo para evitar erros na tela
    float valorTotal = (objValorTotal != null) ? (float) objValorTotal : 0.0f;
    int idReserva = (objIdReserva != null) ? (int) objIdReserva : 0;
%>

<div class="pagamento-container">
    <h3>Finalizar Pagamento da Reserva</h3>
    
    <div class="resumo-valor">
        <p>Valor Total a Pagar:</p>
        <h2 style="color: #2ecc71;">R$ <%= String.format("%.2f", valorTotal) %></h2>
    </div>

    <form method="post" action="${pageContext.request.contextPath}/ReservaController">
        <input type="hidden" name="action" value="confirmarPagamento">
        <input type="hidden" name="idReserva" value="<%= idReserva %>">
        <input type="hidden" name="valorTotal" value="<%= valorTotal %>">

        <div class="form-group">
            <label for="pagamento_reserva">Forma de Pagamento:</label>
            <select name="pagamento_reserva" id="pagamento_reserva" required class="form-control">
                <option value="">Selecione...</option>
                <option value="Pix">Pix (Aprovação imediata)</option>
                <option value="Cartao_Credito">Cartão de Crédito</option>
                <option value="Dinheiro">Dinheiro (Pagar no local)</option>
            </select>
        </div>

        <div class="alerta-pagamento" style="background: #f9f9f9; padding: 15px; border-radius: 8px; margin: 15px 0; border-left: 5px solid #3498db;">
            <p><small>Ao confirmar, o registro de pagamento será gerado com a data e hora atual do sistema.</small></p>
        </div>

        <button type="submit" class="btn-finalizar" style="background: #2ecc71; color: white; padding: 12px 25px; border: none; border-radius: 5px; cursor: pointer; width: 100%; font-size: 1.1em;">
            Confirmar e Finalizar Reserva
        </button>
    </form>
</div>