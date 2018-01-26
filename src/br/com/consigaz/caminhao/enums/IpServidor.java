package br.com.consigaz.caminhao.enums;

public enum IpServidor {

	//Local:
	URL_SERVER_REST("http://bartec13:8080/Caminhao_rest_service");

	
	//Producao:
	//URL_SERVER_REST("http://172.16.0.48:8080/Caminhao_rest_service");

	
	private String valor;
	
	private IpServidor(String valor){
		this.valor = valor;
	}
	
	public String getValor(){
		return valor;
	}
}
