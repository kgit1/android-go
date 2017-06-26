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
    private Texture topTube;
    private Texture bottomTube;

    private int flapState = 0;
    private boolean gameState = false;

    private float birdX;
    private float birdY;
    private int numberOfTubes = 4;
    private float tubesX[] = new float[numberOfTubes];
    private float[][] tubeYDefaults = new float[3][2];
    private float tubeY[][] = new float[numberOfTubes][2];
    private float distanceBetweenTubes;

    private float birdMaxUp;
    private float birdMaxDown;
    private float birdDownMove = 0;
    private float birdVelocity = 0;
    private float jumpHeight = 200;
    private float jump;
    private float tubesVelocity = 4;


    @Override
    //when app starts
    public void create() {

        batch = new SpriteBatch();
        background = new Texture("background.jpg");
        birds = new Texture[2];
        birds[0] = new Texture("bird1a.png");
        birds[1] = new Texture("bird2a.png");
        topTube = new Texture("top_tube.png");
        bottomTube = new Texture("bottom_tube.png");

        //draw background image starting from coordinates half of the screen width - half of birds width - 30px
        birdX = Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2 - 30;
        //half of the screen height - half of birds height
        birdY = Gdx.graphics.getHeight() / 2 - birds[flapState].getHeight() / 2;

        int halfScreenWidth;
        halfScreenWidth = Gdx.graphics.getHeight() / 2;
        int halfTubesGap = 250;
        distanceBetweenTubes = halfScreenWidth;

        birdMaxDown = halfScreenWidth - birds[0].getWidth() / 2;
        birdMaxUp = -halfScreenWidth + birds[0].getWidth();

        tubeYDefaults[0][0] = halfScreenWidth - halfTubesGap - bottomTube.getHeight();
        tubeYDefaults[0][1] = halfScreenWidth + halfTubesGap;
        tubeYDefaults[1][0] = halfTubesGap - bottomTube.getHeight();
        tubeYDefaults[1][1] = halfTubesGap * 3;
        tubeYDefaults[2][0] = halfScreenWidth * 2 - halfTubesGap * 3 - bottomTube.getHeight();
        tubeYDefaults[2][1] = halfScreenWidth * 2 - halfTubesGap;

        initTubesCoordinates(numberOfTubes);

    }

    @Override
    //during app work
    public void render() {

        batch.begin();

        //draw background image starting from coordinates 0,0 with width and height of device
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //called every time screen touched
        if (Gdx.input.justTouched()) {

            if (gameState) {

                if (birdDownMove < birdMaxUp) {
                    Gdx.app.log("Log", "ZERO JUMP");
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
            initTubesCoordinates(numberOfTubes);
            //Gdx.app.log("GDXLog", "GAME OVER");
        }

        if (gameState) {

            for (int i = 0; i < numberOfTubes; i++) {
                batch.draw(bottomTube, tubesX[i], tubeY[i][0]);
                batch.draw(topTube, tubesX[i], tubeY[i][1]);

                tubesX[i] -= tubesVelocity;
                if (tubesX[i] < -topTube.getWidth()) {

                    Gdx.app.log("Log", "Tube width " + String.valueOf(topTube.getWidth()));
                    Gdx.app.log("Log", "Tube height " + String.valueOf(bottomTube.getHeight()));
                    Gdx.app.log("Log", "tubesX " + String.valueOf(tubesX));
                    Gdx.app.log("Log", "birdThickness " + String.valueOf(birds[0].getHeight()));
                    tubesX[i] = numberOfTubes * (distanceBetweenTubes) - bottomTube.getWidth();

                    int randomInt = randomNumber();
                    tubeY[i][0] = tubeYDefaults[randomInt][0];
                    tubeY[i][1] = tubeYDefaults[randomInt][1];
                    Gdx.app.log("Log", "randomPosition " + String.valueOf(randomInt));
                }
            }

        }

        if (flapState == 0) {
            flapState = 1;
        } else {
            flapState = 0;
        }

        //draw bird image starting from coordinates birdX and birdY
        batch.draw(birds[flapState], birdX, birdY - birdDownMove);
        batch.end();

    }

    private int randomNumber() {
        return (int) (Math.random() * 3);
    }

    private void initTubesCoordinates(int numberOfTubes){
        for (int i = 0; i < numberOfTubes; i++) {
            int randomInt = randomNumber();
            tubeY[i][0] = tubeYDefaults[randomInt][0];
            tubeY[i][1] = tubeYDefaults[randomInt][1];
            tubesX[i] = Gdx.graphics.getWidth() + bottomTube.getWidth() / 2 + i * (distanceBetweenTubes);
        }
    }
}
