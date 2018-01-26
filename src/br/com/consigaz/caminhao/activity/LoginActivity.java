package br.com.consigaz.caminhao.activity;

import org.json.JSONException;
import org.json.JSONObject;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import br.com.consigaz.caminhao.R;
import br.com.consigaz.caminhao.dao.Dao;
import br.com.consigaz.caminhao.enums.IpServidor;
import br.com.consigaz.caminhao.util.Dialogs;
import br.com.consigaz.caminhao.util.RecebeJSONObjectLogin;
import br.com.consigaz.caminhao.util.TelaBuilder;
import br.com.consigaz.caminhao_sharedlib.enums.Generico;
import br.com.consigaz.caminhao_sharedlib.modelo.Pda;
import utilitarios.android.VolleySingleton;
import utilitarios.android.VolleyTimeout;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

public class LoginActivity extends Activity {

	private static final String RESOURCE_REST = "/Autenticacao/Login/";
	private RequestQueue requestQueue;
	private EditText editText_usuario;
	private EditText editText_senha;
	private ProgressDialog progressDialog;
	private Context context;
	private Dialogs dialogs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
				  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
				  actionBar.setTitle("Login");
		
		context = LoginActivity.this;
	
		//dialogs = new Dialogs();
				 
		setContentView(constroiTelaInicial());				 
	}

	private LinearLayout constroiTelaInicial() {
		
		TelaBuilder layoutBuilder = new TelaBuilder(context);

		LinearLayout linearLayoutTela = layoutBuilder.criaLinearLayout();
		linearLayoutTela.addView(layoutBuilder.criaTextView("Usuário"));
		editText_usuario = layoutBuilder.criaEditTextSOH_NUMEROS(6);
		linearLayoutTela.addView(editText_usuario);

		linearLayoutTela.addView(layoutBuilder.criaTextView("Senha"));
		editText_senha = layoutBuilder.criaEditTextSOH_NUMEROS(6);
		linearLayoutTela.addView(editText_senha);

		Button button_entrar = layoutBuilder.criaButton("Entrar");
		button_entrar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				acaoAposClique();
			}
		});
		linearLayoutTela.addView(button_entrar);
		linearLayoutTela.addView(layoutBuilder.criaImageView("consigaz"));

		return linearLayoutTela;
	}

	private void acaoAposClique() {

		if (!temCamposEmbranco()) {

			if (temUsuarioNoSqlite()) {
			
				abreMenu();
			}
			else{
				buscarNoWebService();
			}
		}
	}

	private boolean temCamposEmbranco() {

		boolean deuErro = false;

		if (editText_usuario.getText().length() == 0) {

			Toast.makeText(context, "O usuário deve ser informado", Toast.LENGTH_SHORT).show();
			deuErro = true;
		}
		if (editText_senha.getText().length() == 0) {

			Toast.makeText(context, "A senha deve ser informada", Toast.LENGTH_SHORT).show();
			deuErro = true;
		}

		return deuErro;
	}

	private boolean temUsuarioNoSqlite() {

		boolean achouUsuario = false;	
		
		Dao dao = new Dao(context);

		Pda pda_usuarioProcurado = (Pda) dao.devolveObjeto(Pda.class, Pda.COLUMN_TEXT_NR_PDA, " '" + editText_usuario.getText().toString() + "' ", 
																      Pda.COLUMN_INTEGER_SENHA, " '" + editText_senha.getText().toString() + "' ");

		if(pda_usuarioProcurado != null){
			achouUsuario = true;
		}
		
		return achouUsuario;
	}
	
	private void buscarNoWebService() {
		
		requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
		
		try {
			JSONObject jSONObject_params = new JSONObject();
					   jSONObject_params.put("nr_pda", editText_usuario.getText().toString());
					   jSONObject_params.put("senha", editText_senha.getText().toString());
		
			String url = IpServidor.URL_SERVER_REST.getValor() + RESOURCE_REST;
		   
							 dialogs = new Dialogs();
			progressDialog = dialogs.criaProgressDialog(context, "Autenticando PDA...");
			
			JsonObjectRequest jsonObjRequest = new JsonObjectRequest(

					Request.Method.POST, 
					url, 
					jSONObject_params,

					new Response.Listener<JSONObject>() {
						@Override
						public void onResponse(JSONObject jSONObject_resposta) {

							trataRespostaRecebida(jSONObject_resposta);
						}
					}, 
					new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError volleyError) {

							trataErroRecebido(volleyError);	
						}
					});

			jsonObjRequest.setRetryPolicy(VolleyTimeout.recuperarTimeout());

			requestQueue.add(jsonObjRequest);			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void trataRespostaRecebida(JSONObject jSONObject_resposta){
		
		try {
			if (jSONObject_resposta.getInt("achou_nr_pda") == Generico.ENCONTROU_USUARIO.getValor()) {

				RecebeJSONObjectLogin recebeJSONObjectImportar = new RecebeJSONObjectLogin(context);
				
				boolean deuErro = recebeJSONObjectImportar.inserePdaComTodasTabelas(jSONObject_resposta);
				
				if(!deuErro){
					
					dialogs.encerraProgressDialog(progressDialog);
					
					abreMenu();
				}
			} 
			else if (jSONObject_resposta.getInt("achou_nr_pda") == Generico.NAO_ENCONTROU_USUARIO.getValor()) {

				dialogs.encerraProgressDialog(progressDialog);
				
				Toast.makeText(context, "Usuário ou senha invalida", Toast.LENGTH_LONG).show();
			} 
			else if (jSONObject_resposta.getInt("achou_nr_pda") == Generico.OCORREU_ERRO.getValor()) {

				dialogs.encerraProgressDialog(progressDialog);
				
				Toast.makeText(context, "Caiu na @ Exceção @ do webservice", Toast.LENGTH_LONG).show();
			}
			else {
				dialogs.encerraProgressDialog(progressDialog);
				
				Toast.makeText(context, "Erro desconhecido :(", Toast.LENGTH_LONG).show();
			}
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private void trataErroRecebido(VolleyError volleyError){
	
		dialogs.encerraProgressDialog(progressDialog);

		Toast.makeText(context, "VolleyError" + volleyError, Toast.LENGTH_SHORT).show();			
	}

	private void abreMenu() {
	
		startActivity(new Intent(LoginActivity.this, MenuActivity.class));
		finish();
	}

}
