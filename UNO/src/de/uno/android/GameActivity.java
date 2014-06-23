package de.uno.android;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import de.android.uno.R;
import de.uno.android.common.GameStub;
import de.uno.android.tasks.BitmapDecodeTask;
import de.uno.android.tasks.CallUnoTask;
import de.uno.android.tasks.DrawCardTask;
import de.uno.android.tasks.GetGameProgressTask;
import de.uno.android.tasks.GetGameStatusTask;
import de.uno.android.tasks.GetCurrentPlayerTask;
import de.uno.android.tasks.GetStackCardTask;
import de.uno.android.tasks.GetWishedColorTask;
import de.uno.android.tasks.InitGameTask;
import de.uno.android.tasks.PutCardTask;
import de.uno.android.tasks.SetWishedColorTask;
import de.uno.android.util.CardMapper;
import de.uno.android.views.CardImageButton;
import de.uno.card.Card;
import de.uno.card.CardColor;
import de.uno.player.Player;

public class GameActivity extends Activity{
	private static final String TAG =  GameActivity.class.getName();
	private static GameApplication gameApp;
	private static LinearLayout cardScrollViewLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_game);
		cardScrollViewLayout = (LinearLayout) this.findViewById(R.id.cardScollViewLayout);
		CardMapper.init(this);
		
		GameActivity.gameApp = (GameApplication) this.getApplication();
		gameApp.setGameStub(new GameStub());
		
		gameApp.setGameProgress(0);
		ImageButton unoButton = (ImageButton) findViewById(R.id.unoButton);
		//TODO set back
		unoButton.setClickable(false);

		Player carsten = new Player("Carsten");
		Player nico = new Player("Nico");
		Player dave = new Player("Dave");
		
		gameApp.setLocalPlayer(dave);
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
	
	//öffnet einen Asynchronentask, skaliert die Bitmap
	public void loadBitmap(int resId,Context context,ImageView imageView,int reqWidth,int reqHight) {
	    BitmapDecodeTask task = new BitmapDecodeTask(imageView,context);
	    task.execute(resId,reqWidth,reqHight);
	}

	
	//prüft ob es sich um die ganz rechte karte  der Hand des Spielers handelt
	public boolean isOuterRightCard(CardImageButton cardImageButton){
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
			drawCardTask.execute(1);
		}
		
	}
	
	//TODO Check Connectivity
	private void initGame(){
		ProgressDialog mDialog = new ProgressDialog(this);
		mDialog.setMessage("Lade Spiel....");
		mDialog.setCancelable(false);
		mDialog.show();
		InitGameTask initGameTask = new InitGameTask(this, mDialog);
		initGameTask.execute();
	}
	
	public void putCard(Card card, CardImageButton cib){
		if(gameApp.isCardValid(card) && gameApp.getLocalPlayer().equals(gameApp.getActualPlayer())){
			if(card.getColor() == CardColor.BLACK){
				this.generateChoseColorDialog(card,cib).show();
			}else {
				PutCardTask putCardTask = new PutCardTask(this);
				putCardTask.execute(gameApp.getLocalPlayer(),card,cib);
			}
		}
	}
	
	private Dialog generateChoseColorDialog(final Card card, final CardImageButton cib){
		final String[] colors = new String[]{"Gelb","Blau","Grün","Rot"};
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.choose_color_item,colors);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Wähle eine Farbe");
		builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				CardColor wishedColor = null;
				if(which == 0){
					wishedColor = CardColor.YELLOW;
				}else if(which == 1){
					wishedColor = CardColor.BLUE;
				}else if(which == 2){
					wishedColor = CardColor.GREEN;
				}else if(which == 3){
					wishedColor = CardColor.RED;
				}
				gameApp.setWishedColor(wishedColor);
				SetWishedColorTask setWishedColor = new SetWishedColorTask(GameActivity.this);
				setWishedColor.execute();
				
				PutCardTask putCardTask = new PutCardTask(GameActivity.this);
				putCardTask.execute(gameApp.getLocalPlayer(),card,cib);
				
				GetWishedColorTask getWishedColor= new GetWishedColorTask(GameActivity.this);
				getWishedColor.execute();
			}
		});
		final AlertDialog dialog = builder.create();
		dialog.setCancelable(false);
		return dialog;
	}
	
	/**
	 * Pollingservice der gestartet wird sobald der lokale Spieler nicht mehr
	 * an der Reihe ist
	 */
	public void startPollingService(){
		final Handler handler = new Handler();
	    final Timer timer = new Timer();
	    TimerTask pollingTask = new TimerTask() {       
	        @Override
	        public void run() {
	            handler.post(new Runnable() {
	                public void run() {
	                    try {
	                    	if(!gameApp.isGameFinished()){
		                    	GetGameProgressTask getGameProgress = new GetGameProgressTask(GameActivity.this);
		                		getGameProgress.execute();
	                    	}else{
	                    		timer.cancel();
	                    	}
	                    	} catch (Exception e) {
	                    }
	                }
	            });
	        }
	    };
	    timer.schedule(pollingTask, 0, 1000); 
	}
	
	public void callUno(View view){
		if(gameApp.getLocalPlayerHand().getCards().size() == 1){
			CallUnoTask callUno = new CallUnoTask(this);
			callUno.execute();
		}
	}
	
}
