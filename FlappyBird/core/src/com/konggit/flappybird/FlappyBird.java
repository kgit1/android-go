package com.konggit.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

public class FlappyBird extends ApplicationAdapter {

    //SpriteBatch - batch of sprites(images)
    private SpriteBatch batch;

    //set font to draw lettering (title or legend) on the screen
    private BitmapFont font;

    //Texture - some sprite(image)
    private Texture background;
    private Texture[] birds;
    private Texture topTube;
    private Texture bottomTube;
    private Texture gameOver;
    private Texture gameStart;

    private int flapState = 0;

    private final int NOT_STARTED = 0;
    private final int STARTED = 1;
    private final int ENDED = 2;
    private int gameState = NOT_STARTED;

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
    private float jumpHeight = 15;
    private float jump;
    private float tubesVelocity = 4;

    private int score = 0;
    private int topScore = 0;
    private int scoringTube = 0;

    private ShapeRenderer shapeRenderer;
    private Circle birdCircle;
    private Rectangle[] topRectangle;
    private Rectangle[] bottomRectangle;


    @Override
    //when app starts
    public void create() {

        batch = new SpriteBatch();

        font = new BitmapFont();
        font.setColor(Color.WHITE);
        font.getData().setScale(5);

        background = new Texture("background.jpg");
        birds = new Texture[3];
        birds[0] = new Texture("bird1a.png");
        birds[1] = new Texture("bird2a.png");
        birds[2] = new Texture("bird3.png");
        gameOver = new Texture("gameover.png");
        gameStart = new Texture("gamestart.png");

        shapeRenderer = new ShapeRenderer();
        birdCircle = new Circle();
        topRectangle = new Rectangle[numberOfTubes];
        bottomRectangle = new Rectangle[numberOfTubes];

        topTube = new Texture("top_tube.png");
        bottomTube = new Texture("bottom_tube.png");

        //draw bird image starting from coordinates half of the screen width - half of birds width - 30px
        birdX = Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2 - 30;
        //half of the screen height - half of birds height
        birdY = Gdx.graphics.getHeight() / 2 - birds[flapState].getHeight() / 2;

        int halfScreenWidth = Gdx.graphics.getHeight() / 2;
        int halfTubesGap = 250;
        distanceBetweenTubes = halfScreenWidth;

        birdMaxDown = halfScreenWidth - birds[0].getWidth() / 2;
        birdMaxUp = -halfScreenWidth + birds[0].getWidth();

        initTubesDefaultsY(halfScreenWidth, halfTubesGap);

        initTubesCoordinates(numberOfTubes);

        initRectangles();

    }

