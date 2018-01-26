package br.com.consigaz.caminhao.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.LinearLayout;
import br.com.consigaz.caminhao.R;
import br.com.consigaz.caminhao.util.TelaBuilder;

public class ComunicarActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ActionBar actionBar = getActionBar();
				  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
				  actionBar.setTitle("Comunicação");
		
		Context context = ComunicarActivity.this;
		
		TelaBuilder layoutBuilder = new TelaBuilder(context);
		
		LinearLayout linearLayoutTela =  layoutBuilder.criaLinearLayout();
		
		setContentView(linearLayoutTela);
	}
	
	@Override
	public void onBackPressed() {
		 
		  startActivity(new Intent(ComunicarActivity.this, MenuActivity.class));
		  finish();
	}

}
