/**VARIABLES*********/

//We control which screen is active by settings / updating
//gameScreen variable. We display the correct screen accoring to the value of thie variable
//
//0: Initial Screen
//1: Game Screen
//2: Game-over Screen
int gameScreen = 0; //initianlizing gameSreen

//Ball
        float ballX, ballY;
        int ballSize = 15;
        int ballColor = color(0);

//Gravity
        float gravity = 1;
        float ballSpeedVert = 0;

//friction
        float airfriction = 0.0001;
        float friction = 0.1;

//racket
        color racketColor = color(0);
        float racketWidth = 100;
        float racketHeight = 10;
        int racketBounceRate = 10;

//we will start with 0, but we give 10 just for testing
        float ballSpeedHorizon = 10;

//Walls
        int wallSpeed = 5;
        int wallInterval = 1000;
        float lastAddTime = 0;
        int minGapHeight = 200;
        int maxGapHeight = 300;
        int wallWidth = 80;
        color wallColors = color(0);
//This arraylist stores data of the gaps between the walls. Actuals walls are draw accordingly.
//[gapWallX,gapWallY,gapWallWidth,gapWallHeight]
        ArrayList<int[]> walls = new ArrayList<int[]>();


/**SETUP BLOCK****/

        void setup(){
        size(500,500);

        ballX = width/2;
        ballY = height/5;

        }

/***********DRAW BLOCK *************/

        void draw(){
//Display the contents of the current screen
        if(gameScreen == 0) {
        initScreen();
        }else if (gameScreen == 1) {
        gameScreen();
        }else if (gameScreen == 2) {
        gameOverScreen();
        }
        }

/**SCREEN CONTENTS**/

        void initScreen(){
        background(0);

        textAlign(CENTER);

        text("Click to start", height/2, width/2);
        }

        void gameScreen(){
        background(255);
        drawBall();
        drawRacket();
        watchRacketBounce();
        applyGravity();
        applyHorizontalSpeed();
        keepInScreen();
        wallAdder();
        wallHandler();
        }

        void gameOverScreen(){
        background(255);
        }

/********* INPUTS *********/

public void mousePressed() {
// if we are on the initial screen when clicked, start the game
        if (gameScreen==0) {
        startGame();
        }
        }


/********* OTHER FUNCTIONS *********/

// This method sets the necessary variables to start the game
        void startGame() {
        gameScreen=1;
        }

        void drawBall(){
        fill(ballColor);
        ellipse(ballX, ballY, ballSize, ballSize);
        }

        void drawRacket(){
        fill(racketColor);
        rectMode(CENTER);
        rect(mouseX, mouseY, racketWidth, racketHeight);
        }

        void watchRacketBounce(){
        float overhead = mouseY - pmouseY;
        if((ballX + (ballSize/2) > mouseX - (racketWidth/2)) && (ballX - (ballSize/2) < mouseX + (racketWidth/2))) {
        if(dist(ballX,ballY,ballX,mouseY) <= (ballSize/2) + abs(overhead)){
        makeBounceBottom(mouseY);
//racket moving up
        if(overhead < 0){
        ballY += overhead;
        ballSpeedVert += overhead;
        }
        ballSpeedHorizon = (ballX - mouseX)/5;
        }
        }
        }

        void applyGravity(){
        ballSpeedVert += gravity;
        ballY += ballSpeedVert;
        ballSpeedVert -= (ballSpeedVert * airfriction);
        }

        void makeBounceBottom(float surface){
        ballY = surface - (ballSize/2);
        ballSpeedVert *= -1;
        ballSpeedVert -= (ballSpeedVert * friction);
        }

        void makeBounceTop(float surface){
        ballY = surface + (ballSize/2);
        ballSpeedVert *= -1;
        ballSpeedVert -= (ballSpeedVert * friction);
        }

        void applyHorizontalSpeed(){
        ballX += ballSpeedHorizon;
        ballSpeedHorizon -= (ballSpeedHorizon * airfriction);

        }

        void makeBounceRight(float surface){
        ballX = surface - (ballSize/2); //bounce to the left side
        ballSpeedHorizon *= -1;
        ballSpeedHorizon -= (ballSpeedHorizon * friction);
        }

        void makeBounceLeft(float surface){
        ballX = surface + (ballSize/2); //bounce to the right side
        ballSpeedHorizon *= -1;
        ballSpeedHorizon -= (ballSpeedHorizon * friction);
        }




//Keep ball in the screen
        void keepInScreen(){
//ball hits floor
        if(ballY+(ballSize/2) > height){
        makeBounceBottom(height);
        }
//ball hits floor
        if(ballY - (ballSize/2) < 0){
        makeBounceTop(0);
        }
//ball bounce at left wall
        if(ballX - (ballSize/2) < 0){
        makeBounceLeft(0); //bounce occur at Left Wall.
        }
//ball bounce at right wall
        if(ballX +(ballSize/2) > width){
        makeBounceRight(width);
        }
        }

        void wallAdder(){
        if(millis() - lastAddTime > wallInterval){
        int randHeight = round(random(minGapHeight, maxGapHeight));
        int randY = round(random(0, height - randHeight));
        //{gapWallX,gapWallY,gapWallWidth,gapWallHeight}
        int[]randWall = {width, randY, wallWidth, randHeight};
        walls.add(randWall);
        lastAddTime = millis();
        }
        }

        void wallHandler(){
        for(int i = 0; i < walls.size(); i++){
        wallRemover(i);
        wallMover(i);
        wallDrawer(i);
        }
        }

        void wallDrawer(int index){
        int[] wall = walls.get(index);
        //get gap wall settings
        int gapWallX = wall[0];
        int gapWallY = wall[1];
        int gapWallWidth = wall[2];
        int gapWallHeight = wall[3];
        //draw actual walls
        rectMode(CORNER);
        rect(gapWallX, 0, gapWallWidth, gapWallY);
        rect(gapWallX, gapWallY, gapWallHeight, gapWallWidth, height - (gapWallY + gapWallHeight));
        }

        void wallMover(int index){
        int[] wall = walls.get(index);
        wall[0] -= wallSpeed;
        }

        void wallRemover(int index){
        int[] wall = walls.get(index);
        if(wall[0] + wall[2] <= 0){
        walls.remove(index);
        }
        }