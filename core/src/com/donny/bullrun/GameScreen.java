package com.donny.bullrun;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen {
	private Board b;
	
	public GameScreen(){
		b=new Board(11,8);
		
	}
	public void update(){
		b.update();
	}
	public void render(SpriteBatch sb){
		b.render(sb);
		//b.drawDebug();
	}
	
}
