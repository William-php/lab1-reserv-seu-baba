package br.com.reserve_seu_baba.controllers;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import br.com.reserve_seu_baba.dao.AgendaDAO;
import br.com.reserve_seu_baba.dao.CampoDAO;
import br.com.reserve_seu_baba.dao.ReservaDAO;
import br.com.reserve_seu_baba.models.Agenda;
import br.com.reserve_seu_baba.models.Campo;
import br.com.reserve_seu_baba.models.Reserva;
import br.com.reserve_seu_baba.models.Usuario;
import br.com.reserve_seu_baba.utils.DataUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AgendaController")
public class AgendaController extends HttpServlet {
	AgendaDAO agendaDAO;
	
	public void init() throws ServletException {
		try {
			this.agendaDAO = new AgendaDAO();
		} catch (Exception e) {
			System.err.println("AgendaController | Servlet | Init: " + e);
		}
	}
	
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String param = request.getParameter("action");
		try {
			switch (param) {
				case "agendar":
					this.agendarPOST(request, response);
					break;
				default:
					break;
			}
		} catch (Exception e) {
			System.err.println("AgendaController | doPost: " + e);
		}
	}
	
	private void agendarPOST(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		
		CampoDAO campoDAO = new CampoDAO();
		
		Campo campo = campoDAO.getCampoById(Integer.parseInt(request.getParameter("idCampo")));
		
		ReservaDAO reservaDAO = new ReservaDAO();
		Reserva reserva = reservaDAO.getReservaById(Integer.parseInt(request.getParameter("idReserva")), usuarioLogado.getIdUsuario());
		
		Timestamp dataInicio = DataUtils.parseTimestamp(request.getParameter("dataInicio"));
		Timestamp dataFim = DataUtils.parseTimestamp(request.getParameter("dataFim"));
		Agenda agenda = new Agenda(dataInicio, dataFim, campo, reserva);
		int horasAlugada = DataUtils.differenceBetweenTime(dataInicio, dataFim);
		float valorTotal = campo.getPrecoCampo() * horasAlugada;
		
		int responseDAO = this.agendaDAO.createAgenda(agenda);
		
		if (responseDAO > 0) {
			request.setAttribute("idReserva", reserva.getIdReserva());
			request.setAttribute("valorTotal", valorTotal);
			session.setAttribute("usuarioLogado", usuarioLogado);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/home.jsp?param=pagamento");
			dispatcher.forward(request, response);
		}		
	}
	
//	private void minhasReservaAgendadasGET(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		HttpSession session = request.getSession();
//		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
//		
//		ArrayList<Agenda> reservasAgendadas = this.agendaDAO.getAllAgenda(usuarioLogado.getIdUsuario());
//		
//		if (reservasAgendadas.size() > 0) {
//			
//		}
//	}

}
