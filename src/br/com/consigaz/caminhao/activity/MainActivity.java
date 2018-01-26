package br.com.consigaz.caminhao.activity;

import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import br.com.consigaz.caminhao.R;
import br.com.consigaz.caminhao.enums.IpServidor;
import br.com.consigaz.caminhao.enums.VersaoAplicativo;
import br.com.consigaz.caminhao.util.Dialogs;
import br.com.consigaz.caminhao_sharedlib.modelo.Configuracao;
import utilitarios.android.DownloadApk;
import utilitarios.android.VolleySingleton;
import utilitarios.android.VolleyTimeout;

public class MainActivity extends Activity {
	
	private static final String RESOURCE_REST = "/Configuracao/Versao/";
	private RequestQueue requestQueue;
	private ProgressDialog progressDialog;
	private AlertDialog alertDialog;
	private Context context;
	private Dialogs dialogs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ActionBar actionBar = getActionBar();
				  actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(getString(R.color.azul_consigaz))));
				  actionBar.setTitle("Main");
		
		context = MainActivity.this;		  
		
		buscarVersaoNoWeService();
	}

	private void buscarVersaoNoWeService() {

		requestQueue = VolleySingleton.getInstance(this).getRequestQueue();

		String url = IpServidor.URL_SERVER_REST.getValor() + RESOURCE_REST;

						 dialogs = new Dialogs();		  
		progressDialog = dialogs.criaProgressDialog(context, "Aguarde, validando versão...");

		JsonObjectRequest jsonObjRequest = new JsonObjectRequest(

				Request.Method.GET, 
				url, 
				null,

				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject jSONObject_resposta) {

						recebeResposta(jSONObject_resposta);
					}
				}, 
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError volleyError) {

						trataErro(volleyError);
					}
				});
		jsonObjRequest.setRetryPolicy(VolleyTimeout.recuperarTimeout());

		requestQueue.add(jsonObjRequest);
	}
	
	private void trataErro(VolleyError volleyError){
		
		dialogs.encerraProgressDialog(progressDialog);

		Toast.makeText(MainActivity.this, "Sem Internet! "+volleyError, Toast.LENGTH_LONG).show();

		abreSplash();
	}

	private void recebeResposta(JSONObject resposta) {

		try {

			Configuracao configuracaoExterna = new Configuracao();

			if (resposta.has("versaoApp")) {

				configuracaoExterna.setVersao_app(resposta.getInt("versaoApp"));
			}

			if (configuracaoExterna.getVersao_app() > VersaoAplicativo.VERSAO_NUMERO.getNumero()) {

				atualizaVersao();
			} 
			else {
				dialogs.encerraProgressDialog(progressDialog);

				abreSplash();
			}
		} 
		catch (Exception exception) {
			
			exception.printStackTrace();
			
			dialogs.encerraProgressDialog(progressDialog);

			abreSplash();
		}
	}

	private void abreSplash() {

		startActivity(new Intent(context, SplashActivity.class));
		finish();		
	}

	private void atualizaVersao() throws Exception {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		LinearLayout linear = (LinearLayout) inflater.inflate(R.layout.atualizar_app, null);

		//CrudSqliteDAO crudSqliteDAO = new CrudSqliteDAO(MainActivity.this);

		int total = 0; // crudSqliteDAO.countProspects();

		Button button_atualizar = (Button) linear.findViewById(R.id.btnApp_atualizar);

		TextView textView_desejaAtualizar = (TextView) linear.findViewById(R.id.desejaAtualizar);

		TextView textView_sincronizeSeusDadosParaAtualizar = (TextView) linear.findViewById(R.id.sincronizeSeusDadosParaAtualizar);

		if (total > 0) {

			textView_sincronizeSeusDadosParaAtualizar.setVisibility(View.VISIBLE);
			button_atualizar.setVisibility(View.GONE);
		} 
		else {
			textView_desejaAtualizar.setVisibility(View.VISIBLE);
		}

		button_atualizar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				dialogs.encerraProgressDialog(progressDialog);
				dialogs.encerraAlertDialog(alertDialog);
				
				new DownloadApk(context, "Caminhao", IpServidor.URL_SERVER_REST.getValor());
			}
		});
		
		Button buttonAdiar = (Button) linear.findViewById(R.id.btnApp_adiar);
		buttonAdiar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				dialogs.encerraProgressDialog(progressDialog);
				dialogs.encerraAlertDialog(alertDialog);
				
				abreSplash();
			}
		});
		
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
							builder.setView(linear);
		alertDialog = builder.show();
		alertDialog.setCanceledOnTouchOutside(false);
	}

}
