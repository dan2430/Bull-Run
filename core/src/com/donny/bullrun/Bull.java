package com.donny.bullrun;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.donny.bullrun.camera.OrthoCamera;

public class Bull {
	private int x=10;
	private int y=10;
	private Vector2 pos;
	private Vector2 direction;
	private double velocity;
	Texture bull=new Texture("bull.png");
	Texture bullAnim=new Texture("bullAnim.png");
	TextureRegion[] animFrame;
	Animation<TextureRegion> animation;
	float turn;
	float attackTime;
	long reverseTime;
	boolean attack=false;

	ShapeRenderer shapeRenderer;
	Polygon head=new Polygon(new float[]{120,0,180,0,180,90,120,90});
	Polygon body=new Polygon(new float[]{0,0,120,0,120,90,0,90});
	enum going{
		REVERSE,
		FORWARD;
	}
	going g;

	enum dir{
		LEFT,
		RIGHT,
		HARDLEFT,
		HARDRIGHT,
		STOPPED;

	}
	dir m;
	private float elapsedTime;


	public Bull(int x, int y){
		head.setOrigin(90, 45);
		body.setOrigin(90, 45);
		pos=new Vector2(x,y);
		direction=new Vector2();
		shapeRenderer=new ShapeRenderer();
		attackTime=0;
		elapsedTime=0;
		head.setPosition(pos.x, pos.y);
		body.setPosition(pos.x, pos.y);
		m=dir.STOPPED;

		TextureRegion[][] tmpFrames = TextureRegion.split(bullAnim,128,64);
		animFrame=new TextureRegion[4];
		animFrame[0]=tmpFrames[0][0];
		animFrame[1]=tmpFrames[0][1];
		animFrame[2]=tmpFrames[0][2];
		animFrame[3]=tmpFrames[0][3];

		animation=new Animation(1f/15f,animFrame);
	}
	public void attack(){
		attack =true;

	}
	public void turnLeft(){
		m=dir.LEFT;
	}
	public void turnRight(){
		m=dir.RIGHT;
	}
	public void turnHardLeft(){
		m=dir.HARDLEFT;
	}
	public void turnHardRight(){
		m=dir.HARDRIGHT;
	}




	public void forward(){

		g=going.FORWARD;
	}
	public void back(){
		g=going.REVERSE;
		reverseTime=System.currentTimeMillis();
	}


	public void update(){
		if(attackTime>10){
			attack=false;
			attackTime=0;
		}



		if(attack){
			attackTime=attackTime+Gdx.graphics.getDeltaTime();
			//System.out.println(attackTime);
			//System.out.println(Gdx.graphics.getDeltaTime()*10000);
			if((int)(Gdx.graphics.getDeltaTime()*10000%23)==0){
				if((int)(Math.random()*2)==1)
					turnLeft();
				else
					turnRight();
			}
			if(g==going.REVERSE){
				//System.out.println("BACK");
				if(System.currentTimeMillis()-reverseTime>700){
					g=going.FORWARD;

				}


			}


			if(m==dir.LEFT){
				turn+=1;
			}
			else if(m==dir.RIGHT){
				turn-=1;
			}
			else if(m==dir.HARDRIGHT){
				turn-=3;
			}
			else if(m==dir.HARDLEFT){
				turn+=3;
			}

			if(g==going.FORWARD){
				velocity=300;
			}
			else if(g==going.REVERSE){
				velocity=-300;;
			}
			//shape.rotate(1, 1, 1, 1);
			x=(int) (Math.cos(Math.toRadians(turn))*velocity);
			y=(int) (Math.sin(Math.toRadians(turn))*velocity);
			setDirection(x,y);
			pos.add(direction);
			head.setRotation(turn);
			head.setPosition(pos.x, pos.y);
			body.setRotation(turn);
			body.setPosition(pos.x, pos.y);
		}

	}
	/**
	 * Sprite Batch for text and texture, camera for resizing
	 * 
	 * @param sb
	 * @param camera
	 */
	public void render(SpriteBatch sb, OrthoCamera camera){
		elapsedTime += Gdx.graphics.getDeltaTime();
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		if(attack)
			sb.draw(animation.getKeyFrame(elapsedTime, true), pos.x,pos.y, 90, 45, 190, 90, 1, 1, turn);
		else
			sb.draw(animFrame[0], pos.x,pos.y, 90, 45, 180, 90, 1, 1, turn);
		//sb.draw(bull, pos.x, pos.y, 64, 32, 128, 64, 1f, 1f, turn, 0, 0, 128, 64, false, false);
		sb.end();
	}
	public void drawDebug(OrthoCamera camera) {
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.polygon(head.getTransformedVertices());
		shapeRenderer.polygon(body.getTransformedVertices());
		shapeRenderer.end();
	}

	public void setDirection(float x, float y){
		direction.set(x,y);

		direction.scl(Gdx.graphics.getDeltaTime());
	}
	public Polygon getHeadPosition(){
		return head;
	}
	public Polygon getBodyPosition(){
		return body;
	}

}
