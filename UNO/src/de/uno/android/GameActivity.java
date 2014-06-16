package de.uno.android;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import de.android.uno.R;
import de.uno.Hand.Hand;
import de.uno.android.common.GameStub;
import de.uno.android.tasks.BitmapDecodeTask;
import de.uno.android.tasks.DrawCardTask;
import de.uno.android.tasks.InitGameTask;
import de.uno.android.tasks.PutCardTask;
import de.uno.android.util.CardMapper;
import de.uno.card.Card;
import de.uno.card.CardImageButton;
import de.uno.player.Player;

public class GameActivity extends Activity implements OnClickListener{
	private static final String TAG =  GameActivity.class.getName();
	private static GameApplication gameApp;
	private static LinearLayout cardScrollViewLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_game);
		cardScrollViewLayout = (LinearLayout) this.findViewById(R.id.cardScollViewLayout);
		CardMapper.init(this);
		
		this. gameApp = (GameApplication) this.getApplication();
		gameApp.setGameStub(new GameStub());
		
		Player dave = new Player("Dave");
		
		gameApp.setLocalPlayer(dave);
		gameApp.setActualPlayer(dave);
		
		initGame();
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	//TODO! view sperren wenn eine Karte angeklickt wurde damit ein klicken mehrerer Karten nicht möglich
	//ist!
	@Override
	public void onClick(View v) {
		//wird aufgerufen sobald der Spieler eine seiner Karten anklickt
		if(v instanceof CardImageButton){
			CardImageButton tmpV = (CardImageButton) v;
			Log.d(tmpV.getClass().getName(), "image button clicked!!" + " isClicked:" + tmpV.isClicked());
			if(!tmpV.isClicked()){
				tmpV.setClicked(true);
				
				//Grafisches Verschieben der angeklickten Karten
				if(isOuterRightCard(tmpV)){
					tmpV.alterMargin(0,0,0,25);
				}else{
					tmpV.alterMargin(0,0,-80,25);
				}
			}else{
				int cardPosition = (int)v.getTag();
				Hand hand = gameApp.getLocalPlayerHand();
				Card card = gameApp.getLocalPlayerHand().getCards().get(cardPosition);
				
				if(gameApp.isCardValid(card)){
					PutCardTask putCardTask = new PutCardTask(this);
					putCardTask.execute(gameApp.getLocalPlayer(),card,Integer.valueOf(cardPosition),v);
				}else{
					tmpV.setClicked(false);
					if(isOuterRightCard(tmpV)){
						tmpV.alterMargin(0,0,0,0);
					}else{
						tmpV.alterMargin(0,0,-80,0);
					}
				}
				
			}
		}
		
	}
	
	//öffnet einen Asynchronentask, skaliert die Bitmap
	public void loadBitmap(int resId,Context context,ImageView imageView,int reqWidth,int reqHight) {
	    BitmapDecodeTask task = new BitmapDecodeTask(imageView,context);
	    task.execute(resId,reqWidth,reqHight);
	}

	private int convertDpInPixel(int dp) {
		Resources r = getResources();
		int px = (int) Math.round(TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
		return px;
	}
	
	//prüft ob es sich um die ganz rechte karte  der Hand des Spielers handelt
	private boolean isOuterRightCard(CardImageButton cardImageButton){
		LinearLayout linlay = (LinearLayout)findViewById(R.id.cardScollViewLayout);
		CardImageButton imgb = (CardImageButton) linlay.getChildAt(linlay.getChildCount()-1);
		
		if(cardImageButton == imgb){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Methode die aufgerufen wird sobald der User den KartenStapel anclicked
	 */
	public void drawCard(View view){
		//überprüfe ob lokaler Spieler überhaupt spielberechtigt ist
		if(gameApp.getActualPlayer().equals(gameApp.getLocalPlayer())){
			DrawCardTask drawCardTask =  new DrawCardTask(this);
			drawCardTask.execute(gameApp.getLocalPlayer());
		}
		
	}
	
	//TODO Check Connectivity
	private void initGame(){
		ProgressDialog mDialog = new ProgressDialog(this);
		InitGameTask initGameTask = new InitGameTask(this, mDialog);
		initGameTask.execute();
	}
	
	private void putCard(Card card){
		if(gameApp.isCardValid(card) && gameApp.getLocalPlayer().equals(gameApp.getActualPlayer())){
			PutCardTask putCardTask = new PutCardTask(this);
			putCardTask.execute(gameApp.getLocalPlayer(),card);
		}
	}
	

	
}
