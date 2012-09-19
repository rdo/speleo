package ru.reksoft.platformer.objects.npc;

import org.jbox2d.common.Vec2;
import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Circle;
import org.newdawn.slick.Graphics;

import ru.reksoft.platformer.objects.Destructable;
import ru.reksoft.platformer.objects.DynamicGameObject;
import ru.reksoft.platformer.objects.repository.Bullet;
import ru.reksoft.platformer.states.play.PlatformerLevel;

public abstract class  Person implements DynamicGameObject, Destructable{
	
	public Person(PlatformerLevel world, int x, int y){
		this.world=world;
		
		body= new Body(new Circle(20.0f), x,  y);
		body.setFriction(0);
		world.add(body);
		//TODO: terrible circular dependency
		body.setUserData(this);
	}

	public int MAX_JUMP_COUNT = 1;
	public float BULLET_SPEED=40;
	protected Body body;
	protected PlatformerLevel world;

	boolean is_jumping = false;
	boolean allow_next_jump = true;
	public State currentState;
	
	boolean looksRight=true;

	protected int jump_count = 0;
	
	protected int hp=5;
	
	private int exp=0; 
	
	protected Class bulletClass=Bullet.class;
	
	public void endJump() {
		collide();
		
		is_jumping=false;
		
		jump_count=0;
		allow_next_jump=true;
		
		currentState=State.STOP;
	}
	public void collide(){
		body.getJBoxBody().setLinearVelocity(new Vec2(body.getXVelocity(),0));
		body.getJBoxBody().setAngularVelocity(0);
	}
	
	public void jump(){
		if(allow_next_jump && isJumpAllowed()){
			body.getJBoxBody().setLinearVelocity(new Vec2(body.getXVelocity(),-15));
			is_jumping=true;
			jump_count++;
			allow_next_jump=false;
			currentState=State.JUMP;
		}
	}
	public void allowNextJump(){
		allow_next_jump=true;
	}
	public boolean isJumpAllowed(){
		return jump_count<MAX_JUMP_COUNT;
	}
	public void stepLeft(){
		looksRight=false;
		float velo = checkVelocity(body.getXVelocity());
		body.getJBoxBody().setLinearVelocity(new Vec2(-velo,body.getYVelocity()));
		if (currentState != State.JUMP){
		currentState=State.MOVE;
		}
	}
	public void stepRight(){
		looksRight=true;
		float velo = checkVelocity(body.getXVelocity());
		body.getJBoxBody().setLinearVelocity(new Vec2(velo,body.getYVelocity()));
		if (currentState != State.JUMP){
			currentState=State.MOVE;
		}
	}
	private float checkVelocity(float prevVelo) {
		//float velo=body.getXVelocity();
		if(Math.abs(prevVelo)<5){
			prevVelo=5;
		}else{
			return Math.abs(prevVelo);
		}
		return prevVelo;
	}
	public void stopMovement(){
		body.getJBoxBody().setLinearVelocity(new Vec2(body.getXVelocity()/2,body.getYVelocity()));
		if (currentState != State.JUMP) {
			currentState = State.STOP;
		}
	}
	public boolean isControllable(){
		return !(currentState==Person.State.JUMP && !isJumpAllowed());
	}
	public boolean isJumping(){
		return currentState==Person.State.JUMP;
	}

	public Body getBody() {
		return body;
	}
	public float getX(){
		return body.getX();
	}
	public float getY(){
		return body.getY();
	}
	
	public enum State{
		JUMP,
		STOP,
		MOVE;
	}

	@Override
	public String toString() {
		return "Person: hp="+hp+", state="+currentState;
	}
	
	public void shootTo(int x, int y){
		Bullet b = getDefaultBullet();
		shootTo(x, y, b);
		
	}
	
	public void shootTo(int x, int y, Bullet b){
		int xOffset;
		int yOffset;
		if(x>body.getX()){
			xOffset=25;
		}else{
			xOffset=-25;
		}
		if(y<body.getY()){
			yOffset=-15;
		}else{
			yOffset=15;
		}
		
		int xVel=(int) (x-body.getX());
		int yVel=(int)(y-body.getY());
		
		int modReal= xVel*xVel+yVel*yVel;
		float koeff=(float)Math.sqrt((double)modReal/(double)(BULLET_SPEED*BULLET_SPEED));
		
		b.setWorld(world);
		b.setStartPosition(body.getX()+xOffset, body.getY()+yOffset);
		b.setVelocity(xVel/koeff, yVel/koeff);
		b.launch();
		
	}
	@Override
	public void draw(Graphics g, int x, int y) {
		g.drawOval(x-8, y-8, 16, 16);
	}
	@Override
	public void collidesWith(DynamicGameObject other) {		
	}
	public PlatformerLevel getWorld() {
		return world;
	}
	@Override
	public int getHp() {
		return hp;
	}
	@Override
	public void changeHp(int value) {
		hp=hp+value;
		
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	
	public Bullet getDefaultBullet(){
		try{
			return (Bullet)bulletClass.newInstance();
		}catch(Exception e){
			return new Bullet();
		}
		
	}
	public void setBulletClass(Class bulletClass){
		this.bulletClass=bulletClass;
	}
	
	
	
}
