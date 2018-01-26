package br.com.consigaz.caminhao.dao;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import br.com.consigaz.caminhao_sharedlib.modelo.*;
import utilitarios.android.PreencheObjetoUsandoCursor;
import utilitarios.reflexao.Query;

public class Dao extends BancoSQLiteOpenHelper {

	public Dao(Context context) {
		super(context);
	}

	public void insereTabelas(TO transactionalObject) {

		for(Pda pda : transactionalObject.getListaPda()){
			
			insereObjeto_final(pda);
		}
		for(Boleto boleto : transactionalObject.getListaBoleto()){
			
			insereObjeto_final(boleto);
		}
		for(Cliente_pedido cliente_pedido : transactionalObject.getListaClientePedido()){
			
			insereObjeto_final(cliente_pedido);
		}
		for(Condicao_pagamento condicao_pagamento : transactionalObject.getListaCondicaoPagamento()){
			
			insereObjeto_final(condicao_pagamento);
		}
		for(Embarque embarque : transactionalObject.getListaEmbarque()){
			
			insereObjeto_final(embarque);
		}
		for(Estabelecimento_serie estabelecimento_serie : transactionalObject.getListaEstabelecimentoSerie()){
			
			insereObjeto_final(estabelecimento_serie);
		}
		for(Instrucao_bancaria instrucao_bancaria : transactionalObject.getListaInstrucaoBancaria()){
			
			insereObjeto_final(instrucao_bancaria);
		}
		for(Item_nfe item_nfe : transactionalObject.getListaItemNFE()){
			
			insereObjeto_final(item_nfe);
		}
		for(Justificativa justificativa : transactionalObject.getListaJustificativa()){
			
			insereObjeto_final(justificativa);
		}
		for(Local_entrega_pedido local_entrega_pedido : transactionalObject.getListaLocalEntregaPedido()){
			
			insereObjeto_final(local_entrega_pedido);
		}
		for(Mensagem mensagem : transactionalObject.getListaMensagem()){
			
			insereObjeto_final(mensagem);
		}
		for(Natureza_operacao natureza_operacao : transactionalObject.getListaNaturezaOperacao()){
			
			insereObjeto_final(natureza_operacao);
		}
		for(Pedido pedido : transactionalObject.getListaPedido()){
			
			insereObjeto_final(pedido);
		}
		for(Produto produto : transactionalObject.getListaProduto()){
			
			insereObjeto_final(produto);
		}
		for(Vendedor vendedor : transactionalObject.getListaVendedor()){
		
		insereObjeto_final(vendedor);
		}

	}
	
	public void insereOUatualiza(Object objeto, Object... parametros) {

		Class<?> classe = objeto.getClass();

		String select = "select * from " + classe.getSimpleName();

		String condicaoWhere = Query.criaCondicaoWhere_final(parametros);

		String querySelect = select + condicaoWhere;

		insereOUatualizaObjeto_final(objeto, querySelect, condicaoWhere);
	}

	private void insereOUatualizaObjeto_final(Object objeto, String querySelect, String condicaoWhere) {

		SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();

		Cursor cursor = sQLiteDatabase.rawQuery(querySelect, null);
	
		if (cursor.moveToFirst()) {

			atualizaObjeto_final(objeto, condicaoWhere);
		} else {
			insereObjeto_final(objeto);
		}
		cursor.close();
		sQLiteDatabase.close();
	}

	private void atualizaObjeto_final(Object objeto, String condicaoWhere) {

		SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
		sQLiteDatabase.execSQL(Query.criaUpdate_final(objeto, condicaoWhere));
		sQLiteDatabase.close();
	}

	public void insereObjeto_final(Object objeto) {

		SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
		sQLiteDatabase.execSQL(Query.criaInsert_final(objeto));
		sQLiteDatabase.close();
	}
	
	public <T> List<T> listaTodaTabela(Class<T> classe) {

		String querySelect = "select * from " + classe.getSimpleName();

		return devolveListaBaseadoEmSQL_final(classe, querySelect);
	}
		
	public <T> List<T> listaTodaTabela(Class<T> classe, Object... parametros) {

		String select = "select * from " + classe.getSimpleName();

		String condicaoWhere = Query.criaCondicaoWhere_final(parametros);

		String querySelect = select + condicaoWhere;

		return devolveListaBaseadoEmSQL_final(classe, querySelect);
	}
	
	public <T> Object devolveObjeto(Class<T> classe, Object... parametros) {

		String select = "select * from " + classe.getSimpleName();

		String condicaoWhere = Query.criaCondicaoWhere_final(parametros);

		String querySelect = select + condicaoWhere;

		return devolveObjetoBaseadoEmSQL_final(classe, querySelect);
	}

	public void deletaTodosDados() {

		SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();

		for (String tabela : ListaComTabelasModel.devolveListaComTabelasModel()) {

			sQLiteDatabase.execSQL("delete from " + tabela);
		}
		sQLiteDatabase.close();
	}

	public void deletaObjeto(Class<?> classe, Object... parametros) {

		String delete = "delete from " + classe.getSimpleName();

		String condicaoWhere = Query.criaCondicaoWhere_final(parametros);

		String queryDelete = delete + condicaoWhere;

		SQLiteDatabase sQLiteDatabase = this.getWritableDatabase();
		sQLiteDatabase.execSQL(queryDelete);
		sQLiteDatabase.close();
	}

	private Object devolveObjetoBaseadoEmSQL_final(Class<?> classe, String querySelect) {

		SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();

		Cursor cursor = sQLiteDatabase.rawQuery(querySelect, null);

		Object objeto = null;

		if (cursor.moveToNext()) {

			objeto = PreencheObjetoUsandoCursor.devolveObjetoPreenchido(classe, cursor);
		}
		cursor.close();
		sQLiteDatabase.close();

		return objeto;
	}

	private <T> List<T> devolveListaBaseadoEmSQL_final(Class<T> classe, String querySelect) {

		List<T> lista = new ArrayList<T>();

		try {
			SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();

			Cursor cursor = sQLiteDatabase.rawQuery(querySelect, null);

			while (cursor.moveToNext()) {

				lista.add((T) PreencheObjetoUsandoCursor.devolveObjetoPreenchido(classe, cursor));
			}
			cursor.close();
			sQLiteDatabase.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (List<T>) lista;
	}
	
	public int selectDistinct(String parametro, Class classe) {

		String sql = "SELECT distinct "+parametro+" from " + classe.getSimpleName();

		return devolveQtdEncontrada(sql);
	}	

	private int devolveQtdEncontrada(String sql){

		int qtd = 0;

		SQLiteDatabase sQLiteDatabase = this.getReadableDatabase();

		Cursor cursor = sQLiteDatabase.rawQuery(sql, null);

		if (cursor.moveToFirst()) {

			qtd = cursor.getInt(0);
		}
		cursor.close();
		sQLiteDatabase.close();

		return qtd;
	}

}
