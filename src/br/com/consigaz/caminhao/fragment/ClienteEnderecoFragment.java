package br.com.consigaz.caminhao.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import br.com.consigaz.caminhao.modelo.NotaFiscal;
import br.com.consigaz.caminhao.util.TelaBuilder;

public class ClienteEnderecoFragment extends Fragment {

	private Context context;
	private NotaFiscal notaFiscal;

	public ClienteEnderecoFragment(NotaFiscal _notaFiscal){
		this.notaFiscal = _notaFiscal;
	}
	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
	
		context = container.getContext();

		TelaBuilder layoutUtil = new TelaBuilder(context);
			
		ScrollView scrollView = layoutUtil.criaScrollView();
		
		if(notaFiscal.getLocal_entrega_pedido() != null){
			
			LinearLayout linearLayoutTela = layoutUtil.criaLinearLayout();
						 linearLayoutTela = layoutUtil.criaPopulaEAddWidgetsEmLinearLayout(linearLayoutTela, notaFiscal.getLocal_entrega_pedido());
			
			scrollView.addView(linearLayoutTela);
		}
			   
		return scrollView;
	}
	
}