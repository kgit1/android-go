package com.konggit.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


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

    private ShapeRenderer shapeRenderer;
    private Circle birdCircle;
    private Rectangle[] topRectangle;
    private Rectangle[] bottomRectangle;


    @Override
    //when app starts
    public void create() {

        batch = new SpriteBatch();

        background = new Texture("background.jpg");
        birds = new Texture[2];
        birds[0] = new Texture("bird1a.png");
        birds[1] = new Texture("bird2a.png");

        shapeRenderer = new ShapeRenderer();
        birdCircle = new Circle();
        topRectangle = new Rectangle[numberOfTubes];
        bottomRectangle = new Rectangle[numberOfTubes];

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

        for (int i = 0; i < numberOfTubes; i++) {

            topRectangle[i] = new Rectangle();
            bottomRectangle[i] = new Rectangle();

        }

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

                birdCircle.set(Gdx.graphics.getWidth() / 2 - 20, birdY - birdDownMove + birds[flapState].getHeight() / 2, birds[flapState].getWidth() / 2);

                for (int i = 0; i < numberOfTubes; i++) {

                    //shapeRenderer.rect(bottomRectangle[i].x,bottomRectangle[i].y,bottomRectangle[i].width, bottomRectangle[i].height);
                    //shapeRenderer.rect(topRectangle[i].x,topRectangle[i].y,topRectangle[i].width, topRectangle[i].height);

                    if (Intersector.overlaps(birdCircle, topRectangle[i]) || Intersector.overlaps(birdCircle, bottomRectangle[i])) {

                        Gdx.app.log("Log", "Collision");
                        gameOver();

                    }

                }

                if (birdVelocity < 10) {
                    birdVelocity++;
                    // Gdx.app.log("Log", "Velocity: " + String.valueOf(birdVelocity));
                }
                birdDownMove += birdVelocity;
                //Gdx.app.log("Log", "DownMovement " + String.valueOf(birdDownMove));
            }
        } else {
            gameOver();
        }

        if (gameState) {

            for (int i = 0; i < numberOfTubes; i++) {
                batch.draw(bottomTube, tubesX[i], tubeY[i][0]);
                batch.draw(topTube, tubesX[i], tubeY[i][1]);

                bottomRectangle[i].set(tubesX[i], tubeY[i][0], bottomTube.getWidth(), bottomTube.getHeight());
                topRectangle[i].set(tubesX[i], tubeY[i][1], topTube.getWidth(), topTube.getHeight());

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

        //set values of circle
       // birdCircle.set(Gdx.graphics.getWidth() / 2 - 20, birdY - birdDownMove + birds[flapState].getHeight() / 2, birds[flapState].getWidth() / 2);

        //create and fill shapeRenderer
        //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //shapeRenderer.setColor(Color.ROYAL);
        //shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

       /*
       birdCircle.set(Gdx.graphics.getWidth() / 2 - 20, birdY - birdDownMove + birds[flapState].getHeight() / 2, birds[flapState].getWidth() / 2);

       for (int i = 0; i < numberOfTubes; i++) {

            //shapeRenderer.rect(bottomRectangle[i].x,bottomRectangle[i].y,bottomRectangle[i].width, bottomRectangle[i].height);
            //shapeRenderer.rect(topRectangle[i].x,topRectangle[i].y,topRectangle[i].width, topRectangle[i].height);

            if (Intersector.overlaps(birdCircle, topRectangle[i]) || Intersector.overlaps(birdCircle, bottomRectangle[i])) {

                Gdx.app.log("Log", "Collision");

            }


        }*/

        //shapeRenderer.end();


    }

    private int randomNumber() {
        return (int) (Math.random() * 3);
    }

    private void initTubesCoordinates(int numberOfTubes) {
        for (int i = 0; i < numberOfTubes; i++) {
            int randomInt = randomNumber();
            tubeY[i][0] = tubeYDefaults[randomInt][0];
            tubeY[i][1] = tubeYDefaults[randomInt][1];
            tubesX[i] = Gdx.graphics.getWidth() + bottomTube.getWidth() / 2 + i * (distanceBetweenTubes);
        }
    }

    private void gameOver(){

        gameState = false;
        birdDownMove = 0;
        initTubesCoordinates(numberOfTubes);
        Gdx.app.log("GDXLog", "GAME OVER");

    }
}
