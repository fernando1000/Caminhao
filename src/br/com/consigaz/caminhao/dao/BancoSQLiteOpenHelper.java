package br.com.consigaz.caminhao.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import br.com.consigaz.caminhao_sharedlib.modelo.*;
import utilitarios.reflexao.Query;

public class BancoSQLiteOpenHelper extends SQLiteOpenHelper {

	public static final String BANCO_NOME = "caminhao";
	public static final int BANCO_VERSAO = 8;

	public BancoSQLiteOpenHelper(Context context) {
		super(context, BANCO_NOME, null, BANCO_VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase sQLiteDatabase) {
		
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Pda.class));
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Boleto.class));
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Cliente_pedido.class));
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Condicao_pagamento.class));
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Embarque.class));
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Estabelecimento_serie.class));		
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Instrucao_bancaria.class));		
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Item_nfe.class));		
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Justificativa.class));		
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Local_entrega_pedido.class));		
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Mensagem.class));		
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Natureza_operacao.class));		
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Pedido.class));		
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Produto.class));		
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Vendedor.class));
		
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Retorno_atendimento.class));
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Retorno_embarque.class));
		sQLiteDatabase.execSQL(Query.criaCreateTable_final(Retorno_ultima_nota_emitida.class));
				
	}

	@Override
	public void onUpgrade(SQLiteDatabase sQLiteDatabase, int oldVersion, int newVersion) {

		sQLiteDatabase.execSQL(Query.criaDropTable_final(Pda.class));
		sQLiteDatabase.execSQL(Query.criaDropTable_final(Boleto.class));
		sQLiteDatabase.execSQL(Query.criaDropTable_final(Cliente_pedido.class));
		sQLiteDatabase.execSQL(Query.criaDropTable_final(Condicao_pagamento.class));
		sQLiteDatabase.execSQL(Query.criaDropTable_final(Embarque.class));
		sQLiteDatabase.execSQL(Query.criaDropTable_final(Estabelecimento_serie.class));
		sQLiteDatabase.execSQL(Query.criaDropTable_final(Instrucao_bancaria.class));
		sQLiteDatabase.execSQL(Query.criaDropTable_final(Item_nfe.class));
		sQLiteDatabase.execSQL(Query.criaDropTable_final(Justificativa.class));
		sQLiteDatabase.execSQL(Query.criaDropTable_final(Local_entrega_pedido.class));
		sQLiteDatabase.execSQL(Query.criaDropTable_final(Mensagem.class));
		sQLiteDatabase.execSQL(Query.criaDropTable_final(Natureza_operacao.class));
		sQLiteDatabase.execSQL(Query.criaDropTable_final(Pedido.class));
		sQLiteDatabase.execSQL(Query.criaDropTable_final(Produto.class));
		sQLiteDatabase.execSQL(Query.criaDropTable_final(Vendedor.class));
			
		sQLiteDatabase.execSQL(Query.criaDropTable_final(Retorno_atendimento.class));
		sQLiteDatabase.execSQL(Query.criaDropTable_final(Retorno_embarque.class));
		sQLiteDatabase.execSQL(Query.criaDropTable_final(Retorno_ultima_nota_emitida.class));
		
		onCreate(sQLiteDatabase);
	}
}