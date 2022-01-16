/**VARIABLES*********/

//We control which screen is active by settings / updating
//gameScreen variable. We display the correct screen accoring to the value of thie variable
//
//0: Initial Screen
//1: Game Screen
//2: Game-over Screen

int gameScreen = 1; //initianlizing gameSreen

//Ball
        float ballX, ballY;
        int ballSize = 20;
        int ballColor = color(0);

//Gravity
        float gravity = 1;
        float ballSpeedVert = 0;

/**SETUP BLOCK****/

        void setup(){
        size(500,500);

        ballX = width/4;
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
        applyGravity();
        keepInScreen();

        }

        void gameOverScreen(){
        background(255);
        }

        void drawBall(){
        fill(ballColor);
        ellipse(ballX, ballY, ballSize, ballSize);
        }

        void applyGravity(){
        ballSpeedVert += gravity;
        ballY += ballSpeedVert;
        }

        void makeBounceBottom(float surface){
        ballY = surface - (ballSize/2);
        ballSpeedVert *= -1;
        }

        void makeBounceTop(float surface){
        ballY = surface + (ballSize/2);
        ballSpeedVert *= -1;
        }

//Keep ball in the screen
        void keepInScreen(){
//ball hits floor
        if(ballY+(ballSize/2) > height){
        makeBounceTop(0);
        }
//ball hits ceiling
        if(ballY - (ballSize/2) < 0){
        makeBounceTop(0);
        }
        }