package com.donny.bullrun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.donny.bullrun.camera.OrthoCamera;

public class Player {
	private int x,y;
	boolean turnOver;
	ShapeRenderer shape;
	Dice dice;
	
	public Player(int x, int y){
		this.x=x;
		this.y=y;
		turnOver=true;
		shape=new ShapeRenderer();
		dice=new Dice();
	}
	public void update(){
		dice.update();
		
		//checks that the player has pressed to start the roll
		if(!turnOver){
			if(Gdx.input.isKeyJustPressed(Keys.SPACE)||Gdx.input.justTouched()){
				dice.roll();
			
			}
			
		}
		//checks to make sure the dice finished rolling and player confirms
		if(!dice.isRolling()&&(Gdx.input.isKeyJustPressed(Keys.SPACE)||Gdx.input.justTouched())){
			System.out.println("TURN OVER");
			turnOver=true;
			System.out.println(dice.getRollResult());
			dice.reset();
		}
		
	}
	public void render(SpriteBatch sb,OrthoCamera camera){
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		dice.render(sb);
		sb.end();
		shape.setProjectionMatrix(camera.combined);
		shape.begin(ShapeType.Filled);
		shape.circle(1080, 155, 20);
		shape.end();
	}
	public boolean isTurnOver(){
		return turnOver;
	}
	public int rollResults(){
		return dice.getRollResult();
	}
	

}
