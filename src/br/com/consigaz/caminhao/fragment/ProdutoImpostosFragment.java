package br.com.consigaz.caminhao.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ProdutoImpostosFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
				
		//nao vou precisar dessa classe porque as informacoes ja exitem no fragment ProdutoDetalhe
		
		return new LinearLayout(container.getContext());
	}
	
}