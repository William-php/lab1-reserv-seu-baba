package br.com.reserve_seu_baba.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import br.com.reserve_seu_baba.models.ListaParticipante;
import br.com.reserve_seu_baba.models.Reserva;
import br.com.reserve_seu_baba.models.Usuario;
import br.com.reserve_seu_baba.utils.ConnectionSQL;

public class ListaParticipanteDAO {

    public ListaParticipanteDAO() {}

    public int createListaParticipante(ListaParticipante lista, int idReserva) throws Exception {
        Connection conn = new ConnectionSQL().getConnection();
        int result = 0;

        String sql = "INSERT INTO lista_participante (reserva, nome_participante) VALUES (?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        if (lista.getNomesListaParticipante() == null || lista.getNomesListaParticipante().isEmpty()) {
            ps.setInt(1, idReserva);
            ps.setString(2, null);
            result = ps.executeUpdate();
            return result;
        } 
        
        for (String nome : lista.getNomesListaParticipante()) {
        	ps.setInt(1, idReserva);
            ps.setString(2, nome);
            ps.addBatch();
        }
        result = ps.executeBatch()[0];
        conn.close();
        return result;
    }

    public ArrayList<ListaParticipante> getAllListaParticipante() throws Exception {
        String sql = "SELECT lista_participante.reserva AS reserva_id, lista_participante.nome_participante, reserva.*, usuario.* "
                + "FROM lista_participante "
                + "INNER JOIN reserva ON lista_participante.reserva = reserva.id_reserva "
                + "INNER JOIN usuario ON reserva.usuario = usuario.id_usuario "
                + "ORDER BY lista_participante.reserva";

        Connection conn = new ConnectionSQL().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        Map<Integer, ListaParticipante> map = new LinkedHashMap<>();

        while (rs.next()) {
            int reservaId = rs.getInt("reserva_id");
            ListaParticipante lp = map.get(reservaId);

            if (lp == null) {
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

                lp = new ListaParticipante(reservaId, reserva);
                map.put(reservaId, lp);
                lp.setNomesListaParticipante(new ArrayList<String>());
            }

            String nome = rs.getString("nome_participante");
            if (nome != null && !nome.trim().isEmpty()) {
                lp.addParticipante(nome);
            }
        }

        conn.close();
        return new ArrayList<>(map.values());
    }

    public ListaParticipante getListaByReservaId(int reservaId) throws Exception {
        String sql = "SELECT lista_participante.*, reserva.*, usuario.* "
                + "FROM lista_participante "
                + "JOIN reserva ON lista_participante.reserva = reserva.id_reserva "
                + "JOIN usuario ON reserva.usuario = usuario.id_usuario "
                + "WHERE lista_participante.reserva = ?";

        Connection conn = new ConnectionSQL().getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, reservaId);
        ResultSet rs = ps.executeQuery();

        ListaParticipante lp = null;
        
        while (rs.next()) {
        	
            if (lp == null) {            	
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
                        rs.getString("pagamento_reserva") == "PAGO" ? 2 : 1,
                        rs.getTimestamp("data_hora_pagamento_reserva"),
                        rs.getTimestamp("data_hora_registro_reserva"),
                        rs.getFloat("valor_total_reserva")                        
                );

                lp = new ListaParticipante(reservaId, reserva); 
                lp.setNomesListaParticipante(new ArrayList<String>());
            }
            
            String nome = rs.getString("nome_participante");
            if (nome != null && !nome.trim().isEmpty()) {
                lp.addParticipante(nome);
            }
        }

        conn.close();
        return lp;
    }

    public int deleteListaByReservaId(int reservaId) throws Exception {
        Connection conn = new ConnectionSQL().getConnection();
        String sql = "DELETE FROM lista_participante WHERE reserva = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, reservaId);
        int result = ps.executeUpdate();
        conn.close();
        return result;
    }

}
