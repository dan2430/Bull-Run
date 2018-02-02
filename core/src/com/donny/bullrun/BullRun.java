package com.donny.bullrun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BullRun extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	GameScreen game;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		game=new GameScreen();
		
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0.4f, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		game.update();
		
		
		game.render(batch);
		
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
