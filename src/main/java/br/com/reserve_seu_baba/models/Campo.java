package br.com.reserve_seu_baba.models;

public class Campo {
	private int idCampo;
	private String tipoGramadoCampo;
	private float larguraCampo;
	private float comprimentoCampo;
	private String codCampo;
	private float precoCampo;
	
	public Campo(
			int idCampo,
			String tipoGramadoCampo,
			float larguraCampo,
			float comprimentoCampo,
			String codCampo,
			float precoCampo
	) {
		this.idCampo = idCampo;
		this.tipoGramadoCampo = tipoGramadoCampo;
		this.larguraCampo = larguraCampo;
		this.comprimentoCampo = comprimentoCampo;
		this.codCampo = codCampo;
		this.precoCampo = precoCampo;
	}
	
	public Campo() {}

	public int getIdCampo() {
		return idCampo;
	}

	public void setIdCampo(int idCampo) {
		this.idCampo = idCampo;
	}

	public String getTipoGramadoCampo() {
		return tipoGramadoCampo;
	}

	public void setTipoGramadoCampo(String tipoGramadoCampo) {
		this.tipoGramadoCampo = tipoGramadoCampo;
	}

	public float getLarguraCampo() {
		return larguraCampo;
	}

	public void setLarguraCampo(float larguraCampo) {
		this.larguraCampo = larguraCampo;
	}

	public float getComprimentoCampo() {
		return comprimentoCampo;
	}

	public void setComprimentoCampo(float comprimentoCampo) {
		this.comprimentoCampo = comprimentoCampo;
	}

	public String getCodCampo() {
		return codCampo;
	}

	public void setCodCampo(String codCampo) {
		this.codCampo = codCampo;
	}

	public float getPrecoCampo() {
		return precoCampo;
	}

	public void setPrecoCampo(float precoCampo) {
		this.precoCampo = precoCampo;
	}
	
	
	
	
}
