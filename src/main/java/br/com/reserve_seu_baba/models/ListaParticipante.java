package br.com.reserve_seu_baba.models;

import java.util.ArrayList;

public class ListaParticipante {
	private int idListaParticipante;
	private Reserva reserva;
	private ArrayList<String> nomesListaParticipante;
	
	public ListaParticipante(
			int idListaParticipante,
			Reserva reserva,
			ArrayList<String> nomesListaParticipante
	) {
		this.idListaParticipante = idListaParticipante;
		this.reserva = reserva;
		this.nomesListaParticipante = nomesListaParticipante;
	}
	
	public ListaParticipante(
			int idListaParticipante,
			Reserva reserva
	) {
		this.idListaParticipante = idListaParticipante;
		this.reserva = reserva;
		this.nomesListaParticipante = new ArrayList<String>();
	}
	
	public ListaParticipante() {}

	public int getIdListaParticipante() {
		return idListaParticipante;
	}

	public void setIdListaParticipante(int idListaParticipante) {
		this.idListaParticipante = idListaParticipante;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public ArrayList<String> getNomesListaParticipante() {
		return nomesListaParticipante;
	}

	public void setNomesListaParticipante(ArrayList<String> nomesListaParticipante) {
		this.nomesListaParticipante = nomesListaParticipante;
	}
	
	public void addParticipante(String nomeParticipante) {
		this.nomesListaParticipante.add(nomeParticipante);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}
	
	
	
	
}
