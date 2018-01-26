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

public class ClienteArrayAdapter extends ArrayAdapter<String> {

	private Context context;
	private List<NotaFiscal> listaDeNotaFiscal;
	private List<NotaFiscal> listaDeNotaFiscalTemporaria;
	private NotaFiscal notaFiscal;
	LayoutInflater layoutInflater;
	//Pedido pedido;
	//Cliente_pedido cliente_pedido;
	//Local_entrega_pedido local_entrega_pedido;
	private ImageView imageView_cliente;
	private TextView textView_pedidoSolic;
	private TextView textView_codEmitente_com_nomeEmit;
	private TextView textView_endereco_com_bairro;
	private Filter clienteFilter;

	public ClienteArrayAdapter(Context _context, int textViewResourceId, List _listNotaFiscal) {
		super(_context, textViewResourceId, _listNotaFiscal);

		context = _context;
		listaDeNotaFiscal = _listNotaFiscal;
		listaDeNotaFiscalTemporaria = _listNotaFiscal;
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	

	public View getCustomView(int position, View convertView, ViewGroup parent) {
		
		View view_Row = layoutInflater.inflate(R.layout.adapter_cliente, parent, false);

		imageView_cliente = (ImageView) view_Row.findViewById(R.id.imageView_cliente);		
		imageView_cliente.setImageResource(R.drawable.ic_person_black_24dp);
		textView_pedidoSolic = (TextView) view_Row.findViewById(R.id.textView_pedidoSolic);
		textView_codEmitente_com_nomeEmit = (TextView) view_Row.findViewById(R.id.textView_codEmitente_com_nomeEmit);
		textView_endereco_com_bairro = (TextView) view_Row.findViewById(R.id.textView_endereco_com_bairro);

		notaFiscal = null;

		notaFiscal = listaDeNotaFiscal.get(position);

		
		/*
		Dao dao = new Dao(context);
		
		pedido = (Pedido) dao.devolveObjeto(Pedido.class, Pedido.COLUMN_INTEGER_COD_EMITENTE, cliente_pedido.getCod_emitente());
		
		local_entrega_pedido = (Local_entrega_pedido) dao.devolveObjeto(Local_entrega_pedido.class, 
																		Local_entrega_pedido.COLUMN_TEXT_COD_EMITENTE, cliente_pedido.getCod_emitente());
		
		*/
		
		if(notaFiscal.getPedido() == null){
					
			textView_pedidoSolic.setText("nao achou pedido");
		}else{			
			textView_pedidoSolic.setText(notaFiscal.getPedido().getPedido_solic());
		}
				 										 
		textView_codEmitente_com_nomeEmit.setText(notaFiscal.getCliente_pedido().getCod_emitente() + " - " + notaFiscal.getCliente_pedido().getNome_emit());
		
		if(notaFiscal.getLocal_entrega_pedido() == null){
					
			textView_endereco_com_bairro.setText("nao achou local entrega");
		}else{			
			textView_endereco_com_bairro.setText(notaFiscal.getLocal_entrega_pedido().getEndereco());
		}
		
		view_Row.setId(position);
					
		return view_Row;
	}
	
	public void resetData() {
		listaDeNotaFiscal = listaDeNotaFiscalTemporaria;
	}

	public int getCount() {
		return listaDeNotaFiscal.size();	
	}
	
	public long getItemId(int position) {
		return listaDeNotaFiscal.get(position).hashCode();
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
		if (clienteFilter == null)
			clienteFilter = new ClienteFilter();

		return clienteFilter;
	}

	private class ClienteFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence charSequence) {
			FilterResults results = new FilterResults();
			
			if (charSequence == null || charSequence.length() == 0) {
				
				results.values = listaDeNotaFiscalTemporaria;
				results.count = listaDeNotaFiscalTemporaria.size();
			} 
			else {	
				List<NotaFiscal> listaDeNotaFiscal2 = new ArrayList<NotaFiscal>();

				for (NotaFiscal notaFiscal : listaDeNotaFiscal) {
					
					if (containsIgnoreCase(removerAcentos(notaFiscal.getCliente_pedido().getNome_emit()),charSequence.toString())) {
						listaDeNotaFiscal2.add(notaFiscal);
					}
					else if (containsIgnoreCase(removerAcentos(""+notaFiscal.getCliente_pedido().getCod_emitente()),charSequence.toString())) {
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
			
			listaDeNotaFiscal = (List<NotaFiscal>) results.values;
				
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