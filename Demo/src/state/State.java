package state;

import java.awt.Graphics;

import tilegame.Handler;

public abstract class State {
	protected boolean isNewGame = false;
	private static State currentState = null;
	// CLASS
	protected Handler handler; //set protected để có thể đc extends, sử dụng trong lớp con.
	// khởi tạo 
	public State(Handler handler) {
		this.handler = handler;
	}
	// tick + render
	public abstract void tick();

	public abstract void render(Graphics g);

	// get, set
	public static void setState(State state) {
		currentState = state;
	}

	public static State getState() {
		return currentState;
	}
}
