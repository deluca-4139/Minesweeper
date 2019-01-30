
public class Square {
	
	private int data; //Data is the number contained within the square; i.e. the number of mines that surround it. Obtained via the getNeighbors() function in the Main class.
	private boolean isMine = false;
	private boolean hasBeenChosen = false;
	private boolean isFlagged;
	
	public Square() {
		this.data = 0;
		this.isMine = false;
	}
	
	public Square(boolean b) {
		isMine = b;
	}
	
	public Square(int i) {
		this.data = i;
	}
	
	public String toString() {
		if(isMine && isFlagged && hasBeenChosen) {
			return "/";
		}
		if(isMine && hasBeenChosen) {
			return "M";
		}
		else if(hasBeenChosen && this.data==0) {
			return " ";
		}
		else if(hasBeenChosen) {
			return Integer.toString(this.data);
		}	
		else if(isFlagged) {
			return "\u2691";
		}
		else {
			return "X";
		}
	}
	
	public int getData() {
		return this.data;
	}
	
	public void setData(int i) {
		this.data = i;
	}
	
	public boolean isMine() {
		return isMine;
	}
	
	public void setMine(boolean b) {
		this.isMine = b;
	}
	
	public void setHasBeenChosen(boolean b) {
		this.hasBeenChosen = b;
	}
	
	public boolean hasBeenChosen() {
		return hasBeenChosen;
	}
	
	public boolean isFlagged() {
		return isFlagged;
	}
	
	public void setFlagged(boolean b) {
		this.isFlagged = b;
	}

}
