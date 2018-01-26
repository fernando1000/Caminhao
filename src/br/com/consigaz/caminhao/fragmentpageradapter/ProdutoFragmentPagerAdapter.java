package br.com.consigaz.caminhao.fragmentpageradapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import br.com.consigaz.caminhao.fragment.ProdutoImagemFragment;
import br.com.consigaz.caminhao.fragment.ProdutoImpostosFragment;
import br.com.consigaz.caminhao.fragment.ProdutoDetalheFragment;
import br.com.consigaz.caminhao_sharedlib.modelo.Produto;

public class ProdutoFragmentPagerAdapter extends FragmentPagerAdapter {
	
	final int TOTAL_PAGINAS = 3;
	private Produto produto; 

    public ProdutoFragmentPagerAdapter(FragmentManager fm, Produto _produto) {
        super(fm);
        this.produto = _produto;
    }
 
    @Override
	public Fragment getItem(int position) {
		
        switch (position) {
        
        case 0:
        	return new ProdutoDetalheFragment(produto);
        	
        case 1:
        	return new ProdutoImpostosFragment();
        	
        case 2:
        	return new ProdutoImagemFragment(produto.getIt_codigo());
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
			titleFragment = "Produto";	
		}
		else if(position == 1){
			titleFragment = "Impostos";	
		}
		else if(position == 2){
			titleFragment = "Imagem";	
		}
		return titleFragment;
    } 
}