    @Override
    //during app work
    public void render() {

        batch.begin();

        //draw background image starting from coordinates 0,0 with width and height of device
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //called every time screen touched
        if (Gdx.input.justTouched()) {

            if (gameState == STARTED) {

                if (birdDownMove < birdMaxUp) {
                    Gdx.app.log("Log", "ZERO JUMP");
                    jump = 0;
                } else {
                    jump = jumpHeight;
                }

                for (int i = 0; i < 10; i++) {

                    birdDownMove -= jump;

                }

                birdVelocity = 1;

            } else if (gameState == NOT_STARTED) {

                reinitGameObjects();
                gameState = STARTED;

            } else {
                gameState = NOT_STARTED;
            }

        }
        if (gameState == STARTED) {

            if (birdDownMove < birdMaxDown) {

                birdCircle.set(Gdx.graphics.getWidth() / 2 - 20, birdY - birdDownMove + birds[flapState].getHeight() / 2, birds[flapState].getHeight() / 2);

                //shapeRenderer.rect(bottomRectangle[i].x,bottomRectangle[i].y,bottomRectangle[i].width, bottomRectangle[i].height);
                //shapeRenderer.rect(topRectangle[i].x,topRectangle[i].y,topRectangle[i].width, topRectangle[i].height);

                if (Intersector.overlaps(birdCircle, topRectangle[scoringTube]) || Intersector.overlaps(birdCircle, bottomRectangle[scoringTube])) {

                    Gdx.app.log("Log", "Collision");
                    gameOver();

                }

                if (birdVelocity < 10) {

                    birdVelocity++;
                    // Gdx.app.log("Log", "Velocity: " + String.valueOf(birdVelocity));

                }

                birdDownMove += birdVelocity;
                //Gdx.app.log("Log", "DownMovement " + String.valueOf(birdDownMove));

            } else {
                gameOver();
            }

            if (tubesX[scoringTube] < Gdx.graphics.getWidth() / 2 - bottomTube.getWidth() - birds[0].getWidth() / 2) {

                score++;

                if (scoringTube < numberOfTubes - 1) {

                    scoringTube++;
                    Gdx.app.log("ScoringTUBE!!! ", "" + scoringTube);

                } else {

                    scoringTube = 0;

                }

                Gdx.app.log("Score ", "" + score);

                if (topScore < score) {

                    topScore++;
                    Gdx.app.log("Score ", "" + score);
                    Gdx.app.log("topScore ", "" + topScore);

                }

            }

        }

        if (gameState != NOT_STARTED) {

            for (int i = 0; i < numberOfTubes; i++) {

               /* batch.draw(bottomTube, tubesX[i], tubeY[i][0]);
                batch.draw(topTube, tubesX[i], tubeY[i][1]);
                //draw bird image starting from coordinates birdX and birdY
                batch.draw(birds[flapState], birdX, birdY - birdDownMove);*/

                drawBird(batch,birdX, birdY - birdDownMove);

                drawBottomTube(batch,tubesX[i], tubeY[i][0]);

                drawTopTube(batch,tubesX[i], tubeY[i][1]);


                bottomRectangle[i].set(tubesX[i], tubeY[i][0], bottomTube.getWidth(), bottomTube.getHeight());
                topRectangle[i].set(tubesX[i], tubeY[i][1], topTube.getWidth(), topTube.getHeight());

                if (gameState == STARTED) {

                    tubesX[i] -= tubesVelocity;


                    if (tubesX[i] < -topTube.getWidth()) {

                        tubesX[i] = numberOfTubes * (distanceBetweenTubes) - bottomTube.getWidth();

                        int randomInt = randomNumber();
                        tubeY[i][0] = tubeYDefaults[randomInt][0];
                        tubeY[i][1] = tubeYDefaults[randomInt][1];
                        Gdx.app.log("Log", "randomPosition " + String.valueOf(randomInt));
                    }

                } else {

                    batch.draw(gameOver, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);

                }

            }

            birdWingFlipper();
            drawScores(batch);


        } else {

            batch.draw(gameStart, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);

        }

        batch.end();

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

    private void gameOver() {

        Gdx.app.log("Score ", "" + score);
        Gdx.app.log("topScore ", "" + topScore);

        gameState = ENDED;
        birdY = Gdx.graphics.getHeight() / 2 - birds[2].getHeight() / 2 - 50;

        Gdx.app.log("GDXLog", "GAME OVER");

    }

    private void reinitGameObjects() {

        birdDownMove = 0;
        score = 0;
        scoringTube = 0;
        flapState = 0;

        initTubesCoordinates(numberOfTubes);

        initRectangles();

    }

    private void initRectangles() {

        for (int i = 0; i < numberOfTubes; i++) {

            bottomRectangle[i] = new Rectangle();
            topRectangle[i] = new Rectangle();

        }

    }

    private void initTubesDefaultsY(int widthBetweenTubes, int halfTubesGap) {

        tubeYDefaults[0][0] = widthBetweenTubes - halfTubesGap - bottomTube.getHeight();
        tubeYDefaults[0][1] = widthBetweenTubes + halfTubesGap;
        tubeYDefaults[1][0] = halfTubesGap - bottomTube.getHeight();
        tubeYDefaults[1][1] = halfTubesGap * 3;
        tubeYDefaults[2][0] = widthBetweenTubes * 2 - halfTubesGap * 3 - bottomTube.getHeight();
        tubeYDefaults[2][1] = widthBetweenTubes * 2 - halfTubesGap;

    }

    private void drawScores(SpriteBatch batch) {

        font.draw(batch, "Score", 100, 250);
        font.draw(batch, String.valueOf(score), 100, 120);
        font.draw(batch, "TopScore", 100, 490);
        font.draw(batch, String.valueOf(topScore), 100, 370);

    }

    private void drawBird(SpriteBatch batch,float x, float y){

        batch.draw(birds[flapState], x, y);

    }

    private void drawBottomTube(SpriteBatch batch,float x, float y){

        batch.draw(bottomTube, x, y);

    }

    private void drawTopTube(SpriteBatch batch,float x, float y){

        batch.draw(topTube, x, y);

    }


    private void birdWingFlipper() {

        if (gameState == STARTED) {
            
            if (flapState == 0) {

                flapState = 1;

            } else {

                flapState = 0;

            }
        }

        if (gameState == ENDED) {

            flapState = 2;

        }

    }
}
