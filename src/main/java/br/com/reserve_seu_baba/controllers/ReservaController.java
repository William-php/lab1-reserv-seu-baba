package br.com.reserve_seu_baba.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import br.com.reserve_seu_baba.dao.CampoDAO;
import br.com.reserve_seu_baba.dao.ReservaDAO;
import br.com.reserve_seu_baba.models.Campo;
import br.com.reserve_seu_baba.models.Reserva;
import br.com.reserve_seu_baba.models.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ReservaController")
public class ReservaController extends HttpServlet {
	ReservaDAO reservaDAO;
	
	public void init() throws ServletException {
		try {
			this.reservaDAO = new ReservaDAO();
		} catch (Exception e) {
			System.err.println("ReservaController | Servlet | Init: " + e);
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String queryString = request.getQueryString();
		queryString = queryString.substring(6);
		
		try {
			switch (queryString) {
			
			}
		} catch (Exception e) {
			System.err.println("ReservaController | GET: " + e);
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		try {
			switch (action) {
				case "reserva":
					this.fazerReservaPOST(request, response);
					break;
				case "confirmarPagamento":
					this.confirmarPagamentoPOST(request, response);
					break;
				default:
					break;
			}
		} catch (Exception e) {
			System.err.println("ReservaController | POST: " + e);
		}
	}
	
	private void fazerReservaPOST(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		int idCampo = Integer.parseInt(request.getParameter("idCampo"));
		Reserva reserva = new Reserva(usuarioLogado, 1, null, null, 0);
		int idReserva = this.reservaDAO.createReserva(reserva);
		
		if (idReserva > 0) {
			request.setAttribute("idCampo", idCampo);
			request.setAttribute("idReserva", idReserva);
			session.setAttribute("usuarioLogado", usuarioLogado);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/home.jsp?param=reservaAgenda");
			dispatcher.forward(request, response);
		}
	}
	
	private void confirmarPagamentoPOST(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		Timestamp dataHoraAtual = Timestamp.valueOf(LocalDateTime.now());
		Reserva reserva = new Reserva(
				usuarioLogado,
				2,
				dataHoraAtual,
				dataHoraAtual,
				Float.parseFloat(request.getParameter("valorTotal"))
		);
		int updated = this.reservaDAO.updateReserva(reserva, Integer.parseInt(request.getParameter("idReserva")));
		if (updated > 0) {
			session.setAttribute("usuarioLogado", usuarioLogado);
			response.sendRedirect("pages/home.jsp");
		}
	}

}
