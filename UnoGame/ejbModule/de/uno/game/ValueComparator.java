package de.uno.game;

import java.util.Comparator;
import java.util.HashMap;

import de.uno.player.Player;

class ValueComparator implements Comparator<Integer> {
	
	HashMap<Integer, Player> base;
    
	public ValueComparator(HashMap<Integer, Player> players) {
		base = players;
	}



	@Override
	public int compare(Integer o1, Integer o2) {
		if (base.get(o1).getHand().getCards().size() >= base.get(o2).getHand().getCards().size()) {
            return 1;
        } else {
            return -1;
        }
	}
}
