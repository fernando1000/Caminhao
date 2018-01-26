package br.com.consigaz.caminhao.fragmentactivity;

import java.util.ArrayList;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import br.com.consigaz.caminhao.R;
import br.com.consigaz.caminhao.activity.ListaVendasActivity;
import br.com.consigaz.caminhao.fragment.VendaItensListFragment;
import br.com.consigaz.caminhao.fragmentpageradapter.VendaFragmentPagerAdapter;
import br.com.consigaz.caminhao.util.I_Venda;

public class VendaFragmentActivity extends FragmentActivity  implements I_Venda{

	private Context context;
	private FragmentManager fragmentManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmentactivity_notafiscal);

		ActionBar actionBar = getActionBar();
				  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));

		context = VendaFragmentActivity.this;
		
		fragmentManager = getSupportFragmentManager();

		Bundle bundle = getIntent().getExtras();

		int viewId = bundle.getInt("viewId");
	
		VendaFragmentPagerAdapter vendaFragmentPagerAdapter = new VendaFragmentPagerAdapter(fragmentManager, viewId);

		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerNotaFiscal);
				  viewPager.setAdapter(vendaFragmentPagerAdapter);

		Button button_menu = (Button) findViewById(R.id.button_listFragmentNotaFiscalItens_menu);
			   button_menu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				acaoAposClick();
			}
		});
	}

	private void acaoAposClick() {

		ArrayList<String> arrayList_itens = new ArrayList<String>();
		arrayList_itens.add("Resumo");
		arrayList_itens.add("Realizado");
		arrayList_itens.add("Não realizado");
		arrayList_itens.add("Recusado");
		arrayList_itens.add("Reprogramação");
		arrayList_itens.add("Romaneio");
		arrayList_itens.add("Imprimir");

		ArrayAdapter arrayAdapter = new ArrayAdapter(context, R.layout.item_menu_geral, arrayList_itens);

		AlertDialog.Builder alertDialog_Builder = new AlertDialog.Builder(context);
		alertDialog_Builder.setTitle("Menu");
		alertDialog_Builder.setSingleChoiceItems(arrayAdapter, 0, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialogInterface, int posicao) {

				acaoAposEscolhaDoItem(dialogInterface, posicao);		
			}
		});
		alertDialog_Builder.show();
	}

	private void acaoAposEscolhaDoItem(DialogInterface dialogInterface, int posicao){
		
		Log.i("posição selecionada= ", "" + posicao);

		dialogInterface.dismiss();
	}
	
	@Override
	public void onBackPressed() {

		startActivity(new Intent(VendaFragmentActivity.this, ListaVendasActivity.class));
		finish();
	}

	@Override
	public void clicouNoVendaClienteFragment(String mensagem) {
		
		Bundle bundle = new Bundle();
 	   		   bundle.putString("key", mensagem);

 	    VendaItensListFragment vendaItensListFragment = (VendaItensListFragment) fragmentManager.findFragmentByTag("fragProrrogacaoDescontoHolder");
 	    					   vendaItensListFragment.recebeInformacao(bundle); 
	}

}