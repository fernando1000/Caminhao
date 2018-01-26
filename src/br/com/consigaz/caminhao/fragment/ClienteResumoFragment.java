package br.com.consigaz.caminhao.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import br.com.consigaz.caminhao.modelo.NotaFiscal;
import br.com.consigaz.caminhao.util.TelaBuilder;

public class ClienteResumoFragment extends Fragment {
	
	private NotaFiscal notaFiscal;

	public ClienteResumoFragment(NotaFiscal _notaFiscal){
		this.notaFiscal = _notaFiscal;
	}
	
	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {

		ClienteResumoFragment.this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		Context context = container.getContext();
		
		TelaBuilder layoutUtil = new TelaBuilder(context);
		
		LinearLayout linearLayoutTela = layoutUtil.criaLinearLayout();

		if(notaFiscal.getCliente_pedido() != null){
			
			linearLayoutTela = layoutUtil.criaPopulaEAddWidgetsEmLinearLayout(linearLayoutTela, notaFiscal.getCliente_pedido());
		}
				
		return linearLayoutTela;
	}
	
}