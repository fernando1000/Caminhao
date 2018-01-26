package br.com.consigaz.caminhao.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import br.com.consigaz.caminhao.R;
import br.com.consigaz.caminhao.dao.Dao;
import br.com.consigaz.caminhao.util.TelaBuilder;
import br.com.consigaz.caminhao_sharedlib.modelo.Embarque;
import br.com.consigaz.caminhao_sharedlib.modelo.Pda;
import br.com.consigaz.caminhao_sharedlib.modelo.Retorno_embarque;

public class InicioAtividadeActivity extends Activity {

	private Context context;
	private EditText editText_registroMotorista;
	private EditText editText_registroAjudante;
	private EditText editText_quilometragem;
	private Embarque embarque;
	private Dao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
				  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
				  actionBar.setTitle("Inicio da Atividade");

		context = InicioAtividadeActivity.this;

		TelaBuilder layoutBuilder = new TelaBuilder(context);

		setContentView(constroiTelaInicial(layoutBuilder));

		dao = new Dao(context);

		carregaTabelasQueUtilizaremos();
	}

	private LinearLayout constroiTelaInicial(TelaBuilder layoutBuilder) {

		LinearLayout linearLayoutTela = layoutBuilder.criaLinearLayout();
					 linearLayoutTela.addView(layoutBuilder.criaTextView("Registro do Motorista"));
					 						  editText_registroMotorista = layoutBuilder.criaEditTextSOH_NUMEROS(6); 
					 linearLayoutTela.addView(editText_registroMotorista);

					 linearLayoutTela.addView(layoutBuilder.criaTextView("Registro do Ajudante"));
					 						  editText_registroAjudante = layoutBuilder.criaEditTextSOH_NUMEROS(6);
					 linearLayoutTela.addView(editText_registroAjudante);

					 linearLayoutTela.addView(layoutBuilder.criaTextView("Quilometragem"));
					 						  editText_quilometragem = layoutBuilder.criaEditTextSOH_NUMEROS(8);
					 linearLayoutTela.addView(editText_quilometragem);

		Button button = layoutBuilder.criaButton("Confirmar");
			   button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				acaoAposClick();
			}
		});
			   linearLayoutTela.addView(button);
		return linearLayoutTela;
	}

	private void carregaTabelasQueUtilizaremos() {

		Pda pdaLogado = dao.listaTodaTabela(Pda.class).get(0);

		embarque = (Embarque) dao.devolveObjeto(Embarque.class, Embarque.COLUMN_TEXT_NR_PDA, " '"+ pdaLogado.getNr_pda() +"' ");
		
		if(embarque == null){
			
			Toast.makeText(context, " Embarque não encontrado", Toast.LENGTH_SHORT).show();
		}else{
			Log.i("tag", "embarque tem informacao");		
		}
	}

	private void acaoAposClick(){
		
		if(!temCamposEmbranco()){
			
			if(!digitouErrado()){
			
				Retorno_embarque retorno_embarque = new Retorno_embarque();
								 retorno_embarque.setNr_embarque(embarque.getNr_embarque());
								 retorno_embarque.setHodometro_inicial(editText_quilometragem.getText().toString());
				
				dao.insereOUatualiza(retorno_embarque, Retorno_embarque.COLUMN_INTEGER_NR_EMBARQUE, embarque.getNr_embarque());
				
				startActivity(new Intent(InicioAtividadeActivity.this, ListaVendasActivity.class));
				finish();
			}
		}	
	}
	
	private boolean temCamposEmbranco(){
	
		boolean deuErro = false;
		
		if(editText_registroMotorista.getText().length() == 0){
	
			Toast.makeText(context, "O registro do motorista deve ser informado", Toast.LENGTH_SHORT).show();
			deuErro = true;
		}
		if(editText_registroAjudante.getText().length() == 0){
			
			Toast.makeText(context, "O registro do ajudante deve ser informado", Toast.LENGTH_SHORT).show();
			deuErro = true;
		}
		if(editText_quilometragem.getText().length() == 0){
			
			Toast.makeText(context, "A quilometragem deve ser informada", Toast.LENGTH_SHORT).show();
			deuErro = true;
		}
		return deuErro;
	}

	private boolean digitouErrado(){
		
		boolean digitouErrado = false;
		
		if(Integer.valueOf(editText_registroMotorista.getText().toString()) != embarque.getNr_registro_motorista()){
			
			Toast.makeText(context, "O registro do motorista esta incorreto!", Toast.LENGTH_SHORT).show();
			digitouErrado = true;
		}
		if(Integer.valueOf(editText_registroAjudante.getText().toString()) != embarque.getNr_registro_ajudante()){
			
			Toast.makeText(context, "O registro do ajudante esta incorreto!", Toast.LENGTH_SHORT).show();
			digitouErrado = true;
		}

		return digitouErrado;
	}
	
	@Override
	public void onBackPressed() {

		startActivity(new Intent(InicioAtividadeActivity.this, MenuActivity.class));
		finish();
	}

}
