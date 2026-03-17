package br.com.reserve_seu_baba.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import br.com.reserve_seu_baba.dao.ListaParticipanteDAO;
import br.com.reserve_seu_baba.dao.ReservaDAO;
import br.com.reserve_seu_baba.models.ListaParticipante;
import br.com.reserve_seu_baba.models.Reserva;
import br.com.reserve_seu_baba.models.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/ParticipanteController")
public class ParticipanteController extends HttpServlet {
	ListaParticipanteDAO listaParticipanteDAO;
	public void init() throws ServletException {
		try {
			this.listaParticipanteDAO = new ListaParticipanteDAO();
		} catch (Exception e) {
			System.err.println("ParticipanteController | Servlet | e: " + e);
		}
	}
	
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String param = request.getParameter("action");		
		try {
			switch (param) {
				case "adicionarParticipante":
					this.adicionarParticipantePOST(request, response);
					break;
				case "salvarParticipantes":
					this.salvarParticipantes(request, response);
					break;
				case "verParticipantes":
					this.verParticipantesReservaPOST(request, response);
					break;
				default:
					break;
			}
		} catch (Exception e) {
			System.err.println("ParticipanteController | doPost | e: " + e);
		}
	}
	
	private void adicionarParticipantePOST(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		
		request.getParameter("idReserva");
		Reserva reserva = new ReservaDAO().getReservaById(Integer.parseInt(request.getParameter("idReserva")), usuarioLogado.getIdUsuario());
		
		if (reserva != null) {
			session.setAttribute("usuarioLogado", usuarioLogado);
			request.setAttribute("reserva", reserva);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/home.jsp?param=adicionarParticipante");
			dispatcher.forward(request, response);
		}

	}
	
	private void salvarParticipantes(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<String> nomesListaParticipantes = new ArrayList<>(Arrays.asList(request.getParameterValues("nomes")));
		
		
		ListaParticipante listaParticipantes = new ListaParticipante(nomesListaParticipantes);
		
		
		int listaSalva = this.listaParticipanteDAO.createListaParticipante(
				listaParticipantes, 
				Integer.parseInt(request.getParameter("idReserva"))
		);
		if (listaSalva > 0) {
			HttpSession session = request.getSession();
			Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
			
			request.setAttribute("usuarioLogado", usuarioLogado);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/home.jsp");
			dispatcher.forward(request, response);
		};	
	}
	
	private void verParticipantesReservaPOST(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int idReserva = Integer.parseInt(request.getParameter("idReserva")) ;
		ListaParticipante listaParticipantes = this.listaParticipanteDAO.getListaByReservaId(idReserva);		
		
		HttpSession session = request.getSession();
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		
		request.setAttribute("listaParticipantes", listaParticipantes);
		session.setAttribute("usuarioLogado", usuarioLogado);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/home.jsp?param=listaParticipantes");
		dispatcher.forward(request, response);
	}

}
