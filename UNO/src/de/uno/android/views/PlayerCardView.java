package de.uno.android.views;

import java.util.ArrayList;

import de.android.uno.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class PlayerCardView extends View {
	private static final String TAG = PlayerCardView.class.toString();
	
	private int amountCards;
	private Matrix matrix;
	private Paint paint;
	private Bitmap bitmap;
	
	public PlayerCardView(Context context) {
		super(context);
		this.matrix = new Matrix();
		this.paint = new Paint();
		this.amountCards = 0;	
	}
	
	public PlayerCardView(Context context, AttributeSet attrs){
        super(context, attrs);
    }
    public PlayerCardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
	
	@SuppressLint("DrawAllocation")
	@Override
	public void onDraw(Canvas c){
		this.matrix = new Matrix();
		int distance = 0;
		this.bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.rueckseite);
		this.bitmap = Bitmap.createScaledBitmap(bitmap,60 ,90 ,true);
		this.matrix.postRotate(getRot());
		this.bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		
		for (int i= 0; i < amountCards; i++) {
			if(this.getContext().getResources().getResourceEntryName(this.getId()).toString().equals("top")){
				this.matrix.setTranslate(c.getWidth()/3 + distance,10);
				c.drawBitmap(bitmap, this.matrix, this.paint);
			}else if(this.getContext().getResources().getResourceEntryName(this.getId()).toString().equals("left")) {
				this.matrix.setTranslate(c.getWidth() - bitmap.getWidth(), c.getHeight()/3 + distance);
				c.drawBitmap(bitmap, this.matrix, this.paint);
			}else if(this.getContext().getResources().getResourceEntryName(this.getId()).toString().equals("right")){
				this.matrix.setTranslate(c.getWidth() - bitmap.getWidth(), c.getHeight()/3 + distance);
				c.drawBitmap(bitmap, this.matrix, this.paint);
			}
			distance+=(20 - amountCards);
			if(distance < 10)distance = 10;
		}
	}

	public void setAmountCards(int amountCards){
		this.amountCards = amountCards;
		this.invalidate();
	}
	
	
	private int getRot(){
		switch (getResources().getResourceEntryName(this.getId()).toString()) {
		case "left":
			return 90;
		case "top":
			return 180;
		case "right":
			return -90;
		default:
			break;
		}
		return 0;
	}

}
