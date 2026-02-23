package br.com.reserve_seu_baba.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.reserve_seu_baba.models.Campo;
import br.com.reserve_seu_baba.utils.ConnectionSQL;

public class CampoDAO {
	
	public CampoDAO() {}
	
	public int createCampo(Campo campo) throws Exception {
		Connection conn = new ConnectionSQL().getConnection();
		int result = 0;
		String sql = "INSERT INTO campo ("
				+ "tipo_gramado_campo,"
				+ "largura_campo,"
				+ "comprimento_campo,"
				+ "cod_campo,"
				+ "preco_campo"
				+ ") VALUES ("
				+ "?,"
				+ "?,"
				+ "?,"
				+ "?,"
				+ "?"
				+ ")";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, campo.getTipoGramadoCampo());
		ps.setFloat(2, campo.getLarguraCampo());
		ps.setFloat(3, campo.getComprimentoCampo());
		ps.setString(4, campo.getCodCampo());
		ps.setFloat(5, campo.getPrecoCampo());
		result = ps.executeUpdate();
		return result;
	}
	
	public Campo getCampoById(int id) throws Exception {
		Connection conn = new ConnectionSQL().getConnection();
		Campo campo = null;
		String sql = "SELECT * FROM campo WHERE id_campo = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		if (rs != null) {
			campo = new Campo(
					rs.getInt("id_campo"),
					rs.getString("tipo_gramado_campo"),
					rs.getFloat("largura_campo"),
					rs.getFloat("comprimento_campo"),
					rs.getString("cod_campo"),
					rs.getFloat("preco_campo")
			);
		}
		return campo;
	}
	
	public ArrayList<Campo> getAllCampos() throws Exception {
		Connection conn = new ConnectionSQL().getConnection();
		ArrayList<Campo> listaCampos = new ArrayList<Campo>();
		String sql = "SELECT * FROM  campo";
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			Campo campo = new Campo(
					rs.getInt("id_campo"),
					rs.getString("tipo_gramado_campo"),
					rs.getFloat("largura_campo"),
					rs.getFloat("comprimento_campo"),
					rs.getString("cod_campo"),
					rs.getFloat("preco_campo")
			);
			listaCampos.add(campo);
		}
		
		return listaCampos;
	}
	
	public int updateCampo(Campo campoAtualizado, int id) throws Exception {
		Connection conn = new ConnectionSQL().getConnection();
		int result = 0;
		String sql = "UPDATE campo "
				+ "SET tipo_gramado_campo = ?,"
				+ "largura_campo = ?,"
				+ "comprimento_campo = ?,"
				+ "cod_campo = ?,"
				+ "preco_campo = ? "
				+ "WHERE id_campo = ?";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, campoAtualizado.getTipoGramadoCampo());
		ps.setFloat(2, campoAtualizado.getLarguraCampo());
		ps.setFloat(3, campoAtualizado.getComprimentoCampo());
		ps.setString(4, campoAtualizado.getCodCampo());
		ps.setFloat(5, campoAtualizado.getPrecoCampo());
		ps.setInt(6, id);
		result = ps.executeUpdate();
		return result;
	}
	
	public int deleteCampo(int id) throws Exception {
		Connection conn = new ConnectionSQL().getConnection();
		int result = 0;
		String sql = "DELETE TABLE campo WHERE id_campo = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		result = ps.executeUpdate();
		return result;
	}

}
