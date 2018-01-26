package br.com.consigaz.caminhao.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import br.com.consigaz.caminhao.adapter.ClienteArrayAdapter;
import br.com.consigaz.caminhao.dao.Dao;
import br.com.consigaz.caminhao.enums.Auxiliar;
import br.com.consigaz.caminhao.fragmentactivity.ClienteFragmentActivity;
import br.com.consigaz.caminhao.modelo.NotaFiscal;
import br.com.consigaz.caminhao.util.TelaBuilder;
import br.com.consigaz.caminhao_sharedlib.modelo.Cliente_pedido;
import br.com.consigaz.caminhao_sharedlib.modelo.Condicao_pagamento;
import br.com.consigaz.caminhao_sharedlib.modelo.Local_entrega_pedido;
import br.com.consigaz.caminhao_sharedlib.modelo.Pedido;
import br.com.consigaz.caminhao.R;

public class ListaClientesActivity extends Activity {

	private Context context;
	private ListView listView;
	private ClienteArrayAdapter clienteAdapter;
	private List<NotaFiscal> listaDeNotaFiscal;
	private boolean requisicaoVeioDaVenda = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
				  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
				  actionBar.setTitle("Lista de Clientes");

		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		context = ListaClientesActivity.this;
			
		Bundle bundle = getIntent().getExtras();
			
		if(bundle != null){
			
			requisicaoVeioDaVenda = bundle.getBoolean("venda");
		}

		setContentView(constroiTelaInicial());
	}
	
	private LinearLayout constroiTelaInicial(){
		
		TelaBuilder layoutBuilder = new TelaBuilder(context);

		LinearLayout linearLayoutTela = layoutBuilder.criaLinearLayout();
		
        EditText editText_pesquiza = new EditText(context);
		editText_pesquiza.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (count < before) {
					// We're deleting char so we need to reset the adapter data
					clienteAdapter.resetData();
				}
				clienteAdapter.getFilter().filter(s.toString());
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		linearLayoutTela.addView(editText_pesquiza);
		
		populaListaDeNotasFiscais();
		
		clienteAdapter = new ClienteArrayAdapter(context, R.layout.adapter_cliente, listaDeNotaFiscal);
		
		listView = layoutBuilder.criaListView();
		listView.setAdapter(clienteAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				acaoAposClique(view.getId(), position);
			}
		});
		linearLayoutTela.addView(listView);

		return linearLayoutTela;
	}
	
	private void populaListaDeNotasFiscais(){
		
		Dao dao = new Dao(context);
		
		listaDeNotaFiscal = new ArrayList<NotaFiscal>();
				
		for(Cliente_pedido cliente_pedido : dao.listaTodaTabela(Cliente_pedido.class)){
			
			NotaFiscal notaFiscal = new NotaFiscal();
					   notaFiscal.setCliente_pedido(cliente_pedido);	
					   
			
			Local_entrega_pedido locEntr = (Local_entrega_pedido)dao.devolveObjeto(Local_entrega_pedido.class, Local_entrega_pedido.COLUMN_INTEGER_COD_EMITENTE, cliente_pedido.getCod_emitente());
					   notaFiscal.setLocal_entrega_pedido(locEntr);
	
			Pedido pedido = (Pedido) dao.devolveObjeto(Pedido.class, Pedido.COLUMN_INTEGER_COD_EMITENTE, cliente_pedido.getCod_emitente());
					   notaFiscal.setPedido(pedido);
		
			if(pedido != null){
				
			Condicao_pagamento condPag = (Condicao_pagamento) dao.devolveObjeto(Condicao_pagamento.class, Condicao_pagamento.COLUMN_INTEGER_COD_COND_PAG, pedido.getCod_cond_pag());
					   notaFiscal.setCondicao_pagamento(condPag);
			}		
					   
			listaDeNotaFiscal.add(notaFiscal);
		}
		
	}

	private void acaoAposClique(int viewId, int position) {

		NotaFiscal notaFiscal = listaDeNotaFiscal.get(position);

		Bundle bundle = new Bundle();
			   bundle.putSerializable("notaFiscal", (Serializable) notaFiscal);
		
		Intent intent;
		
		if(requisicaoVeioDaVenda){
				
			intent = new Intent();
			intent.putExtras(bundle);

			setResult(Auxiliar.REQUISICAO_VEIO_DA_VENDA.getValor(), intent);

			finish();
		}
		else{
			intent = new Intent(context, ClienteFragmentActivity.class);
			intent.putExtras(bundle);

			startActivityForResult(intent, viewId);
		}
				
	}

	@Override
	public void onBackPressed() {

		Intent intent = new Intent(ListaClientesActivity.this, MenuActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

		if (intent != null) {
			for (NotaFiscal notaFiscal : listaDeNotaFiscal) {
				if (1 == requestCode) {

					notaFiscal.getCliente_pedido().getCod_emitente();
				}
			}
			((ClienteArrayAdapter) listView.getAdapter()).notifyDataSetChanged();
		}
	}

}
