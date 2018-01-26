package br.com.consigaz.caminhao.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import br.com.consigaz.caminhao.adapter.NotaFiscalArrayAdapter;
import br.com.consigaz.caminhao.modelo.NotaFiscal;
import br.com.consigaz.caminhao.util.TelaBuilder;
import java.util.ArrayList;
import java.util.List;
import br.com.consigaz.caminhao.R;

public class VendaItensListFragment extends ListFragment {

	private Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		context = getContext();

		return constroiTelainicial();
	}
	
	private LinearLayout constroiTelainicial(){
		
		TelaBuilder telaBuilder = new TelaBuilder(context);

		LinearLayout linearLayout_tela = telaBuilder.criaLinearLayout();
			
		ListView listView =  telaBuilder.criaListView();
		listView.setId(android.R.id.list);
		listView.setAdapter(new NotaFiscalArrayAdapter(context, R.layout.adapter_notafiscal, populaListaComNotasFiscais()));
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				Log.i("normal", "posicao: " + position);
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

				mostraOpcaoEditarExcluir();

				return true;
			}
		});
		linearLayout_tela.addView(listView);
		
		return linearLayout_tela;
	}

	private List<NotaFiscal> populaListaComNotasFiscais(){

		List<NotaFiscal> listaDeNotasFiscais = new ArrayList<NotaFiscal>();
		
		return listaDeNotasFiscais;
	}
	
	private void mostraOpcaoEditarExcluir() {

		ArrayList<String> arrayList_itens = new ArrayList<String>();
						  arrayList_itens.add("Editar");
						  arrayList_itens.add("Excluir");
	
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, R.layout.item_menu_geral, arrayList_itens);

		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Menu");
		builder.setSingleChoiceItems(arrayAdapter, 0, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int posicao) {

				Log.v("posição selecionada= ", "" + posicao);

				dialogInterface.dismiss();
			}
		});
		builder.show();
	}
	
	public void recebeInformacao(Bundle bundle) {

		String informacao = bundle.getString("key");
	
	}

}
