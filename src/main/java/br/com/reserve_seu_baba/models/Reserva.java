package br.com.reserve_seu_baba.models;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Reserva {
	private int idReserva;
	private Usuario usuario;
	private int pagamentoReserva;
	private Timestamp dataHoraPagamentoReserva;
	private Timestamp dataHoraRegistroReserva;
	private float valorTotalReserva;
	
	public Reserva(
			int idReserva,
			Usuario usuario,
			int pagamentoReserva,
			Timestamp dataHoraPagamentoReserva,
			Timestamp dataHoraRegistroReserva,
			float valorTotalReserva			
	) {
		this.idReserva = idReserva;
		this.usuario = usuario;
		this.pagamentoReserva = pagamentoReserva;
		this.dataHoraPagamentoReserva = dataHoraPagamentoReserva;
		this.dataHoraRegistroReserva = dataHoraRegistroReserva;
		this.valorTotalReserva = valorTotalReserva;		
	}
	
	public Reserva(			
			Usuario usuario,
			int pagamentoReserva,
			Timestamp dataHoraPagamentoReserva,
			Timestamp dataHoraRegistroReserva,
			float valorTotalReserva			
	) {		
		this.usuario = usuario;
		this.pagamentoReserva = pagamentoReserva;
		this.dataHoraPagamentoReserva = dataHoraPagamentoReserva;
		this.dataHoraRegistroReserva = dataHoraRegistroReserva;
		this.valorTotalReserva = valorTotalReserva;		
	}
	
	public Reserva(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public Reserva() {}

	public int getIdReserva() {
		return idReserva;
	}

	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getPagamentoReserva() {
		return pagamentoReserva;
	}

	public void setPagamentoReserva(int pagamentoReserva) {
		this.pagamentoReserva = pagamentoReserva;
	}

	public Timestamp getDataHoraPagamentoReserva() {
		return dataHoraPagamentoReserva;
	}

	public void setDataHoraPagamentoReserva(Timestamp dataHoraPagamentoReserva) {
		this.dataHoraPagamentoReserva = dataHoraPagamentoReserva;
	}

	public Timestamp getDataHoraRegistroReserva() {
		return dataHoraRegistroReserva;
	}

	public void setDataHoraRegistroReserva(Timestamp dataHoraRegistroReserva) {
		this.dataHoraRegistroReserva = dataHoraRegistroReserva;
	}

	public float getValorTotalReserva() {
		return valorTotalReserva;
	}

	public void setValorTotalReserva(float valorTotalReserva) {
		this.valorTotalReserva = valorTotalReserva;
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
