package br.com.reserve_seu_baba.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import br.com.reserve_seu_baba.models.Reserva;
import br.com.reserve_seu_baba.models.Usuario;
import br.com.reserve_seu_baba.utils.ConnectionSQL;

public class ReservaDAO {
	public ReservaDAO() {}
	
	public int createReserva(Reserva reserva) throws Exception {
	    Connection conn = new ConnectionSQL().getConnection();
	    int generatedId = 0;
	    
	    String sql = "INSERT INTO reserva ("
	            + "usuario, "
	            + "pagamento_reserva, "
	            + "data_hora_pagamento_reserva, "
	            + "data_hora_registro_reserva, "
	            + "valor_total_reserva "                
	            + ") VALUES (?, ?, ?, ?, ?)";

	    // 1. Informe ao JDBC que você quer as chaves geradas de volta
	    PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
	    
	    ps.setInt(1, reserva.getUsuario().getIdUsuario());
	    ps.setInt(2, reserva.getPagamentoReserva());
	    ps.setTimestamp(3, reserva.getDataHoraPagamentoReserva());
	    ps.setTimestamp(4, reserva.getDataHoraRegistroReserva());
	    ps.setFloat(5, reserva.getValorTotalReserva());        
	    
	    int rowsAffected = ps.executeUpdate();

	    // 2. Se a inserção funcionou, recupere o ID
	    if (rowsAffected > 0) {
	        try (ResultSet rs = ps.getGeneratedKeys()) {
	            if (rs.next()) {
	                generatedId = rs.getInt(1);
	            }
	        }
	    }

	    ps.close();
	    conn.close();
	    
	    // 3. Retorne o ID gerado (será 0 se falhar)
	    return generatedId;
	}
	
	
	public Reserva getReservaById(int reservaId, int usuarioId) throws Exception {
		Connection conn = new ConnectionSQL().getConnection();
		Reserva reserva = null;
		String sql = "SELECT usuario.*, reserva.* FROM reserva INNER JOIN usuario ON id_reserva = ? AND reserva.usuario = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, reservaId);
		ps.setInt(2, usuarioId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			Usuario usuario = new Usuario(
				rs.getInt("id_usuario"),
				rs.getString("nome_usuario"),
				rs.getString("email_usuario"),
				rs.getString("senha_usuario"),
				rs.getTimestamp("data_nascimento_usuario"),
				rs.getBoolean("adm_usuario")
			);
			
			reserva = new Reserva(
					rs.getInt("id_reserva"),
					usuario,
					rs.getString("pagamento_reserva") == "AGUARDANDO" ? 1 : 2,
					rs.getTimestamp("data_hora_pagamento_reserva"),
					rs.getTimestamp("data_hora_registro_reserva"),
					rs.getFloat("valor_total_reserva")										
			);
		}
		return reserva;
	}
	
	public ArrayList<Reserva> getAllReserva() throws Exception {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();
		String sql = "SELECT usuario.*, reserva.* FROM reserva INNER JOIN usuario ON reserva.usuario = usuario.id_usuario";
		Connection conn = new ConnectionSQL().getConnection();
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
				
				Reserva reserva = new Reserva(
						rs.getInt("id_reserva"),
						usuario,
						rs.getInt("pagamento_reserva"),
						rs.getTimestamp("data_hora_pagamento_reserva"),
						rs.getTimestamp("data_hora_registro_reserva"),
						rs.getFloat("valor_total_reserva")						
						
				);
				
				reservas.add(reserva);
		}
		
		return reservas;
	}
	
	public int updateReserva(Reserva reservaAtualizada, int id) throws Exception {
		Connection conn = new ConnectionSQL().getConnection();
		String sql = "UPDATE reserva "
				+ "SET pagamento_reserva = ?, "
				+ "data_hora_pagamento_reserva = ?,"
				+ "data_hora_registro_reserva = ?,"
				+ "valor_total_reserva = ?"				
				+ "WHERE id_reserva = ?;";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, reservaAtualizada.getPagamentoReserva());
		ps.setTimestamp(2, reservaAtualizada.getDataHoraPagamentoReserva());
		ps.setTimestamp(3, reservaAtualizada.getDataHoraRegistroReserva());
		ps.setFloat(4, reservaAtualizada.getValorTotalReserva());		
		ps.setInt(5, id);
		int result = ps.executeUpdate();
		return result;
	}
	
	public int deleteReserva(int id) throws Exception {
		Connection conn = new ConnectionSQL().getConnection();
		String sql = "DELETE FROM reserva WHERE id_reserva = ?";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, id);
		int result = ps.executeUpdate();
		return result;
	}
	
	
}
