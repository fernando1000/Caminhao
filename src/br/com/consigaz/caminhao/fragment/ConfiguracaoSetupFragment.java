package br.com.consigaz.caminhao.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import br.com.consigaz.caminhao.util.TelaBuilder;
import br.com.consigaz.caminhao_sharedlib.modelo.Pda;

public class ConfiguracaoSetupFragment extends Fragment{
	
	private Context context;
	private Pda pdaLogado;
	
	public ConfiguracaoSetupFragment(Pda _pdaLogado){
		this.pdaLogado = _pdaLogado;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
		
		context = viewGroup.getContext();
		
		TelaBuilder telaBuilder = new TelaBuilder(context);
		
		return constroiTelaPrehPopulada(telaBuilder);
	}
	
	private LinearLayout constroiTelaPrehPopulada(TelaBuilder telaBuilder) {
		
		LinearLayout linearLayoutTela = telaBuilder.criaLinearLayout();
		linearLayoutTela.addView(telaBuilder.criaTextView("Placa"));
		linearLayoutTela.addView(telaBuilder.criaEditText(pdaLogado.getPlaca_veiculo()));

		linearLayoutTela.addView(telaBuilder.criaTextView("NF Modelo"));
		linearLayoutTela.addView(telaBuilder.criaEditText(String.valueOf(pdaLogado.getModelo_nota())));

		linearLayoutTela.addView(telaBuilder.criaTextView("Densidade"));
		linearLayoutTela.addView(telaBuilder.criaEditText(pdaLogado.getDensidade()));

		linearLayoutTela.addView(telaBuilder.criaTextView("Tipo Venda"));
		linearLayoutTela.addView(telaBuilder.criaEditText(String.valueOf(pdaLogado.getTipo_venda())));

		linearLayoutTela.addView(telaBuilder.criaTextView("Cancelar NF"));	 
		linearLayoutTela.addView(telaBuilder.criaEditText(String.valueOf(pdaLogado.getModelo_nota())));

		return linearLayoutTela;
	}

}
