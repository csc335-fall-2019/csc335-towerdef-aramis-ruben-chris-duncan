package network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TowerDefenseTurnMessage implements Serializable{
	private List<TowerDefenseMoveMessage> moves = new ArrayList<TowerDefenseMoveMessage>();
	
	public List<TowerDefenseMoveMessage> getMoves(){
		return moves;
	}
	
	public void addMove(TowerDefenseMoveMessage move) {
		moves.add(move);
	}
}
