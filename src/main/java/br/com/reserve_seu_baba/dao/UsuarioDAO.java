package br.com.reserve_seu_baba.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;

import br.com.reserve_seu_baba.models.Usuario;
import br.com.reserve_seu_baba.utils.ConnectionSQL;
import br.com.reserve_seu_baba.utils.ConnectionSQL;

public class UsuarioDAO {
	
	public UsuarioDAO() {}
	
	public int createUsuarioDAO(Usuario usuario) throws Exception {
		Connection conn = new ConnectionSQL().getConnection();
		
		int result = 0;
			
		String sql = "INSERT INTO usuario ("				
			+ "nome_usuario,"
			+ "email_usuario,"
			+ "senha_usuario,"
			+ "data_nascimento_usuario,"
			+ "adm_usuario"
		+ ") VALUES ("
			+ "?,"
			+ "?,"
			+ "?,"
			+ "?,"
			+ "?"
			+ ")";
			
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, usuario.getNomeUsuario());
		ps.setString(2, usuario.getEmailUsuario());
		ps.setString(3, usuario.getSenhaUsuario());
		ps.setString(4, usuario.getDataNascimentoUsuario().toString());
		ps.setBoolean(5, usuario.isAdmUsuario());
						
		result = ps.executeUpdate();
		conn.close();
		return result;
	}
	
	public Usuario getUsuarioById(int id) throws Exception {
		Connection conn = new ConnectionSQL().getConnection();
		Usuario usuario = null;
		String sql = "SELECT * FROM usuario WHERE usuarios.id_usuario = ? ";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		
		ResultSet rs = ps.executeQuery();
		
		if (rs != null) {
			usuario = new Usuario(
					rs.getInt("id_usuario"),
					rs.getString("nome_usuario"),
					rs.getString("email_usuario"),
					rs.getString("senha_usuario"),
					rs.getTimestamp("data_nascimento_usuario"),
					rs.getBoolean("adm_usuario")
			);
		}
		conn.close();
		return usuario;
	}
	
	public ArrayList<Usuario> getAllUsuarios() throws Exception {
		Connection conn = new ConnectionSQL().getConnection();
		ArrayList<Usuario> listaUsuario = new ArrayList<Usuario>();
		
		String sql = "SELECT * FROM usuario";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Usuario usuario = new Usuario(
					rs.getInt("id_usuario"),
					rs.getString("nome_usuario"),
					rs.getString("email_usuario"),
					rs.getString("senha_usuario"),
					rs.getTimestamp("data_nascimento_usuario"),
					rs.getBoolean("adm_usuario")
			);
			listaUsuario.add(usuario);
		}
		conn.close();
		return listaUsuario;
	}
	
	public int updateUsuario(Usuario usuarioAtualizado,int id) throws Exception {
		Connection conn = new ConnectionSQL().getConnection();		
		
		int result = 0;
		String sql = "UPDATE usuario"
				+ "SET nome_usuario = ?,"
				+ "email_usuario = ?,"
				+ "senha_usuario = ?,"
				+ "data_nascimento_usuario = ?,"
				+ "adm_usuario = ?"
				+ "WHERE id_usuario = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, usuarioAtualizado.getNomeUsuario());
		ps.setString(2, usuarioAtualizado.getEmailUsuario());
		ps.setString(3, usuarioAtualizado.getSenhaUsuario());
		ps.setString(4, usuarioAtualizado.getDataNascimentoUsuario().toString());
		ps.setBoolean(5, usuarioAtualizado.isAdmUsuario());
		ps.setInt(6, id);
		
		result = ps.executeUpdate();
		conn.close();
		return result;
	}
	
	
	public int deleteUsuario(int id) throws Exception {
		Connection conn = new ConnectionSQL().getConnection();
		String sql = "DELETE FROM usuario WHERE id_usuario = ?";
		int result = 0;
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		
		result = ps.executeUpdate();
		conn.close();
		return result;
	}
	
}
