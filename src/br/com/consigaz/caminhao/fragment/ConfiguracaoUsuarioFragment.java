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
import br.com.consigaz.caminhao_sharedlib.modelo.Pda;

public class ConfiguracaoUsuarioFragment extends Fragment {

	private Context context;
	private Pda pdaLogado;
	
	public ConfiguracaoUsuarioFragment(Pda _pdaLogado){
		this.pdaLogado = _pdaLogado;
	}
	@Override
	public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {

		context = viewGroup.getContext();

		TelaBuilder telaBuilder = new TelaBuilder(context);

		return constroiTelaPrehPopulada(telaBuilder);
	}

	private LinearLayout constroiTelaPrehPopulada(TelaBuilder telaBuilder) {
		
		LinearLayout linearLayoutTela = telaBuilder.criaLinearLayout();
		linearLayoutTela.addView(telaBuilder.criaTextView("Empresa"));
		linearLayoutTela.addView(telaBuilder.criaEditText(pdaLogado.getEmpresa()));

		linearLayoutTela.addView(telaBuilder.criaTextView("Usuário"));
		EditText editText_usuario = telaBuilder.criaEditTextSOH_NUMEROS(6);
		editText_usuario.setText(pdaLogado.getNr_pda());
		linearLayoutTela.addView(editText_usuario);

		linearLayoutTela.addView(telaBuilder.criaTextView("Senha"));
		EditText editText_senha = telaBuilder.criaEditTextSOH_NUMEROS(6);
		editText_senha.setText(""+ pdaLogado.getSenha());
		linearLayoutTela.addView(editText_senha);

		return linearLayoutTela;
	}

}
