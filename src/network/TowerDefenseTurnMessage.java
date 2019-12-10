package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TowerDefenseTurnMessage implements Serializable{
	private List<TowerDefenseMoveMessage> moves = new ArrayList<TowerDefenseMoveMessage>();
	private ObjectOutputStream out;
	
	public TowerDefenseTurnMessage(ObjectOutputStream out){
		this.out = out;
	}
	
	public List<TowerDefenseMoveMessage> getMoves(){
		return moves;
	}
	
	public void addMove(TowerDefenseMoveMessage move) {
		try {
			out.writeObject(move);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
