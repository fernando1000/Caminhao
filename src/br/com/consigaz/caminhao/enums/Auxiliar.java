package br.com.consigaz.caminhao.enums;

public enum Auxiliar {
		
	VENDA_FAZENDO_REQUISICAO(10),
	REQUISICAO_VEIO_DA_VENDA(1);
	
	
	private int resultado;
	
	public int getValor(){
		return resultado;
	}
	
	private Auxiliar(int _resultado){
		this.resultado = _resultado;
	}
}
