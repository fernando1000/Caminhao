package br.com.consigaz.caminhao.enums;

public enum VersaoAplicativo{

	VERSAO_NUMERO(201604121);
	
	private int numero;
		
	private VersaoAplicativo(int numero){
		this.numero = numero;
	}
	
	public int getNumero(){
		return numero;
	}
}
