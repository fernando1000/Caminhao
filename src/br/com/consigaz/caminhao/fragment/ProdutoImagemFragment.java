package br.com.consigaz.caminhao.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import br.com.consigaz.caminhao.util.TelaBuilder;

public class ProdutoImagemFragment extends Fragment {

	private Context context;
	private String itCodigo;
	
	public ProdutoImagemFragment(){}
	public ProdutoImagemFragment(String _itCodigo){
		this.itCodigo = _itCodigo;
	}
	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {

		context = container.getContext();

		TelaBuilder layoutUtil = new TelaBuilder(context);
		
		LinearLayout linearLayoutTela = layoutUtil.criaLinearLayout();
		
		ImageView imageView = layoutUtil.criaImageView(itCodigo);
				  
					 linearLayoutTela.addView(imageView);
		return linearLayoutTela;
	}
	

}