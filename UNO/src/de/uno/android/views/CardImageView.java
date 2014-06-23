package de.uno.android.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import de.uno.card.Card;

public class CardImageView extends View {
	
	private Bitmap bitmap;
	private Card card;
	
	public CardImageView(Context context,Bitmap bitmap,Card card) {
		super(context);
		this.bitmap = bitmap;
		this.card = card;
		initBitmap();
	}
	
	public Bitmap getBitmap(){
		return this.bitmap;
	}
	
	private void initBitmap(){
		
	}
}
