package br.com.consigaz.caminhao.util;

import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import br.com.consigaz.caminhao.R;

public class Animacao {

	/*
	 * public void startAnimations(Context context, LinearLayout linearLayout) {
	 * 
	 * Animation animation = AnimationUtils.loadAnimation(context,
	 * R.anim.translate); animation.reset();
	 * 
	 * linearLayout.clearAnimation(); linearLayout.startAnimation(animation); }
	 */

	public void startAnimations(Context context, LinearLayout linearLayout, ImageView imageView) {

		Animation animation = AnimationUtils.loadAnimation(context, R.anim.alpha);
		animation.reset();

		// LinearLayout linearLayout=(LinearLayout) findViewById(R.id.splash);

		linearLayout.clearAnimation();
		linearLayout.startAnimation(animation);

		animation = AnimationUtils.loadAnimation(context, R.anim.translate);
		animation.reset();

		// ImageView imageView = (ImageView) findViewById(R.id.logo);
		imageView.clearAnimation();
		imageView.startAnimation(animation);
	}

}
