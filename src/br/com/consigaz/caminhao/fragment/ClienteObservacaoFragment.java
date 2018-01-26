package br.com.consigaz.caminhao.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import br.com.consigaz.caminhao.util.TelaBuilder;

public class ClienteObservacaoFragment extends Fragment {

	private Context context;
	
	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
	
		context = container.getContext();

		TelaBuilder layoutUtil = new TelaBuilder(context);
		
		LinearLayout linearLayoutTela = layoutUtil.criaLinearLayout();
		
		EditText editText = new EditText(context);
		
			   linearLayoutTela.addView(editText);
		return linearLayoutTela;
	}
	
}