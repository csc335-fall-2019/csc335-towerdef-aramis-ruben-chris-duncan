package game;

import java.util.ArrayList;
import java.util.List;

public class TowerDefenseTurnMessage {
	private List<TowerDefenseMoveMessage> moves = new ArrayList<TowerDefenseMoveMessage>();
	
	public List<TowerDefenseMoveMessage> getMoves(){
		return moves;
	}
	
	public void addMove(TowerDefenseMoveMessage move) {
		moves.add(move);
	}
}
