package ca.lalalala.tictactoe;

public class CheckIsDone {
	private boolean isdone;
//	The TextCode is used to identify the result(Who wins or ties)
	private int TextCode;
	public boolean getisIsdone() {
		return isdone;
	}
	public void setIsdone(boolean isdone) {
		this.isdone = isdone;
	}
	public String getText() {
		switch (TextCode) {
		case MainActivity.HUMAN:
			return "You Win!";
		case MainActivity.COMPUTER:
			return "You Lose!";
		default:
			return "We Tied!";
		}
	}
	public int getTextCode() {
		return TextCode;
	}
	
	public void setTextCode(int text) {
		TextCode = text;
	}
	public CheckIsDone(boolean isdone, int text) {
		super();
		this.isdone = isdone;
		TextCode = text;
	}
	
	public CheckIsDone() {
		// TODO Auto-generated constructor stub
	}
}
