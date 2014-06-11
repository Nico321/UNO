package de.uno.card;


import android.content.Context;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;

public class CardImageButton extends ImageButton{
	private Context context;
	private boolean clicked = false;
	private LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	
	public CardImageButton(Context applicationContext) {
		super(applicationContext);
		this.context = applicationContext;
		this.setLayout();
	}
	
	public boolean isClicked(){
		return this.clicked;
	}

	public void setClicked(boolean clicked){
		this.clicked = clicked;
	}
	
	private void setLayout(){
		this.alterMargin(0,0,-80,0);
		this.setPadding(0,0, 0, 0);
	}
	
	public void alterMargin(int left, int top, int right, int bottom){
		lp.setMargins(left, top, right, bottom);
		this.setLayoutParams(lp);
	}
	
}
