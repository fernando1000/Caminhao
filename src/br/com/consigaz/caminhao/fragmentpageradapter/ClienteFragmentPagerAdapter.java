package br.com.consigaz.caminhao.fragmentpageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import br.com.consigaz.caminhao.fragment.ClienteEnderecoFragment;
import br.com.consigaz.caminhao.fragment.ClienteObservacaoFragment;
import br.com.consigaz.caminhao.fragment.ClienteResumoFragment;
import br.com.consigaz.caminhao.modelo.NotaFiscal;

public class ClienteFragmentPagerAdapter extends FragmentPagerAdapter {
	
	private final int TOTAL_PAGINAS = 3;	
	private NotaFiscal notaFiscal;
	
    public ClienteFragmentPagerAdapter(FragmentManager fm, NotaFiscal _notaFiscal) {
        super(fm);
        this.notaFiscal = _notaFiscal;
    }
 
    @Override
	public Fragment getItem(int position) {
		
        switch (position) {
        
        case 0:
        	return new ClienteResumoFragment(notaFiscal);
        	
        case 1:
        	return new ClienteEnderecoFragment(notaFiscal);
        	
        case 2:
        	return new ClienteObservacaoFragment();
        }
        return null;
	}

	@Override
	public int getCount() {
		return TOTAL_PAGINAS;
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
      
		CharSequence titleFragment = null;
		
		if(position == 0){
			titleFragment = "Resumo";	
		}
		else if(position == 1){
			titleFragment = "Endereço";	
		}
		else if(position == 2){
			titleFragment = "Observação";	
		}
		return titleFragment;
    }
}