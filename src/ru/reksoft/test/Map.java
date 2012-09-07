package ru.reksoft.test;


import org.jbox2d.common.Vec2;
import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Circle;
import org.newdawn.fizzy.CollisionEvent;
import org.newdawn.fizzy.Rectangle;
import org.newdawn.fizzy.World;
import org.newdawn.fizzy.WorldListener;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Map extends BasicGame{
	private static final int FRAME_RATE = 20;
	Image car=null;
	World world = null;
	public float rotation;
	float x;
	float y;
	
	TiledMap map;
	
	private Body player;
	boolean w_pressed=false;
	boolean a_pressed=false;
	boolean s_pressed=false;
	boolean d_pressed=false;
	boolean space_pressed=false;
	
    int velocityIterations = 6;
    int positionIterations = 2;

	public Map(String title) {
		super(title);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		//int xPos=(int)player.getX()-400;
		//int yPos=(int)player.getY()-300;
		map.render(0, 0);
		
		int total = world.getBodyCount();
		for(int i=0;i<total;i++){
			Body b = world.getBody(i);
			if(b.equals(player)){
				//Rectangle r = (Rectangle)b.getShape();
				//car.rotate(player.getRotation());
				rotation = rotation%360;
				Image rotated=car.copy();
				rotated.rotate(rotation);
				y=(float) Math.cos(rotation*Math.PI/180);
				x=(float) Math.sin(rotation*Math.PI/180);
				g.drawImage(rotated, (b.getX()-33), (b.getY()-61));
				g.drawString(Float.toString(rotation), 1, 1);
				g.drawString(Float.toString(x)+" - "+Float.toString(y), 50, 1);
				
			}
			else if (b.getShape() instanceof Circle){
				//g.drawImage(car, b.getX()-8, b.getY()-8);
				g.drawRect(b.getX()-2, b.getY()-2, 4, 4);
				//g.drawString(i+" - ("+b.getX() +"; "+b.getY()+")", 0, i*12);
				//System.out.println(i+" - "+b.getX() +" : "+b.getY());
			}
			else if(b.getShape() instanceof Rectangle){
				Rectangle r = (Rectangle)b.getShape();
				g.drawRect(b.getX()-r.getWidth()/2, b.getY()-r.getHeight()/2,r.getWidth(), r.getHeight() );
			}
		}
		
		
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		map=new TiledMap("data/map1.tmx");
		car = new Image("data/tank.png");
		world = new World(0,0,800,600,0,1);
		//for (int i=0;i<1000;i++) {
		//	Body body = new Body(new Circle(4.0f), (16*i)%800, (16*i)%600);
		//	world.add(body);
		//}
		//world.add(new Body(new Rectangle(1800.0f, 10.0f), 1, 590.0f, true));
		//world.add(new Body(new Rectangle(10.0f, 1600.0f), 1, 1, true));
		//world.add(new Body(new Rectangle(10.0f, 1600.0f), 790, 1, true));
		player= new Body(new Circle(25), 400, 300);
		world.add(player);
		//player.setRotation(player.getRotation()+0.11f);
		world.addListener(new WorldListener(){

			@Override
			public void collided(CollisionEvent event) {
				Body a=event.getBodyA();
				Body b=event.getBodyB();
				
				if(a.equals(player) && b.getShape() instanceof Rectangle) {
					applyFriction();
					System.out.println("Collision");
				}
				if(b.equals(player) && a.getShape() instanceof Rectangle) {
					applyFriction();
					System.out.println("Collision");
				}
				
			}

			@Override
			public void separated(CollisionEvent event){
				
			}
			
		});
		arg0.getInput().addKeyListener(new KeyboardListener());
		
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		if(a_pressed) {
			rotation-=1;
			applyFriction();
		}
		if(w_pressed) {
			player.getJBoxBody().applyForce(new Vec2(x*10000,-y*10000), new Vec2(0,0));
		}else{
			applyFriction();
		}
		if(s_pressed) {
			player.getJBoxBody().applyForce(new Vec2(-x*5000,y*5000), new Vec2(0,0));
		}
		if(d_pressed) {
			rotation+=1;
			applyFriction();
		}
		if(space_pressed){
			applyFire();
		}
		world.update(1);
		
	}
	
	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Map("Breakout"));
			app.setDisplayMode(800, 600, false);
			app.setTargetFrameRate(FRAME_RATE);
			app.setShowFPS(false);
			app.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	class KeyboardListener implements KeyListener{

		@Override
		public void setInput(Input paramInput) {}

		@Override
		public boolean isAcceptingInput() {
			return true;
		}

		@Override
		public void inputEnded() {}

		@Override
		public void inputStarted() {}

		@Override
		public void keyPressed(int paramInt, char paramChar) {
			if(paramChar=='w') w_pressed=true;
			if(paramChar=='a') a_pressed=true;
			if(paramChar=='s') s_pressed=true;
			if(paramChar=='d') d_pressed=true;
			if(paramChar==' ') space_pressed=true;
			
			
		
		}

		@Override
		public void keyReleased(int paramInt, char paramChar) {
			if(paramChar=='w') w_pressed=false;
			if(paramChar=='a') a_pressed=false;
			if(paramChar=='s') s_pressed=false;
			if(paramChar=='d') d_pressed=false;
			if(paramChar==' ') space_pressed=false;
			
		}
		
	}
	private void applyFriction() {
		float x=9*player.getXVelocity()/10;
		float y=9*player.getYVelocity()/10;
		player.getJBoxBody().setLinearVelocity(new Vec2(x,y));
		//player.getBody().setAngularVelocity(0);
		//player.setRotation(0);
	}
	private void applyFire(){
		Body body = new Body(new Circle(4.0f), player.getX()+60*x, player.getY()-60*y);
		world.add(body);
		body.getJBoxBody().setLinearVelocity(new Vec2(5*x,-5*y));
		//	world.add(body);
	}

}
