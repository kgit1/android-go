package com.konggit.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class FlappyBird extends ApplicationAdapter {
    //SpriteBatch - batch of sprites(images)
    private SpriteBatch batch;
    //Texture - some sprite(image)
    private Texture background;
    private Texture[] birds;
    private Texture[] tubes;

    private int flapState = 0;
    private boolean gameState = false;

    private float birdX;
    private float birdY;
    private float tubeDownY;
    private float tubeUpY;
    private float tubesX;
    private float tubesXMoved;
    private float tubesMove = 2;

    private float birdMaxUp = -700;
    private float birdMaxDown = 800;
    private float birdDownMove = 0;
    private float birdVelocity = 0;
    private float jumpHeight = 200;
    private float jump;
    private float tubesVelocity = 3;

    @Override
    //when app starts
    public void create() {

        batch = new SpriteBatch();
        background = new Texture("background.jpg");
        birds = new Texture[2];
        tubes = new Texture[2];
        birds[0] = new Texture("bird1a.png");
        birds[1] = new Texture("bird2a.png");
        tubes[0] = new Texture("tube_down.png");
        tubes[1] = new Texture("tube_up.png");

        //draw background image starting from coordinates half of the screen width - half of birds width - 30px
        birdX = Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2 - 30;
        //half of the screen height - half of birds height
        birdY = Gdx.graphics.getHeight() / 2 - birds[flapState].getHeight() / 2;

        tubeDownY = 1450;
        tubeUpY = 0;
        tubesX = Gdx.graphics.getWidth();

    }

    @Override
    //during app work
    public void render() {


        //called every time screen touched
        if (Gdx.input.justTouched()) {

            if (gameState) {
                Gdx.app.log("GDXLog", "Touched");
                Gdx.app.log("Log", "DownMovement " + String.valueOf(-tubes[0].getWidth()));
                Gdx.app.log("Log", "DownMovement " + String.valueOf(tubesX - tubesXMoved));

                if (birdDownMove < birdMaxUp) {
                    Gdx.app.log("Log","ZERO JUMP");
                    jump = 0;
                } else {
                    jump = jumpHeight;
                }

                birdDownMove -= jump;
                birdVelocity = 1;

            } else {
                gameState = true;
            }
        }


        if (flapState == 0) {
            flapState = 1;
        } else {
            flapState = 0;
        }
        if (birdDownMove < birdMaxDown) {

            if (gameState) {
                if (birdVelocity < 10) {
                    birdVelocity++;
                   // Gdx.app.log("Log", "Velocity: " + String.valueOf(birdVelocity));
                }
                birdDownMove += birdVelocity;
                //Gdx.app.log("Log", "DownMovement " + String.valueOf(birdDownMove));
            }
        } else {
            gameState = false;
            birdDownMove = 0;
            tubesXMoved = 0;
            //Gdx.app.log("GDXLog", "GAME OVER");
        }

        if (gameState) {
            tubesXMoved += tubesVelocity;
            if ((tubesX - tubesXMoved) < -tubes[0].getWidth()) {
                Gdx.app.log("Log", "DownMovement " + String.valueOf(-tubes[0].getWidth()));
                Gdx.app.log("Log", "DownMovement " + String.valueOf(tubesX));
                tubesXMoved = 0;
            }

        }

        batch.begin();

        //draw background image starting from coordinates 0,0 with width and heigth of device
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //draw bird image starting from coordinates birdX and birdY
        batch.draw(birds[flapState], birdX, birdY - birdDownMove);
        batch.draw(tubes[0], tubesX - tubesXMoved, tubeDownY);
        batch.draw(tubes[1], tubesX - tubesXMoved, tubeUpY);
        batch.end();

    }
}
