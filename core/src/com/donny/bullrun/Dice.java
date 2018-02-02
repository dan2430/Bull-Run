package com.donny.bullrun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Dice {
	boolean roll;
	TextureRegion[] animFrame;
	Animation<TextureRegion> animation;
	float elapsedTime;
	Texture dice=new Texture("dice.png");
	int turn;
	int die1,die2;

	Vector2 pos=new Vector2();
	public Dice(){
		roll=false;
		TextureRegion[][] tmpFrames = TextureRegion.split(dice,64,64);
		animFrame=new TextureRegion[6];
		animFrame[0]=tmpFrames[0][0];
		animFrame[1]=tmpFrames[0][1];
		animFrame[2]=tmpFrames[0][2];
		animFrame[3]=tmpFrames[0][3];
		animFrame[4]=tmpFrames[0][4];
		animFrame[5]=tmpFrames[0][5];

		pos.x=400;
		pos.y=0;
		animation=new Animation(1f/15f,animFrame);

	}
	public void roll(){
		roll=true;
	}
	public void reset(){
		pos.y=0;
		roll=false;
	}
	public boolean isRolling(){
		if(pos.y<300)
			return true;
		else
			return false;
	}
	//Returns sum of dice or -1 if they match
	public int getRollResult(){
		System.out.println(die1+" "+die2);
		if(die1==die2){
			return -1;
		}
		else{
			//plus two, one for each dice
			return(die1+die2+2);
		}
	}

	public void update(){
		
		if(roll&&pos.y<300){
			pos.y+=9;
			turn+=1;
			die1=(int)((Math.random()*6));
			die2=(int)((Math.random()*6));
		}
		else {
			
			roll=false;
		}
	}
	public void render(SpriteBatch sb){
		elapsedTime += Gdx.graphics.getDeltaTime();
		if(roll){
			sb.draw(animation.getKeyFrame(elapsedTime, true), pos.x-80,pos.y, 0, 0, 64, 64, 1, 1, turn);
			sb.draw(animation.getKeyFrame(elapsedTime, true), pos.x,pos.y, 0, 0, 64, 64, 1, 1, turn);
		}
		else{
			sb.draw(animFrame[die1], pos.x-80,pos.y, 0, 0, 64, 64, 1, 1, turn);
			sb.draw(animFrame[die2], pos.x,pos.y, 0, 0, 64, 64, 1, 1, turn);
		}


	}

}
