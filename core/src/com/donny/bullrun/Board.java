package com.donny.bullrun;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.donny.bullrun.camera.OrthoCamera;

public class Board {
	private OrthoCamera camera;
	private ArrayList<Player> players;
	private Bull bull;
	Polygon[] board;
	int playerTurn;
	//Polygon r=new Polygon(new float[]{0,0,64,0,64,64,0,64});
	BitmapFont font=new BitmapFont();
	Texture block =new Texture("block.png");
	Texture backGround=new Texture("background.jpg");
	ShapeRenderer shapeRenderer=new ShapeRenderer();


	public Board(int x, int y){
		board=new Polygon[36];
		fillBoard();


		font.getData().setScale(1.7f, 1.7f);
		font.setColor(Color.DARK_GRAY);
		playerTurn=0;
		players=new ArrayList<Player>();
		for(int i=0;i<3;i++){
			players.add(new Player(0, 0));
		}
		System.out.println("Created");
		players.get(playerTurn).turnOver=false;

		camera=new OrthoCamera();
		camera.resize();
		bull=new Bull(250,250);
	}
	public void update(){
		bull.update();



		if(players.get(playerTurn).isTurnOver()){

			if(players.get(playerTurn).rollResults()==-1){
				bull.attack();
				bull.forward();

				if((int)(Math.random()*2)==1)
					bull.turnLeft();
				else
					bull.turnRight();
			}


			System.out.println("PLayer "+playerTurn);
			playerTurn++;

			//bull.attack();
			//bull.forward();

			//			if((int)(Math.random()*2)==1)
			//				bull.turnLeft();
			//			else
			//				bull.turnRight();

			if(playerTurn>players.size()-1)
				playerTurn=0;
			players.get(playerTurn).turnOver=false;
		}

		//Collision detection
		for(int i=0;i<board.length;i++){

			if(Intersector.overlapConvexPolygons(board[i], bull.getHeadPosition())){

				System.out.println("COLLISION "+i);
				bull.back();

				if((int)(Math.random()*2)==1)
					bull.turnHardLeft();
				else
					bull.turnHardRight();
			}
			if(Intersector.overlapConvexPolygons(board[i], bull.getBodyPosition())){
				bull.forward();
				if((int)(Math.random()*2)==1)
					bull.turnHardLeft();
				else
					bull.turnHardRight();
			}


		}



		for(int i=0;i<players.size();i++){

			players.get(i).update();
		}



	}
	/**
	 * Fills the board array with Polygons for collision
	 * 
	 */
	private void fillBoard(){
		int index=0;


		int x=190;
		//Top Row
		for(int i=0;i<11;i++){ 
			//board[index]=new Rectangle(x,64,64,64);
			board[index]=new Polygon(new float[]{0,0,64,0,64,64,0,64});
			board[index].setPosition(x, 64);
			index++;
			x+=70;
		}

		x=190;
		//Bottom 
		for(int i=0;i<11;i++){ 
			//board[index]=new Rectangle(x,610,64,64);

			board[index]=new Polygon(new float[]{0,0,64,0,64,64,0,64});
			board[index].setPosition(x, 610);
			index++;
			x+=70;
		}
		//Left Side
		int y=124;
		for(int i=0;i<7;i++){
			board[index]=new Polygon(new float[]{0,0,64,0,64,64,0,64});
			board[index].setPosition(100,y);


			index++;

			y+=70;
		}
		//Right Side
		y=124;
		for(int i=0;i<7;i++){
			board[index]=new Polygon(new float[]{0,0,64,0,64,64,0,64});
			board[index].setPosition(980,y);

			index++;

			y+=70;
		}



	}
	public void drawDebug() {
		shapeRenderer.setColor(Color.GREEN);
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		for(int i=0;i<36;i++){
			shapeRenderer.polygon(board[i].getTransformedVertices());
		}


		shapeRenderer.end();
		bull.drawDebug(camera);
	}


	public void render(SpriteBatch sb){ 



		camera.resize();
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		sb.draw(backGround, 0, 0);
		int x=190;
		//horizontal lines
		for(int i=0;i<11;i++){ 
			sb.draw(block, x, 610);
			sb.draw(block, x, 64);
			x+=70;
		}


		int y=124;
		for(int i=0;i<7;i++){
			sb.draw(block, 100, y);
			sb.draw(block, 980, y);

			y+=70;
		}


		
		font.draw(sb,"Player "+ playerTurn, 1100, 600);
		
		sb.end();

		bull.render(sb,camera);
		//bull.drawDebug(camera);
		//drawDebug();

		players.get(playerTurn).render(sb,camera);


		


	}

}
