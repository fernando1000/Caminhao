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
import br.com.consigaz.caminhao.activity.ListaProdutosActivity;
import br.com.consigaz.caminhao.fragmentpageradapter.ProdutoFragmentPagerAdapter;
import br.com.consigaz.caminhao_sharedlib.modelo.Produto;

public class ProdutoFragmentActivity extends FragmentActivity{
	 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragmentactivity_produto);

		ActionBar actionBar = getActionBar();
				  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));

		FragmentManager fragmentManager = getSupportFragmentManager();
		
		Bundle data = getIntent().getExtras();

		Produto produto = (Produto) data.getSerializable("produto");
			
		ProdutoFragmentPagerAdapter produtoFragmentPagerAdapter = new ProdutoFragmentPagerAdapter(fragmentManager, produto);
		
		ViewPager viewPager = (ViewPager) findViewById(R.id.viewPagerProduto);
				  viewPager.setAdapter(produtoFragmentPagerAdapter);
	}
	@Override
	public void onBackPressed() {
		
		  startActivity(new Intent(ProdutoFragmentActivity.this, ListaProdutosActivity.class));
		  finish();
	}
}