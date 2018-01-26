package br.com.consigaz.caminhao.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import br.com.consigaz.caminhao.R;
import br.com.consigaz.caminhao.activity.ListaClientesActivity;
import br.com.consigaz.caminhao.enums.Auxiliar;
import br.com.consigaz.caminhao.modelo.NotaFiscal;
import br.com.consigaz.caminhao.util.I_Venda;
import br.com.consigaz.caminhao.util.TelaBuilder;

public class VendaClienteFragment extends Fragment implements View.OnClickListener{

	private Context context;	
	private int viewId;
	private EditText editText_cliente;
	private EditText editText_condicao;
	private EditText editText_entrega;
	private EditText editText_pedido;
	private EditText editText_total;

	public VendaClienteFragment(int _viewId){
		this.viewId = _viewId;
	}
	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {

		VendaClienteFragment.this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		context = container.getContext();

		return constroiTelaInicial();
	}
	
	private LinearLayout constroiTelaInicial(){
		
		TelaBuilder telaBuilder = new TelaBuilder(context);
		
		LinearLayout linearLayout_tela = telaBuilder.criaLinearLayout();
					 linearLayout_tela.addView(telaBuilder.criaTextView("Cliente"));
		
			editText_cliente = new EditText(context);
			editText_cliente.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
				Log.i("tag", "passou pelo onClick do editText_cliente");
				
				acaoAposClick();
			}
		});
				     linearLayout_tela.addView(editText_cliente);
					
					 linearLayout_tela.addView(telaBuilder.criaTextView("Condição"));
					 						   editText_condicao = telaBuilder.criaEditText("");
					 linearLayout_tela.addView(editText_condicao);
				 
					 linearLayout_tela.addView(telaBuilder.criaTextView("Entrega"));
					 						   editText_entrega = telaBuilder.criaEditText("");
					 linearLayout_tela.addView(editText_entrega);
	
					 linearLayout_tela.addView(telaBuilder.criaTextView("Pedido"));
					 						   editText_pedido = telaBuilder.criaEditText("");
					 linearLayout_tela.addView(editText_pedido);
	
					 linearLayout_tela.addView(telaBuilder.criaTextView("Total"));
					 						   editText_total = telaBuilder.criaEditText("");	 
					 linearLayout_tela.addView(editText_total);
	
		return linearLayout_tela;		 
	}
		
	private void acaoAposClick() {

	    				Intent intent = new Intent(context, ListaClientesActivity.class); 
		   	   				   intent.putExtra("venda", true);
		startActivityForResult(intent, Auxiliar.VENDA_FAZENDO_REQUISICAO.getValor());		
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		
		if(requestCode == Auxiliar.VENDA_FAZENDO_REQUISICAO.getValor() && resultCode == Auxiliar.REQUISICAO_VEIO_DA_VENDA.getValor()){
			
			NotaFiscal notaFiscal = (NotaFiscal) intent.getSerializableExtra("notaFiscal");
			
			if(notaFiscal.getCliente_pedido() != null){
				
				editText_cliente.setText(notaFiscal.getCliente_pedido().getNome_emit());
			}
			if(notaFiscal.getCondicao_pagamento() != null){
				
				editText_condicao.setText(notaFiscal.getCondicao_pagamento().getDescricao());
			}
			if(notaFiscal.getLocal_entrega_pedido() != null){
				
				editText_entrega.setText(notaFiscal.getLocal_entrega_pedido().getEndereco());
			}
			if(notaFiscal.getPedido() != null){
				
				editText_pedido.setText(notaFiscal.getPedido().getPedido_solic());
			}
			editText_total.setText(""+ notaFiscal.getTotal());
					
		}
		
	}
	
	private I_Venda i_Venda;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);

		i_Venda = (I_Venda) context;
	}

	@Override
	public void onClick(View view) {
		
		Log.i("tag", "passou pelo onClick da Classe");
		
		//if (view.getId() == R.id.button_fragProrrogacaoDesconto_editar) {

			//i_Venda.clicouNoVendaClienteFragment("button_prorrogacaodesconto_editar");
		//}

	}
	


	
	
}
