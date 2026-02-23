package br.com.reserve_seu_baba.controllers;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

import br.com.reserve_seu_baba.dao.UsuarioDAO;
import br.com.reserve_seu_baba.models.Usuario;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
		try {
			this.listarUsuarios(request, response);
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	private void listarUsuarios(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ArrayList<Usuario> listaUsuarios = this.usuarioDAO.getAllUsuarios();
		request.setAttribute("listaUsuarios", listaUsuarios);
		RequestDispatcher dispatcher = request.getRequestDispatcher("listaUsuarios.jsp");
		dispatcher.forward(request, response);
	}
	
}
