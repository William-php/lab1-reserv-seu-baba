package br.com.reserve_seu_baba.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Agenda {
	private int idAgenda;
	private Timestamp dataHoraInicioAgenda;
	private Timestamp dataHoraFimAgenda;
	private Campo campo;
	private float valorTotalAgenda;
	private Reserva reserva;
	
	public Agenda(
			int idAgenda,
			Timestamp dataHoraInicioAgenda,
			Timestamp dataHoraFimAgenda,
			Campo campo,
			float valorTotalAgenda,
			Reserva reserva
	) {
		this.idAgenda = idAgenda;
		this.dataHoraInicioAgenda = dataHoraInicioAgenda;
		this.dataHoraFimAgenda = dataHoraFimAgenda;
		this.campo = campo;
		this.valorTotalAgenda = valorTotalAgenda;
		this.reserva = reserva;
	}
	
	public Agenda(			
			Timestamp dataHoraInicioAgenda,
			Timestamp dataHoraFimAgenda,
			Campo campo,
			float valorTotalAgenda,
			Reserva reserva
	) {		
		this.dataHoraInicioAgenda = dataHoraInicioAgenda;
		this.dataHoraFimAgenda = dataHoraFimAgenda;
		this.campo = campo;
		this.valorTotalAgenda = valorTotalAgenda;
		this.reserva = reserva;
	}
	
	public Agenda() {}

	public int getIdAgenda() {
		return idAgenda;
	}

	public void setIdAgenda(int idAgenda) {
		this.idAgenda = idAgenda;
	}

	public Timestamp getDataHoraInicioAgenda() {
		return dataHoraInicioAgenda;
	}

	public void setDataHoraInicioAgenda(Timestamp dataHoraInicioAgenda) {
		this.dataHoraInicioAgenda = dataHoraInicioAgenda;
	}

	public Timestamp getDataHoraFimAgenda() {
		return dataHoraFimAgenda;
	}

	public void setDataHoraFimAgenda(Timestamp dataHoraFimAgenda) {
		this.dataHoraFimAgenda = dataHoraFimAgenda;
	}

	public Campo getCampo() {
		return campo;
	}

	public void setCampo(Campo campo) {
		this.campo = campo;
	}

	public float getValorTotalAgenda() {
		return valorTotalAgenda;
	}

	public void setValorTotalAgenda(float valorTotalAgenda) {
		this.valorTotalAgenda = valorTotalAgenda;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
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
