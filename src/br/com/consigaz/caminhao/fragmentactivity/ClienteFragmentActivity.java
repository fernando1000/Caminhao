package br.com.consigaz.caminhao.fragmentactivity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import br.com.consigaz.caminhao.R;
import br.com.consigaz.caminhao.activity.ListaClientesActivity;
import br.com.consigaz.caminhao.fragmentpageradapter.ClienteFragmentPagerAdapter;
import br.com.consigaz.caminhao.modelo.NotaFiscal;

public class ClienteFragmentActivity extends FragmentActivity{
	 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmentactivity_cliente);
		
		ActionBar actionBar = getActionBar();
				  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));

		FragmentManager fragmentManager = getSupportFragmentManager();
		
		Bundle bundle = getIntent().getExtras();

		NotaFiscal notaFiscal = (NotaFiscal) bundle.getSerializable("notaFiscal");
			
		ClienteFragmentPagerAdapter clienteFragmentPagerAdapter = new ClienteFragmentPagerAdapter(fragmentManager, notaFiscal);
		
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerCliente);
				  viewPager.setAdapter(clienteFragmentPagerAdapter);
	}
	@Override
	public void onBackPressed() {
		
		  startActivity(new Intent(ClienteFragmentActivity.this, ListaClientesActivity.class));
		  finish();
	}
	
}