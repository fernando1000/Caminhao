package br.com.consigaz.caminhao.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import br.com.consigaz.caminhao.util.TelaBuilder;
import br.com.consigaz.caminhao_sharedlib.modelo.Produto;

public class ProdutoDetalheFragment extends Fragment {

	private Context context;
	private Produto produto; 

	public ProdutoDetalheFragment(){}
	public ProdutoDetalheFragment(Produto _produto){
	      this.produto = _produto;
	}
	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {

		ProdutoDetalheFragment.this.getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		context = container.getContext();

		TelaBuilder layoutUtil = new TelaBuilder(context);
				
		ScrollView scrollView = layoutUtil.criaScrollView();
		
		if(produto != null){
			
			LinearLayout linearLayoutTela = layoutUtil.criaLinearLayout();
						 linearLayoutTela = layoutUtil.criaPopulaEAddWidgetsEmLinearLayout(linearLayoutTela, produto);
			
			scrollView.addView(linearLayoutTela);
		}
			   
		return scrollView;
	}
	
}