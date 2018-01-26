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

public class VendaArrayAdapter extends ArrayAdapter<String> {

	private Context context;
	private List<NotaFiscal> listaDeVenda1;
	private List<NotaFiscal> listaDeVendaTemporaria;
	private Filter vendaFilter;
	private TextView TextView_nomeRazaoSocialCliente;
	private TextView textView_notaFiscal;
	
	NotaFiscal notaFiscal;

	LayoutInflater layoutInflater;

	public VendaArrayAdapter(Context activitySpinner, int textViewResourceId, List _listVenda) {
		super(activitySpinner, textViewResourceId, _listVenda);

		context = activitySpinner;
		listaDeVenda1 = _listVenda;
		listaDeVendaTemporaria = _listVenda;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	

	public View getCustomView(int position, View convertView, ViewGroup parent) {
		
		View view_Row = layoutInflater.inflate(R.layout.adapter_venda, parent, false);

		notaFiscal = null;

		notaFiscal = listaDeVenda1.get(position);

		TextView_nomeRazaoSocialCliente = (TextView) view_Row.findViewById(R.id.textView_nomeRazaoSocialCliente);
		TextView_nomeRazaoSocialCliente.setText(notaFiscal.getCliente_pedido().getNome_emit());
		
		textView_notaFiscal = (TextView) view_Row.findViewById(R.id.TextView_nrNotaFisRemessa_com_total);
		textView_notaFiscal.setText(notaFiscal.getItem_nfe().getNr_nota_fis_remessa() + "                    R$ " + notaFiscal.getTotal());
		
					 
		ImageView imageView_printer = (ImageView) view_Row.findViewById(R.id.imageView_venda);		
				  imageView_printer.setImageResource(R.drawable.ic_shopping_cart_black_24dp); //.ico_print_error
		
			
		view_Row.setId( notaFiscal.getIdInterno() );
					
		return view_Row;
	}
	
	public void resetData() {
		listaDeVenda1 = listaDeVendaTemporaria;
	}

	public int getCount() {
		return listaDeVenda1.size();	
	}
	
	public long getItemId(int position) {
		return listaDeVenda1.get(position).hashCode();
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
		if (vendaFilter == null)
			vendaFilter = new VendaFilter();

		return vendaFilter;
	}

	private class VendaFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence charSequence) {
			FilterResults results = new FilterResults();
			
			if (charSequence == null || charSequence.length() == 0) {
				
				results.values = listaDeVendaTemporaria;
				results.count = listaDeVendaTemporaria.size();
			} 
			else {	
				List<NotaFiscal> listaDeVenda2 = new ArrayList<NotaFiscal>();

				for (NotaFiscal notaFiscal : listaDeVenda1) {
					
					if (containsIgnoreCase(removerAcentos(notaFiscal.getCliente_pedido().getNome_emit()),charSequence.toString())) {
						listaDeVenda2.add(notaFiscal);
					}
					else if (containsIgnoreCase(removerAcentos(notaFiscal.getItem_nfe().getNr_nota_fis_remessa()),charSequence.toString())) {
						listaDeVenda2.add(notaFiscal);
					}
				}
				results.values = listaDeVenda2;
				results.count = listaDeVenda2.size();
			}
			return results;
		}
					
		public CharSequence removerAcentos(CharSequence str) {
		    return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
		}

		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			
				listaDeVenda1 = (List<NotaFiscal>) results.values;
				
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