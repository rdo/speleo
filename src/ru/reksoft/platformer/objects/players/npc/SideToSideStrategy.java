package ru.reksoft.platformer.objects.players.npc;


public class SideToSideStrategy extends AbstractNpcStrategy {
	
	

	@Override
	public void update() {
		
		float xDiff=Math.abs(self.prevX-self.getX());
		if(xDiff<1){
				self.moveRigth=!self.moveRigth;
		}
		self.prevX=self.getX();
		if(r.nextInt()%3==0){
			self.jump();
		}
		self.move();
		
		self.shootToPlayer();
	}

}
