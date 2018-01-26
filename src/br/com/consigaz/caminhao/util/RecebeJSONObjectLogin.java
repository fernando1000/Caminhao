package br.com.consigaz.caminhao.util;

import org.json.JSONArray;
import org.json.JSONObject;
import android.content.Context;
import android.widget.Toast;
import br.com.consigaz.caminhao.dao.Dao;
import br.com.consigaz.caminhao_sharedlib.modelo.*;
import utilitarios.reflexao.DevolveListaDaClasseEJsonArrayInformado;

public class RecebeJSONObjectLogin {

	private Context context;

	public RecebeJSONObjectLogin() {
	}
	public RecebeJSONObjectLogin(Context _context) {
		this.context = _context;
	}

	public boolean inserePdaComTodasTabelas(JSONObject jSONObject_resposta) {

		boolean erro = false;

		try {
			JSONArray jSONArray1 = jSONObject_resposta.getJSONArray("listaBoleto");
			JSONArray jSONArray2 = jSONObject_resposta.getJSONArray("listaClientePedido");
			JSONArray jSONArray3 = jSONObject_resposta.getJSONArray("listaCondicaoPagamento");
			JSONArray jSONArray4 = jSONObject_resposta.getJSONArray("listaEmbarque");
			JSONArray jSONArray5 = jSONObject_resposta.getJSONArray("listaEstabelecimentoSerie");
			JSONArray jSONArray6 = jSONObject_resposta.getJSONArray("listaInstrucaoBancaria");
			JSONArray jSONArray7 = jSONObject_resposta.getJSONArray("listaItemNFE");
			JSONArray jSONArray8 = jSONObject_resposta.getJSONArray("listaJustificativa");
			JSONArray jSONArray9 = jSONObject_resposta.getJSONArray("listaLocalEntregaPedido");
			JSONArray jSONArray10 = jSONObject_resposta.getJSONArray("listaMensagem");
			JSONArray jSONArray11 = jSONObject_resposta.getJSONArray("listaNaturezaOperacao");
			JSONArray jSONArray12 = jSONObject_resposta.getJSONArray("listaPedido");
			JSONArray jSONArray13 = jSONObject_resposta.getJSONArray("listaProduto");
			JSONArray jSONArray14 = jSONObject_resposta.getJSONArray("listaVendedor");
			JSONArray jSONArray15 = jSONObject_resposta.getJSONArray("listaPda");
							
			DevolveListaDaClasseEJsonArrayInformado devolveLista = new DevolveListaDaClasseEJsonArrayInformado();
			
			TO transactionalObject = new TO();		
			   transactionalObject.setListaBoleto(devolveLista.devolve(Boleto.class, jSONArray1));
			   transactionalObject.setListaClientePedido(devolveLista.devolve(Cliente_pedido.class, jSONArray2));
			   transactionalObject.setListaCondicaoPagamento(devolveLista.devolve(Condicao_pagamento.class, jSONArray3));
			   transactionalObject.setListaEmbarque(devolveLista.devolve(Embarque.class, jSONArray4));
			   transactionalObject.setListaEstabelecimentoSerie(devolveLista.devolve(Estabelecimento_serie.class, jSONArray5));
			   transactionalObject.setListaInstrucaoBancaria(devolveLista.devolve(Instrucao_bancaria.class, jSONArray6));
			   transactionalObject.setListaItemNFE(devolveLista.devolve(Item_nfe.class, jSONArray7));
			   transactionalObject.setListaJustificativa(devolveLista.devolve(Justificativa.class, jSONArray8));
			   transactionalObject.setListaLocalEntregaPedido(devolveLista.devolve(Local_entrega_pedido.class, jSONArray9));
			   transactionalObject.setListaMensagem(devolveLista.devolve(Mensagem.class, jSONArray10));
			   transactionalObject.setListaNaturezaOperacao(devolveLista.devolve(Natureza_operacao.class, jSONArray11));
			   transactionalObject.setListaPedido(devolveLista.devolve(Pedido.class, jSONArray12));
			   transactionalObject.setListaProduto(devolveLista.devolve(Produto.class, jSONArray13));
			   transactionalObject.setListaVendedor(devolveLista.devolve(Vendedor.class, jSONArray14));
			   transactionalObject.setListaPda(devolveLista.devolve(Pda.class, jSONArray15));
							
			Dao dao = new Dao(context);
				dao.deletaTodosDados();
				dao.insereTabelas(transactionalObject);
		} 
		catch (Exception exception) {

			Toast.makeText(context, ""+exception, Toast.LENGTH_SHORT).show();
			
			erro = true;
		}
		return erro;
	}

}
