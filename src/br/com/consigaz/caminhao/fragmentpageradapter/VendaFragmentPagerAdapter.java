package br.com.consigaz.caminhao.fragmentpageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import br.com.consigaz.caminhao.fragment.VendaClienteFragment;
import br.com.consigaz.caminhao.fragment.VendaItensListFragment;

public class VendaFragmentPagerAdapter extends FragmentPagerAdapter {
	
	private final int TOTAL_PAGINAS = 2;
	private int viewId; 
	
    public VendaFragmentPagerAdapter(FragmentManager fm, int _viewId) {
        super(fm);
        this.viewId = _viewId;
    }
 
    @Override
	public Fragment getItem(int position) {
		
        switch (position) {
        
        case 0:
        	return new VendaClienteFragment(viewId);
        	
        case 1:
        	return new VendaItensListFragment();     	
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
			titleFragment = "Cliente";	
		}
		else if(position == 1){
			titleFragment = "Itens";	
		}	
		return titleFragment;
    }
}