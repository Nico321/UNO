package de.uno.android.tasks;

import de.uno.android.GameActivity;
import de.uno.player.Player;

public class GetNextPlayerTask extends GetDataFromServerTask<Player, Void, Player> {

	public GetNextPlayerTask(String Tag, GameActivity gameActivity) {
		super(gameActivity);
	}

	@Override
	protected Player doInBackground(Player... arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
