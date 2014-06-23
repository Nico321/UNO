package de.uno.android.views;


import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout.LayoutParams;
import de.uno.Hand.Hand;
import de.uno.android.GameActivity;
import de.uno.android.GameApplication;
import de.uno.android.tasks.PutCardTask;
import de.uno.card.Card;

public class CardImageButton extends ImageButton{

	protected GameApplication gameApp;
	protected GameActivity gameActivity;
	private boolean clicked = false;
	private LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	private Card card;
	
	public CardImageButton(Context applicationContext,final GameActivity gameActivity,final GameApplication gameApp, final Card card) {
		super(applicationContext);
		this.setLayout();
		this.card = card;
		this.gameApp = gameApp;
		this.gameActivity = gameActivity;
		
		this.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//Karte die geklickt wurde
				CardImageButton tmpV = (CardImageButton) arg0;
				Log.d(tmpV.getClass().getName(), "image button clicked!!" + " isClicked:" + tmpV.isClicked());
				if(!tmpV.isClicked()){
					tmpV.setClicked(true);
					//Grafisches Verschieben der angeklickten Karten
					if(gameActivity.isOuterRightCard(tmpV)){
						tmpV.alterMargin(0,0,0,25);
					}else{
						tmpV.alterMargin(0,0,-80,25);
					}
				}else{
					//Aufruf der Methode zum legen der Karte in der GameActivity
					if(gameApp.isCardValid(card)){
						gameActivity.putCard(card,tmpV);
					}
					
					tmpV.setClicked(false);
					if(gameActivity.isOuterRightCard(tmpV)){
						tmpV.alterMargin(0,0,0,0);
					}else{
						tmpV.alterMargin(0,0,-80,0);
						}
					}
			}
			
		});
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
	
	public Card getCardObject(){
		return this.card;
	}
	
}
