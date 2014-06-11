package de.uno.android.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import de.uno.card.Card;
import de.uno.card.CardColor;
import de.uno.card.ChangeDirectionCard;
import de.uno.card.DrawCard;
import de.uno.card.NormalCard;
import de.uno.card.SkipCard;

public class CardMapper {
	private static String TAG = CardMapper.class.toString();
	private static Resources res;
	private static Context ctx;
	
	public static void init(Context context){
		res = context.getResources();
		ctx = context;
	}
	
	/*
	 * Mapped das Card Object an die dafür hinterlegte Drawable ressource und gibt die
	 * id dieser zurück um die richtige Karte auf dem Display anzeigen zu können
	 */
	public static int mapCardToResource(Card card) {
		
		Log.d(TAG, "enter mapCard with " + card.getClass());
		String resString = "";
		
		switch (card.getColor()) {
		case BLUE:
			resString = "blau_";
			break;
		case RED:
			resString = "rot_";
			break;	
		case GREEN:
			resString = "gruen_";
			break;
		case YELLOW:
			resString = "gelb_";
			break;
		case BLACK:
			if(card instanceof NormalCard) resString ="farbwahl";
			break;
		}
		
		if(card instanceof NormalCard && card.getColor() != CardColor.BLACK){
			NormalCard tmpCard = (NormalCard) card;
			resString += tmpCard.getNumber(); 
		}

		if(card instanceof ChangeDirectionCard){
			resString += "richtungswechsel";
		}
		
		if(card instanceof DrawCard){
			if(card.getColor() != CardColor.BLACK){
			resString += "plus2";
			}else{
		    resString += "plus4";	
			}
		}
		
		if (card instanceof SkipCard){
			resString += "aussetzen";
		}
		
		Log.d(TAG, "try to find drawable:" + resString);
		return res.getIdentifier(resString, "drawable", ctx.getPackageName());
	}
	
}
