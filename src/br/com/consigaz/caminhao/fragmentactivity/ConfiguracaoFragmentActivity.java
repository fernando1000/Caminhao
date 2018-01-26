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
import br.com.consigaz.caminhao.activity.MenuActivity;
import br.com.consigaz.caminhao.fragmentpageradapter.ConfiguracaoFragmentPagerAdapter;
import br.com.consigaz.caminhao_sharedlib.modelo.Pda;

public class ConfiguracaoFragmentActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmentactivity_cliente);
		
		ActionBar actionBar = getActionBar();
				  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
				  actionBar.setTitle("Configuração");
				  
		FragmentManager fragmentManager = getSupportFragmentManager();
		
		Bundle bundle = getIntent().getExtras();

		Pda pdaLogado = (Pda) bundle.getSerializable("Pda");
		
		ConfiguracaoFragmentPagerAdapter configuracaoFragmentPagerAdapter = new ConfiguracaoFragmentPagerAdapter(fragmentManager, pdaLogado);
		
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerCliente);
				  viewPager.setAdapter(configuracaoFragmentPagerAdapter);
	}
	@Override
	public void onBackPressed() {
		
		  startActivity(new Intent(ConfiguracaoFragmentActivity.this, MenuActivity.class));
		  finish();
	}
	
}
