package ru.reksoft.platformer.objects.npc.strategies;



import org.newdawn.fizzy.Body;
import org.newdawn.fizzy.Rectangle;
import org.newdawn.fizzy.Shape;

public class GuardianStrategy extends AbstractNpcStrategy{
	
	boolean inited=false;
	
	float startX;
	float endX;
	
	float prevX;
	
	@Override
	public void update() {
		if(!inited){
			if(self.getLastCollision()==null){
				return;
			}
			System.out.println("guardian inited");
			inited=true;
			Body b = self.getLastCollision();
			float xPos=b.getX();
			Shape rect = b.getShape();
			if(rect instanceof Rectangle){
				startX=xPos-((Rectangle)rect).getWidth()/2+32;
				endX=xPos+((Rectangle)rect).getWidth()/2-32;
			}
			self.moveRight=true;
		}else{
			//if out of range, turn
			if(self.getX()< startX || self.getX()>endX ){
				self.moveRight=!self.moveRight;
			}
			//if can't move, turn
			if(Math.abs(self.getX()-prevX)<1){
				self.moveRight=!self.moveRight;
			}
			//move
			if(self.moveRight){
				self.stepRight();
			}else{
				self.stepLeft();
			}
			prevX=self.getX();
			self.shootToPlayer();
		}
		
	}

}
