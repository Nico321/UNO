package de.uno.android.tasks;

import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import de.uno.android.GameActivity;

public class InitGameTask extends GetDataFromServerTask<Void, Void, Void> {
	
	private final WeakReference<ProgressDialog> progressDialogReferences;
	private final WeakReference<GetStackCardTask> stackCardTaskRef;
	private final WeakReference<GetGameStatusTask> gameStatusTaskRef;
	private final WeakReference<GetHandTask> handTaskRef;
	private final WeakReference<GetCurrentPlayerTask> nextPlayerTaskRef;

	public InitGameTask(GameActivity gameActivity,ProgressDialog mDialog) {
		super(gameActivity);
		progressDialogReferences = new WeakReference<ProgressDialog>(mDialog);
		stackCardTaskRef = new WeakReference<GetStackCardTask>(new GetStackCardTask(gameActivity));
		gameStatusTaskRef = new WeakReference<GetGameStatusTask>(new GetGameStatusTask(gameActivity));
		handTaskRef = new WeakReference<GetHandTask>(new GetHandTask(gameActivity));
		nextPlayerTaskRef = new WeakReference<GetCurrentPlayerTask>(new GetCurrentPlayerTask(gameActivity));
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();	
	}
	
	@Override
	protected Void doInBackground(Void... arg0) {
		handTaskRef.get().execute();
		nextPlayerTaskRef.get().execute();
		gameStatusTaskRef.get().execute();
		stackCardTaskRef.get().execute();
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
		gameActivity.startPollingService();
	}

}
