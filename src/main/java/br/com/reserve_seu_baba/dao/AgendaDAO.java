package br.com.reserve_seu_baba.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import br.com.reserve_seu_baba.models.Agenda;
import br.com.reserve_seu_baba.models.Campo;
import br.com.reserve_seu_baba.models.Reserva;
import br.com.reserve_seu_baba.models.Usuario;
import br.com.reserve_seu_baba.utils.ConnectionSQL;

public class AgendaDAO {
	public AgendaDAO() {}
	
	public int createAgenda(Agenda agenda) throws Exception {
		Connection conn = new ConnectionSQL().getConnection();
		String sql = "INSERT INTO agenda ("
				+ "data_hora_inicio,"
				+ "data_hora_fim,"
				+ "campo,"
				+ "valor_total_agenda,"
				+ "reserva"
				+ ") VALUES (?, ?, ?, ?, ?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setTimestamp(1, agenda.getDataHoraInicioAgenda());
		ps.setTimestamp(2, agenda.getDataHoraFimAgenda());
		ps.setInt(3, agenda.getCampo().getIdCampo());
		ps.setFloat(4, agenda.getValorTotalAgenda());
		ps.setInt(5, agenda.getReserva().getIdReserva());
		int result = ps.executeUpdate();
		return result;
	}
	
	public ArrayList<Agenda> getAllAgenda() throws Exception {
		String sql = "SELECT campo.*, reserva.*, agenda.*, usuario.* FROM agenda"
				+ " INNER JOIN campo ON agenda.campo = campo.id_campo"
				+ " INNER JOIN reserva ON agenda.reserva = reserva.id_reserva"
				+ " INNER JOIN usuario ON reserva.usuario = usuario.id_usuario";
		ArrayList<Agenda> agendas = new ArrayList<Agenda>();
		Connection conn = new ConnectionSQL().getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			//Criar objeto campo, pois ele compõe o objeto agenda
			Campo campo = new Campo(
					rs.getInt("id_campo"),
					rs.getString("tipo_gramado_campo"),
					rs.getFloat("largura_campo"),
					rs.getFloat("comprimento_campo"),
					rs.getString("cod_campo"),
					rs.getFloat("preco_campo")
			);
			//Criar o objeto usuário, pois ele compõe o objeto reserva
			Usuario usuario = new Usuario(
					rs.getInt("id_usuario"),
					rs.getString("nome_usuario"),
					rs.getString("email_usuario"),
					rs.getString("senha_usuario"),
					rs.getTimestamp("data_nascimento_usuario"),
					rs.getBoolean("adm_usuario")
			);
			//Criar o objeto reserva, pois ele compõe o objeto agenda
			Reserva reserva = new Reserva(
						rs.getInt("id_reserva"),
						usuario,
						rs.getInt("pagamento_reserva"),
						rs.getTimestamp("data_hora_pagamento_reserva"),
						rs.getTimestamp("data_hora_registro_reserva"),
						rs.getFloat("valor_total_reserva"),
						rs.getInt("status_reserva")
						
			);
			//Criar o objeto agenda.
			Agenda agenda = new Agenda(
					rs.getInt("id_agenda"),
					rs.getTimestamp("data_hora_inicio_agenda"),
					rs.getTimestamp("data_hora_fim_agenda"),
					campo,
					rs.getFloat("valor_total_agenda"),
					reserva
			);
			agendas.add(agenda);
		}
		return agendas;
	}
	
	public Agenda getAgendaById(int id) throws Exception {
		Connection conn = new ConnectionSQL().getConnection();
		return null;
	}
}
