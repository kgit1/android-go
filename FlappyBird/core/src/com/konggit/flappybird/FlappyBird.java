package com.konggit.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends ApplicationAdapter {
    //SpriteBatch - batch of sprites(images)
	SpriteBatch batch;
    //Texture - some sprite(image)
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
	}

	@Override
	public void render () {
        //draw color(1,0,0,1 is red)
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //start gathering batch
		batch.begin();
        //add img to batch
		batch.draw(img, 0, 0);
        //end batch build
		batch.end();
	}
}
