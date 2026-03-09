package br.com.reserve_seu_baba.controllers;

import java.io.IOException;
import java.util.ArrayList;

import br.com.reserve_seu_baba.dao.CampoDAO;
import br.com.reserve_seu_baba.models.Campo;
import br.com.reserve_seu_baba.models.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SuppressWarnings("serial")
@WebServlet("/CampoController")
public class CampoController extends HttpServlet {
	private CampoDAO campoDAO;
	
	public void init() throws ServletException {
		try {
			this.campoDAO = new CampoDAO();
		} catch (Exception e) {
			throw new ServletException("Erro ao inicializar o campoDAO: " + e);
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String queryString = request.getQueryString().substring(6);
		try {
			switch (queryString) {
				case "adicionarCampo":
					this.adicionarCampoGET(request, response);
					break;
				case "listarCampos":
					this.listarCamposGET(request, response);
					break;
				default:
					break;
			}
		} catch (Exception e) {
			System.out.println("CampoController.doGet | error: " + e);
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		try {
			switch (action) {
				case "add":
					this.adicionarCampoPOST(request, response);
					break;
				case "delete":
					this.deletarCampoPOST(request, response);
					break;
				default:
					break;
			}
		} catch (Exception e) {
			System.out.println("CampoController.doPost | error: " + e);
		}
	}
	
	public void adicionarCampoGET(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/home.jsp?param=adicionarCampo");
		dispatcher.forward(request, response);
	}
	
	public void adicionarCampoPOST(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Campo campo = new Campo(
			request.getParameter("tipoGramado"),
			Float.parseFloat(request.getParameter("largura")),
			Float.parseFloat(request.getParameter("comprimento")),
			request.getParameter("codCampo"),
			Float.parseFloat(request.getParameter("preco"))						
		);
		
		HttpSession session = request.getSession();
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		
		if (this.campoDAO.createCampo(campo) > 0) {			
			request.getSession().setAttribute("usuarioLogado", usuarioLogado);
			response.sendRedirect(request.getContextPath() + "/pages/home.jsp");
		};
		
	}
	
	public void listarCamposGET(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<Campo> listaCampos = this.campoDAO.getAllCampos();
		HttpSession session = request.getSession();
		Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
		request.setAttribute("usuarioLogado", usuarioLogado);
		request.setAttribute("listaCampos", listaCampos);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/home.jsp?param=listarCampos");
		dispatcher.forward(request, response);
	}
	
	public void deletarCampoPOST(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int deleted = this.campoDAO.deleteCampo(Integer.parseInt(request.getParameter("idCampo")));
		if (deleted > 0) {
			ArrayList<Campo> listaCampos = this.campoDAO.getAllCampos();
			HttpSession session = request.getSession();
			Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
			request.setAttribute("usuarioLogado", usuarioLogado);
			request.setAttribute("listaCampos", listaCampos);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/home.jsp?param=listarCampos");
			dispatcher.forward(request, response);
		}
		
	}

}
