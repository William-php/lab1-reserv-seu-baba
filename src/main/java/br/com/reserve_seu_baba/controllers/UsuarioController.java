package br.com.reserve_seu_baba.controllers;

import java.awt.List;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

import br.com.reserve_seu_baba.dao.AgendaDAO;
import br.com.reserve_seu_baba.dao.UsuarioDAO;
import br.com.reserve_seu_baba.models.Agenda;
import br.com.reserve_seu_baba.models.Usuario;
import br.com.reserve_seu_baba.utils.DataUtils;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet("/UsuarioController")
public class UsuarioController extends HttpServlet {
	private UsuarioDAO usuarioDAO;
	public void init() throws ServletException {
		try {
			this.usuarioDAO = new UsuarioDAO();
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar o usuarioDAO: " + e);
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String queryString = request.getQueryString().substring(6);		
		try {
			switch (queryString) {
				case "listaUsuarios":
					this.listarUsuariosGET(request, response);
					break;
				case "editarPerfil":
					this.atualizarUsuariosGET(request, response);
					break;
				case "logout":
					HttpSession session = request.getSession(false);
					if (session != null) {
						session.invalidate();
					}
					response.sendRedirect(request.getContextPath() + "/index.jsp");
					break;
				
				default:
					
					break;
			}
			
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		try {
			switch (action) {
				case "add":
					this.adicionarUsuariosPOST(request, response);
					break;
				case "edit":
					this.atualizarUsuariosPOST(request, response);
					break;
				case "delete":
					this.deletarUsuarioPOST(request, response);
					break;
				case "login":
					this.loginGET(request, response);
					break;
				default:
					break;
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	private void listarUsuariosGET(HttpServletRequest request, HttpServletResponse response) throws Exception {				
		ArrayList<Usuario> listaUsuarios = this.usuarioDAO.getAllUsuarios();
		request.setAttribute("listaUsuarios", listaUsuarios);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/home.jsp?param=listaUsuarios");
		dispatcher.forward(request, response);
	}
	
	private void loginGET(HttpServletRequest request, HttpServletResponse response) throws Exception {				
		Usuario usuarioLogado = this.usuarioDAO.login(request.getParameter("email"), request.getParameter("senha"));
		ArrayList<Agenda> reservasAgendadas = new AgendaDAO().getAllAgenda(usuarioLogado.getIdUsuario());		
		request.getSession().setAttribute("usuarioLogado", usuarioLogado);
		request.getSession().setAttribute("reservasAgendadas", reservasAgendadas);
		response.sendRedirect("pages/home.jsp");
	}
	
	private void atualizarUsuariosGET(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");		
		request.getSession().setAttribute("usuarioLogado", usuarioLogado);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/home.jsp?param=editarPerfil");
		dispatcher.forward(request, response);
	}
	
	private void adicionarUsuariosPOST(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Timestamp timestamp = DataUtils.parseTimestamp(request.getParameter("dataNascimento")); 
		Usuario usuarioLogado = new Usuario(
				request.getParameter("nome"),
				request.getParameter("email"),
				request.getParameter("senha"),
				timestamp,
				false
		);		
		this.usuarioDAO.createUsuarioDAO(usuarioLogado);
		 
		
		response.sendRedirect("index.jsp");
	}
	
	private void atualizarUsuariosPOST(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		Usuario usuario = new Usuario(
				request.getParameter("nome"),
				request.getParameter("email"),
				request.getParameter("senha")							
		);
		this.usuarioDAO.updateUsuario(usuario, Integer.parseInt(request.getParameter("id")));
		Usuario usuarioLogado = this.usuarioDAO.getUsuarioById(Integer.parseInt(request.getParameter("id")));
		request.getSession().setAttribute("usuarioLogado", usuarioLogado);
		response.sendRedirect(request.getContextPath() + "/pages/home.jsp");
	}
	
	private void deletarUsuarioPOST(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    int deleted = this.usuarioDAO.deleteUsuario(Integer.parseInt(request.getParameter("idUsuario")));
	    if (deleted > 0) {
	        HttpSession session = request.getSession();
	        response.sendRedirect(request.getContextPath() + "/pages/home.jsp");
	    }
	}
		
	
	
}
