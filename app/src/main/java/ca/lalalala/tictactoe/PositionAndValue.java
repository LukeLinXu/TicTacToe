package ca.lalalala.tictactoe;

public class PositionAndValue {
	private int x;
	private int y; 
//	value is used to show which move is the best
	private int value;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public PositionAndValue(int x, int y, int value) {
		super();
		this.x = x;
		this.y = y;
		this.value = value;
	}
	public PositionAndValue() {
		// TODO Auto-generated constructor stub
	}
}
