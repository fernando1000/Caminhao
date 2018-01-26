package br.com.consigaz.caminhao.util;

import java.lang.reflect.Field;
import android.content.Context;
import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import br.com.consigaz.caminhao.R;

public class TelaBuilder {

	private Context context;
	private LayoutParams layoutParams_MATCH_MATCH;
	private LayoutParams layoutParams_WRAP_WRAP;
	private LayoutParams layoutParams_MATCH_WRAP;
		
	public TelaBuilder(Context _context) {
		this.context = _context;
		layoutParams_MATCH_MATCH = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		layoutParams_WRAP_WRAP = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams_MATCH_WRAP = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);		
	}

	public LinearLayout criaPopulaEAddWidgetsEmLinearLayout(LinearLayout linearLayout, Object objeto) {

		Class classe = objeto.getClass();

		for (Field field : classe.getDeclaredFields()) {

			try {
				field.setAccessible(true);

				if (!field.getName().startsWith("COLUMN")) {

					linearLayout.addView(criaTextView(field.getName()));
					linearLayout.addView(criaEditText(String.valueOf(field.get(objeto))));
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return linearLayout;
	}

	public ScrollView criaScrollView() {

		ScrollView scrollView = new ScrollView(context);
		scrollView.setLayoutParams(layoutParams_MATCH_MATCH);
		return scrollView;
	}

	public LinearLayout criaLinearLayout() {

		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setLayoutParams(layoutParams_MATCH_MATCH);
		linearLayout.setBackgroundColor(context.getResources().getColor(R.color.plano_de_fundo_layout));
		return linearLayout;
	}

	public LinearLayout criaLinearLayoutHOLDER(float peso) {

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, peso);

		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setLayoutParams(params);
		
		//linearLayout.setGravity(Gravity.CENTER|Gravity.BOTTOM);

		return linearLayout;
	}

	public TextView criaTextView(String nome) {

		TextView textView = new TextView(context);
		textView.setText(Html.fromHtml("<b>" + nome + ": " + "</b>"));
		textView.setTextSize(20);
		textView.setTextColor(context.getResources().getColor(R.color.azul_consigaz));
		textView.setLayoutParams(layoutParams_WRAP_WRAP);
		return textView;
	}

	public EditText criaEditText(String conteudo) {

		EditText editText = new EditText(context);
		editText.setLayoutParams(layoutParams_MATCH_WRAP);
		editText.setEnabled(false);
		editText.setFocusable(false);
		editText.setText(conteudo);
		return editText;
	}

	public EditText criaEditTextSOH_NUMEROS(int qtdMaximaCaracter) {

		EditText editText = new EditText(context);
				 editText.setLayoutParams(layoutParams_MATCH_WRAP);
				 editText.setInputType(InputType.TYPE_CLASS_NUMBER);
				 editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(qtdMaximaCaracter) });
					
		return editText;
	}

	public ListView criaListView() {

		ListView listView = new ListView(context);
		listView.setLayoutParams(layoutParams_WRAP_WRAP);
		listView.setBackgroundColor(context.getResources().getColor(R.color.plano_de_fundo_lista));
		listView.setDivider(context.getResources().getDrawable(R.color.divisoria));
		listView.setDividerHeight(10);
		listView.setScrollingCacheEnabled(false);
		 
		return listView;
	}

	public ImageView criaImageView(String itCodigo) {

		ImageView imageView = new ImageView(context);
				  imageView.setLayoutParams(layoutParams_MATCH_MATCH);

		if(itCodigo.contains("13")){
			
			imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.p13));
		}
		else if(itCodigo.contains("20")){
			
			imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.p20));
		}
		else if(itCodigo.contains("45")){
		
			imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.p45));
		}
		else if(itCodigo.contains("consigaz")){
			
			imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.consigaz_logo));
		}
		else{			
			imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_launcher));
		}

		return imageView;
	}

	public Button criaButton(String conteudo) {

		Button button = new Button(context);
			   button.setLayoutParams(layoutParams_MATCH_WRAP);
			   button.setText(conteudo);
		return button;
	}

	public Button criaButtonParaListaDeVendas(String conteudo) {

		Button button = new Button(context);
			   button.setLayoutParams(layoutParams_MATCH_WRAP);
			   button.setText(conteudo);
			   //button.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER);
			   button.setTextSize(30);
			   button.setTextColor(context.getResources().getColor(R.color.branco));
			   button.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.style_btn_consigaz));
			    	        	  
		return button;
	}
	
	
	
}
