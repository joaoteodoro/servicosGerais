package org.servicosGerais.bean.enun;

public enum TipoServico {
	LAVAGEM("Lavagem"), 
	PASSAGEM("Passagem"), 
	LIMPEZA_CASA("Limpeza de Casa");
	
	private String descricao;
	
	TipoServico(String descricao){
		this.descricao = descricao;
	}
	
	public String getDescricao(){
		return descricao;
	}
}
