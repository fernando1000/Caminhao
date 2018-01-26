package br.com.consigaz.caminhao.activity;

import java.util.List;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import br.com.consigaz.caminhao.R;
import br.com.consigaz.caminhao.dao.Dao;
import br.com.consigaz.caminhao.util.Animacao;
import br.com.consigaz.caminhao.util.TelaBuilder;
import br.com.consigaz.caminhao_sharedlib.modelo.Pda;

public class SplashActivity extends Activity {

	private static int SPLASH_TIME_OUT = 2000;
	private Context context;
	private LinearLayout linearLayout_tela;
	private ImageView imageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
		actionBar.setTitle("Splash");

		context = SplashActivity.this;

		setContentView(constroiTelaInicial());

		fazAnimacao();
		
		aguardaUmTempoEfazAlgo();
	}
	
	private LinearLayout constroiTelaInicial() {

		TelaBuilder layoutBuilder = new TelaBuilder(context);

		linearLayout_tela = layoutBuilder.criaLinearLayout();
		linearLayout_tela.setBackgroundColor(context.getResources().getColor(R.color.branco));

					  			  imageView = layoutBuilder.criaImageView("consigaz");
		linearLayout_tela.addView(imageView);

		return linearLayout_tela;
	}

	private void fazAnimacao(){

		Animacao animacao = new Animacao();
		 		 animacao.startAnimations(context, linearLayout_tela, imageView);
	}
	
	private void aguardaUmTempoEfazAlgo(){
		
		new android.os.Handler().postDelayed(new Runnable() {
			@Override
			public void run() {

				verificaSeExisteAlguemLogado();				
			}
		}, SPLASH_TIME_OUT);
	}
	
	private void verificaSeExisteAlguemLogado(){
		
		Dao dao = new Dao(context);

		List<Pda> listaComPdas = dao.listaTodaTabela(Pda.class);

		if (listaComPdas.isEmpty()) {

			startActivity(new Intent(context, LoginActivity.class));
		} else {
			startActivity(new Intent(context, MenuActivity.class));
		}
		finish();
	}
	
}