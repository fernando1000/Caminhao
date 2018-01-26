package br.com.consigaz.caminhao.fragmentpageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import br.com.consigaz.caminhao.fragment.ConfiguracaoSetupFragment;
import br.com.consigaz.caminhao.fragment.ConfiguracaoUsuarioFragment;
import br.com.consigaz.caminhao_sharedlib.modelo.Pda;

public class ConfiguracaoFragmentPagerAdapter extends FragmentPagerAdapter {
	
	private final int TOTAL_PAGINAS = 2;	
	private Pda pdaLogado;
	
    public ConfiguracaoFragmentPagerAdapter(FragmentManager fm, Pda _pdaLogado) {
        super(fm);
        this.pdaLogado = _pdaLogado;
    }
 
    @Override
	public Fragment getItem(int position) {
		
        switch (position) {
        
        case 0:
        	return new ConfiguracaoUsuarioFragment(pdaLogado);
        	
        case 1:
        	return new ConfiguracaoSetupFragment(pdaLogado);  	
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
			titleFragment = "Usuário";	
		}
		else if(position == 1){
			titleFragment = "Setup";	
		}
		
		return titleFragment;
    }
}