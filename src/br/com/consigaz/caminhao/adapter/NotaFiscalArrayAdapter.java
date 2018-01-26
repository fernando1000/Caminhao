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
import br.com.consigaz.caminhao.modelo.NotaFiscal;

public class NotaFiscalArrayAdapter extends ArrayAdapter<String> {

	private Context context;
	private List<NotaFiscal> listaDeNotaFiscal1;
	private List<NotaFiscal> listaDeNotaFiscalTemporaria;
	private ImageView imageView_produto;
	private TextView TextView_itCodigo_com_qtd_com_total;
	private TextView textView_descItem;
	private NotaFiscal notaFiscal;
	private Filter notaFiscalFilter;
	private LayoutInflater layoutInflater;

	public NotaFiscalArrayAdapter(Context activitySpinner, int textViewResourceId, List _listNotaFiscal) {
		super(activitySpinner, textViewResourceId, _listNotaFiscal);

		context = activitySpinner;
		listaDeNotaFiscal1 = _listNotaFiscal;
		listaDeNotaFiscalTemporaria = _listNotaFiscal;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public View getCustomView(int position, View convertview, ViewGroup parent) {
		
		View view = layoutInflater.inflate(R.layout.adapter_notafiscal, parent, false);

		notaFiscal = null;

		notaFiscal = listaDeNotaFiscal1.get(position);

		imageView_produto = (ImageView) view.findViewById(R.id.imageView_produto2);		
		imageView_produto.setImageResource(R.drawable.ic_palette_black_24dp);
		
		TextView_itCodigo_com_qtd_com_total = (TextView) view.findViewById(R.id.textView_itCodigo_com_qtd_com_total);
		TextView_itCodigo_com_qtd_com_total.setText(""+ notaFiscal.getItem_nfe().getIt_codigo() + " " + notaFiscal.getItem_nfe().getQuantidade() + " " + notaFiscal.getTotal());

		textView_descItem = (TextView) view.findViewById(R.id.textView_descItem2);
		textView_descItem.setText(notaFiscal.getProduto().getDesc_item());
				 										 
			
		view.setId(notaFiscal.getIdInterno());
					
		return view;
	}
	
	public void resetData() {
		listaDeNotaFiscal1 = listaDeNotaFiscalTemporaria;
	}

	public int getCount() {
		return listaDeNotaFiscal1.size();	
	}
	
	public long getItemId(int position) {
		return listaDeNotaFiscal1.get(position).hashCode();
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
		if (notaFiscalFilter == null)
			notaFiscalFilter = new NotaFiscalFilter();

		return notaFiscalFilter;
	}

	private class NotaFiscalFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence charSequence) {
			FilterResults results = new FilterResults();
			
			if (charSequence == null || charSequence.length() == 0) {
				
				results.values = listaDeNotaFiscalTemporaria;
				results.count = listaDeNotaFiscalTemporaria.size();
			} 
			else {	
				List<NotaFiscal> listaDeNotaFiscal2 = new ArrayList<NotaFiscal>();

				for (NotaFiscal notaFiscal : listaDeNotaFiscal1) {
					
					if (containsIgnoreCase(removerAcentos(notaFiscal.getProduto().getIt_codigo()),charSequence.toString())) {
						listaDeNotaFiscal2.add(notaFiscal);
					}
					else if (containsIgnoreCase(removerAcentos(notaFiscal.getProduto().getDesc_item()),charSequence.toString())) {
						listaDeNotaFiscal2.add(notaFiscal);
					}
				}
				results.values = listaDeNotaFiscal2;
				results.count = listaDeNotaFiscal2.size();
			}
			return results;
		}
					
		public CharSequence removerAcentos(CharSequence str) {
		    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			
				listaDeNotaFiscal1 = (List<NotaFiscal>) results.values;
				
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