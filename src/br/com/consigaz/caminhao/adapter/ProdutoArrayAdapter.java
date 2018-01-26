package br.com.consigaz.caminhao.adapter;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.consigaz.caminhao.R;
import br.com.consigaz.caminhao_sharedlib.modelo.Produto;

public class ProdutoArrayAdapter extends ArrayAdapter<String> {

	private Context context;
	private List<Produto> listaDeProduto1;
	private List<Produto> listaDeProdutoTemporaria;
	private ImageView imageView_produto;
	private TextView textView_descItem;
	private TextView TextView_itCodigo_com_uni;
	private LayoutInflater layoutInflater;
	private Filter produtoFilter;
	private Produto produto;

	public ProdutoArrayAdapter(Context activitySpinner, int textViewResourceId, List _listProduto) {
		super(activitySpinner, textViewResourceId, _listProduto);

		context = activitySpinner;
		listaDeProduto1 = _listProduto;
		listaDeProdutoTemporaria = _listProduto;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public View getCustomView(int position, View convertView, ViewGroup parent) {
		
		View view_Row = layoutInflater.inflate(R.layout.adapter_produto, parent, false);

		produto = null;
	
		produto = listaDeProduto1.get(position);

		imageView_produto = (ImageView) view_Row.findViewById(R.id.imageView_produto);		
		imageView_produto.setImageResource(R.drawable.ic_palette_black_24dp);
		
		textView_descItem = (TextView) view_Row.findViewById(R.id.textView_descItem);
		textView_descItem.setText(produto.getDesc_item());
				 										 
		TextView_itCodigo_com_uni = (TextView) view_Row.findViewById(R.id.textView_itCodigo_com_uni);
		TextView_itCodigo_com_uni.setText(produto.getIt_codigo() + " " + produto.getUni());
			
		view_Row.setId(position);
					
		return view_Row;
	}
	
	public void resetData() {
		listaDeProduto1 = listaDeProdutoTemporaria;
	}

	public int getCount() {
		return listaDeProduto1.size();	
	}
	
	public long getItemId(int position) {
		return listaDeProduto1.get(position).hashCode();
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		super.registerDataSetObserver(observer);
	}
	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		super.unregisterDataSetObserver(observer);
	}
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent);
	}
	@Override
	public Filter getFilter() {
		if (produtoFilter == null)
			produtoFilter = new ProdutoFilter();

		return produtoFilter;
	}

	private class ProdutoFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence charSequence) {
			FilterResults results = new FilterResults();
			
			if (charSequence == null || charSequence.length() == 0) {
				
				results.values = listaDeProdutoTemporaria;
				results.count = listaDeProdutoTemporaria.size();
			} 
			else {	
				List<Produto> listaDeProduto2 = new ArrayList<Produto>();

				for (Produto produto : listaDeProduto1) {
					
					if (containsIgnoreCase(removerAcentos(produto.getIt_codigo()),charSequence.toString())) {
						listaDeProduto2.add(produto);
					}
					else if (containsIgnoreCase(removerAcentos(produto.getDesc_item()),charSequence.toString())) {
						listaDeProduto2.add(produto);
					}
				}
				results.values = listaDeProduto2;
				results.count = listaDeProduto2.size();
			}
			return results;
		}
					
		public CharSequence removerAcentos(CharSequence str) {
		    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			
				listaDeProduto1 = (List<Produto>) results.values;
				
				notifyDataSetChanged();
		}
		
		public boolean containsIgnoreCase(CharSequence haystack, String needle) {
			if (needle.equals(""))
				return true;
			if (haystack == null || needle == null || haystack.equals(""))
				return false;

			Pattern p = Pattern.compile(needle, Pattern.CASE_INSENSITIVE
					+ Pattern.LITERAL);
			Matcher m = p.matcher(haystack);
			return m.find();
		}
	}
}