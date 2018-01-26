package br.com.consigaz.caminhao.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

public class Dialogs {

	public void encerraProgressDialog(ProgressDialog progressDialog) {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public void encerraAlertDialog(AlertDialog alertDialog) {
		if (alertDialog != null) {
			alertDialog.dismiss();
			alertDialog = null;
		}
	}

	public ProgressDialog criaProgressDialog(Context context, String mensagem){
		
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setMessage(mensagem);
		progressDialog.show();
		
		return progressDialog;
	}
	
}
