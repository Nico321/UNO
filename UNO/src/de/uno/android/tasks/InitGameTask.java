package de.uno.android.tasks;

import java.lang.ref.WeakReference;

import android.app.ProgressDialog;
import android.widget.ImageView;
import de.uno.android.GameActivity;

public class InitGameTask extends GetDataFromServerTask<Void, Void, Void> {
	
	private final WeakReference<ProgressDialog> progressDialogReferences;

	public InitGameTask(String Tag, GameActivity gameActivity,ProgressDialog mDialog) {
		super(gameActivity);
		progressDialogReferences = new WeakReference<ProgressDialog>(mDialog);
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialogReferences.get().setMessage("Loading Game...");
		progressDialogReferences.get().setCancelable(false);
		progressDialogReferences.get().show();
	}
	
	@Override
	protected Void doInBackground(Void... arg0) {
		GetHandTask getHandTask = new GetHandTask(this.gameActivity);
		getHandTask.execute(gameApp.getLocalPlayer());
		
		GetStackCardTask getStackCardTask = new GetStackCardTask(this.gameActivity);
		getStackCardTask.execute(gameApp.getLocalPlayer());
		
		GetPlayerStatusTask getPlayerStatusTask = new GetPlayerStatusTask(this.gameActivity);
		getPlayerStatusTask.execute(gameApp.getLocalPlayer());
		
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if (progressDialogReferences != null) {
			final ProgressDialog mDialog = progressDialogReferences.get();
			if (mDialog != null) {
				mDialog.dismiss();
			}
		}
	}

}
