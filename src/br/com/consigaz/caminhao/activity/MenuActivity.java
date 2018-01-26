package br.com.consigaz.caminhao.activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import br.com.consigaz.caminhao.adapter.DrawerItemArrayAdapter;
import br.com.consigaz.caminhao.dao.Dao;
import br.com.consigaz.caminhao.enums.VersaoAplicativo;
import br.com.consigaz.caminhao.fragmentactivity.ClienteFragmentActivity;
import br.com.consigaz.caminhao.fragmentactivity.ConfiguracaoFragmentActivity;
import br.com.consigaz.caminhao.modelo.DrawerItem;
import br.com.consigaz.caminhao.util.Dialogs;
import br.com.consigaz.caminhao_sharedlib.modelo.Embarque;
import br.com.consigaz.caminhao_sharedlib.modelo.Pda;
import br.com.consigaz.caminhao_sharedlib.modelo.Retorno_embarque;
import br.com.consigaz.caminhao.R;

public class MenuActivity extends Activity {

	private static int SPLASH_TIME_OUT = 1000;
	
	private Context context;
	private ActionBar actionBar;
	private DrawerLayout drawerLayout;
	private ListView listViewDrawerList;
	private ActionBarDrawerToggle actionBarDrawerToggle;
	private DrawerItemArrayAdapter customDrawerAdapter;
	private List<DrawerItem> listaDeDrawerItem;
	private Dao dao;
	private Pda pdaLogado;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_sistema);

		context = MenuActivity.this;
				
		dao = new Dao(context);
		
		pdaLogado = new Pda();
		pdaLogado = dao.listaTodaTabela(Pda.class).get(0);
				
		actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
		actionBar.setTitle("PDA:"+ pdaLogado.getNr_pda());

		TextView versao = (TextView) findViewById(R.id.versao);
				 versao.setText("Versão: "+ VersaoAplicativo.VERSAO_NUMERO.getNumero() );

		listaDeDrawerItem = new ArrayList<DrawerItem>();
		listaDeDrawerItem.add(new DrawerItem("Opções:"));
		listaDeDrawerItem.add(new DrawerItem("Vendas", R.drawable.ic_shopping_cart_black_24dp));
		listaDeDrawerItem.add(new DrawerItem("Clientes", R.drawable.ic_people_black_24dp));
		listaDeDrawerItem.add(new DrawerItem("Produtos", R.drawable.ic_palette_black_24dp));
		listaDeDrawerItem.add(new DrawerItem("Comunicar", R.drawable.ic_sync_black_24dp));
		listaDeDrawerItem.add(new DrawerItem("Configurar", R.drawable.ic_settings_black_24dp));

		customDrawerAdapter = new DrawerItemArrayAdapter(this, R.layout.custom_drawer_item, listaDeDrawerItem);

		listViewDrawerList = (ListView) findViewById(R.id.left_drawer);
		listViewDrawerList.setAdapter(customDrawerAdapter);
		listViewDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

		actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.drawable.ic_drawer, R.string.enviar, R.string.salvar) {
			public void onDrawerClosed(View view) {
				invalidateOptionsMenu();
			}
			public void onDrawerOpened(View drawerView) {
				invalidateOptionsMenu();
			}
		};

		drawerLayout.setDrawerListener(actionBarDrawerToggle);
	
		if (savedInstanceState == null) {

			SelectItem(0);
		}
	}

	public void SelectItem(int possition) {

		if (possition != 0) {

			switch (possition) {

			case 1:
				
				Embarque embarque = (Embarque) dao.devolveObjeto(Embarque.class, Embarque.COLUMN_TEXT_NR_PDA, " '"+pdaLogado.getNr_pda()+"' ");	

				Retorno_embarque retorno_embarque = (Retorno_embarque) dao.devolveObjeto(Retorno_embarque.class, 
																						 Retorno_embarque.COLUMN_INTEGER_NR_EMBARQUE, embarque.getNr_embarque());
				
				if(retorno_embarque == null){
					
					startActivity(new Intent(context, InicioAtividadeActivity.class));
				}else{
					startActivity(new Intent(context, ListaVendasActivity.class));
				}
				
				finish();
				break;

			case 2:
			           Intent intent2 = new Intent(context, ListaClientesActivity.class); 
				      		  intent2.putExtra("venda", false);
				startActivity(intent2);	
				finish();
				break;

			case 3:
		  		startActivity(new Intent(context, ListaProdutosActivity.class));	
				finish();
				break;
				
			case 4:
				startActivity(new Intent(context, ComunicarActivity.class));
				finish();
				break;
				
			case 5:
				       Intent intent = new Intent(context, ConfiguracaoFragmentActivity.class); 
						      intent.putExtra("Pda", (Serializable) pdaLogado);
				startActivity(intent);
				finish();
				break;
				
			default:
				break;
			}
		}
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		actionBarDrawerToggle.syncState();
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		actionBarDrawerToggle.onConfigurationChanged(newConfig);
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return false;
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (listaDeDrawerItem.get(position).getTitle() == null) {
				SelectItem(position);
			}
		}
	}
	
	@Override
	public void onBackPressed() {

		solicitaConfirmacaoDeSaida();
	}

	private void solicitaConfirmacaoDeSaida() {
		
		final Dialogs dialogs = new Dialogs();
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
				//context, R.style.dialogSucess);
		builder.setTitle("Sair")
				.setIcon(R.drawable.ic_highlight_off_black_24dp)
				.setMessage("Sair do Aplicativo")
				.setPositiveButton("Sim",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {

								final ProgressDialog progressDialog = dialogs.criaProgressDialog(context, "Saindo...");
											
								aguardaUmTempoEfazAlgo(progressDialog, dialogs);				
							}
						})
				.setNegativeButton("Não", new DialogInterface.OnClickListener() {public void onClick(DialogInterface dialog, int id) {}});
		
		Dialog dialog = builder.show();

		int dividerId = dialog.getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
		View divider = dialog.findViewById(dividerId);
			 divider.setBackgroundColor(dialog.getContext().getResources().getColor(R.color.azul_consigaz)); 
	}

	private void aguardaUmTempoEfazAlgo(final ProgressDialog progressDialog, final Dialogs dialogs){
		
		new android.os.Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				dialogs.encerraProgressDialog(progressDialog);
				
				finishAffinity();
			}
		}, SPLASH_TIME_OUT);
	}
	
}
