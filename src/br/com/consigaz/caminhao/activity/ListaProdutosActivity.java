package br.com.consigaz.caminhao.activity;

import java.io.Serializable;
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
import br.com.consigaz.caminhao.R;
import br.com.consigaz.caminhao.adapter.ClienteArrayAdapter;
import br.com.consigaz.caminhao.adapter.ProdutoArrayAdapter;
import br.com.consigaz.caminhao.dao.Dao;
import br.com.consigaz.caminhao.fragmentactivity.ProdutoFragmentActivity;
import br.com.consigaz.caminhao.util.TelaBuilder;
import br.com.consigaz.caminhao_sharedlib.modelo.Produto;

public class ListaProdutosActivity extends Activity {

	private Context context;
	private ActionBar actionBar;
	private ListView listView;
	private ProdutoArrayAdapter produtoAdapter;
	private List<Produto> listaDeProdutos;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		context = ListaProdutosActivity.this;

		TelaBuilder layoutUtil = new TelaBuilder(context);
		
		LinearLayout linearLayoutTela = layoutUtil.criaLinearLayout();
		
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
		actionBar.setTitle("Lista de Produtos");
		
		
	    EditText editText_pesquiza = new EditText(context);	
		editText_pesquiza.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				if (count < before) {
					// We're deleting char so we need to reset the adapter data
					produtoAdapter.resetData();
				}
				produtoAdapter.getFilter().filter(s.toString());
			}
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		linearLayoutTela.addView(editText_pesquiza);

		Dao dao = new Dao(context);
		
		listaDeProdutos = dao.listaTodaTabela(Produto.class);

		produtoAdapter = new ProdutoArrayAdapter(context, R.layout.adapter_produto, listaDeProdutos);

		listView = layoutUtil.criaListView();
		listView.setAdapter(produtoAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				acaoAposClique(view.getId(), position);
			}
		});
		linearLayoutTela.addView(listView);

		setContentView(linearLayoutTela);
	}
	
	private void acaoAposClique(int viewId, int position){
		
		Produto produto = listaDeProdutos.get(position);				

		Bundle data = new Bundle();
			   data.putSerializable("produto", (Serializable) produto);

		Intent intent = new Intent(context, ProdutoFragmentActivity.class);
			   intent.putExtras(data);

		startActivityForResult(intent, viewId);
	}

	@Override
	public void onBackPressed() {

		Intent intent = new Intent(ListaProdutosActivity.this, MenuActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

		if (intent != null) {
			for (Produto produto : listaDeProdutos) {
				if (10000 == requestCode) { // getCod_emitente()

					produto.getAliquota_ipi();
				}
			}
			((ClienteArrayAdapter) listView.getAdapter()).notifyDataSetChanged();
		}
	}

}
