package br.com.consigaz.caminhao.activity;

import java.util.ArrayList;
import java.util.List;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import br.com.consigaz.caminhao.adapter.VendaArrayAdapter;
import br.com.consigaz.caminhao.dao.Dao;
import br.com.consigaz.caminhao.fragmentactivity.VendaFragmentActivity;
import br.com.consigaz.caminhao.modelo.NotaFiscal;
import br.com.consigaz.caminhao.util.TelaBuilder;
import br.com.consigaz.caminhao_sharedlib.modelo.Cliente_pedido;
import br.com.consigaz.caminhao_sharedlib.modelo.Item_nfe;
import br.com.consigaz.caminhao_sharedlib.modelo.Produto;
import br.com.consigaz.caminhao.R;

public class ListaVendasActivity extends Activity {

	private Context context;
	private ListView listView;
	private List<NotaFiscal> listaDeNotasFiscais;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
				  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
				  actionBar.setTitle("Lista de Vendas");

		context = ListaVendasActivity.this;
		
		setContentView(constroiTelaInicial());
	}
	
	private LinearLayout constroiTelaInicial(){

		TelaBuilder layoutBuilder = new TelaBuilder(context);
		
		LinearLayout linearLayoutTela = layoutBuilder.criaLinearLayout();
		
		populaListaDeNotasFiscais();
		
		VendaArrayAdapter vendaAdapter = new VendaArrayAdapter(context, R.layout.adapter_venda, listaDeNotasFiscais);

		listView = layoutBuilder.criaListView();
		listView.setAdapter(vendaAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				abreVendaFragActivity(view.getId());				
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
				
				solicitaConfirmacaoCancelamento();
	
				return true;
			}
		});
		
		LinearLayout linearLayout_holderListView = layoutBuilder.criaLinearLayoutHOLDER(0.10f);		
					 linearLayout_holderListView.addView(listView);

		linearLayoutTela.addView(linearLayout_holderListView);
		
		LinearLayout linearLayout_holderButton = layoutBuilder.criaLinearLayoutHOLDER(0.90f);
		
		
		
		Button button_adicionarVenda = layoutBuilder.criaButtonParaListaDeVendas("Adicionar Venda");
			   button_adicionarVenda.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
				abreVendaFragActivity(view.getId());			
			}
		});
					 linearLayout_holderButton.addView(button_adicionarVenda);
		
		linearLayoutTela.addView(linearLayout_holderButton);
		
		return linearLayoutTela;
	}
	
	private void abreVendaFragActivity(int viewId){
		
		Bundle data = new Bundle();
			   data.putInt("viewId", viewId);
		   
		Intent intent = new Intent(context, VendaFragmentActivity.class);
			   intent.putExtras(data);

		startActivityForResult(intent, viewId);
	}

	private void solicitaConfirmacaoCancelamento(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Atenção").setMessage("Cancelar nota Fiscal?")
			   .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						Log.e("SIM", "" + id);
					}
				})
			   .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						Log.e("NAO", "" + id);
					}
				});
		builder.show();

	}
		
	private void populaListaDeNotasFiscais(){
		
		Dao dao = new Dao(context);
		
		String a = "2";
		String b = "0414363";
		String itCodigo = "GC0020";
		
		Item_nfe item_nfe = (Item_nfe) dao.devolveObjeto(Item_nfe.class, 
						  								 Item_nfe.COLUMN_INTEGER_COD_ESTABEL, 1, 
						  								 Item_nfe.COLUMN_TEXT_SERIE_REMESSA, " '"+ a +"' ",
						  								 Item_nfe.COLUMN_TEXT_NR_NOTA_FIS_REMESSA, " '"+ b +"' ", 
						  								 Item_nfe.COLUMN_INTEGER_NR_SEQ_FAT, 10,   						  
						  								 Item_nfe.COLUMN_TEXT_IT_CODIGO, " '"+ itCodigo +"' ");
		
		Produto produto = (Produto) dao.devolveObjeto(Produto.class, Produto.COLUMN_TEXT_IT_CODIGO, " '"+ itCodigo +"' ");
		
		Cliente_pedido cliente_pedido = (Cliente_pedido) dao.devolveObjeto(Cliente_pedido.class, Cliente_pedido.COLUMN_INTEGER_COD_EMITENTE, 10314);
		
		NotaFiscal notaFiscal = new NotaFiscal();
				   notaFiscal.setItem_nfe(item_nfe);
				   notaFiscal.setProduto(produto);
				   notaFiscal.setCliente_pedido(cliente_pedido);
				   
		listaDeNotasFiscais = new ArrayList<NotaFiscal>();
		listaDeNotasFiscais.add(notaFiscal);		   
		
	}
	
	@Override
	public void onBackPressed() {
	
		startActivity(new Intent(ListaVendasActivity.this, MenuActivity.class));
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

		if (intent != null) {

			for (NotaFiscal venda : listaDeNotasFiscais) {

				if (venda.getIdInterno() == requestCode) {
				}
			}
			((VendaArrayAdapter) listView.getAdapter()).notifyDataSetChanged();
		}
	}

}
