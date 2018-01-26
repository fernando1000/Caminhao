package br.com.consigaz.caminhao.dao;

import java.util.ArrayList;
import java.util.List;

public class ListaComTabelasModel{
	
	public static List<String> devolveListaComTabelasModel(){
		
		List<String> lista = new ArrayList<String>(); 
		 			 lista.add("Pda");
		 			 lista.add("Boleto");
		 			 lista.add("Cliente_pedido");
		 			 lista.add("Condicao_pagamento");
		 			 lista.add("Embarque");
		 			 lista.add("Estabelecimento_serie");
		 			 lista.add("Instrucao_bancaria");
		 			 lista.add("Item_nfe");
		 			 lista.add("Justificativa");
		 			 lista.add("Local_entrega_pedido");
		 			 lista.add("Mensagem");
		 			 lista.add("Natureza_operacao");
		 			 lista.add("Pedido");
		 			 lista.add("Produto");
		 			 lista.add("Vendedor");
		
		 			 lista.add("Retorno_atendimento");
		 			 lista.add("Retorno_embarque");
		 			 lista.add("Retorno_ultima_nota_emitida");
	 			 
		 return lista;
	}	
}
