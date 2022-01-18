int ballSizeX = 30;
int ballSizeY = 30;
float ballX, ballY;

float gravity = 1;
float ballSpeedVert = 0;


void setup(){
  size(500,1000);

  ballX = width/4;
  ballY = height/5;
  
}

void draw(){
  gameScreen();  
}

void gameScreen(){
  background(200);
  drawBall();
  applyGravity();
}

void drawBall(){
  ellipse(ballX, ballY, ballSizeX, ballSizeY); 
}

void applyGravity(){
  ballSpeedVert += gravity;
  ballY += ballSpeedVert;
}
