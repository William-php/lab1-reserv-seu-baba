package br.com.reserve_seu_baba.models;

import java.security.Timestamp;
import java.time.LocalDateTime;

public class Usuario {
	private int idUsuario;
	private String nomeUsuario;
	private String emailUsuario;
	private String senhaUsuario;
	private java.sql.Timestamp dataNascimentoUsuario;
	private boolean admUsuario;
	
	public Usuario(
			int idUsuario,
			String nomeUsuario,
			String emailUsuario,
			String senhaUsuario,
			java.sql.Timestamp dataNascimentoUsuario,
			boolean admUsuario
	) {
		this.idUsuario = idUsuario;
		this.nomeUsuario = nomeUsuario;
		this.emailUsuario = emailUsuario;
		this.senhaUsuario = senhaUsuario;
		this.dataNascimentoUsuario = dataNascimentoUsuario;
		this.admUsuario = admUsuario;
	}
	
	public Usuario(
			String nomeUsuario,
			String emailUsuario,
			String senhaUsuario,
			java.sql.Timestamp dataNascimentoUsuario,
			boolean admUsuario
	) {
		this.nomeUsuario = nomeUsuario;
		this.emailUsuario = emailUsuario;
		this.senhaUsuario = senhaUsuario;
		this.dataNascimentoUsuario = dataNascimentoUsuario;
		this.admUsuario = admUsuario;
	}
	
	public Usuario() {}
	
	

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getEmailUsuario() {
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}

	public String getSenhaUsuario() {
		return senhaUsuario;
	}

	public void setSenhaUsuario(String senhaUsuario) {
		this.senhaUsuario = senhaUsuario;
	}

	public java.sql.Timestamp getDataNascimentoUsuario() {
		return dataNascimentoUsuario;
	}

	public void setDataNascimentoUsuario(java.sql.Timestamp dataNascimentoUsuario) {
		this.dataNascimentoUsuario = dataNascimentoUsuario;
	}

	public boolean isAdmUsuario() {
		return admUsuario;
	}

	public void setAdmUsuario(boolean admUsuario) {
		this.admUsuario = admUsuario;
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
