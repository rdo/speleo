package ru.reksoft.platformer.objects.npc.strategies;

public class GuardianStrategy extends AbstractNpcStrategy{
	
	boolean inited=false;
	
	@Override
	public void update() {
		if(!inited && self.getLastCollision()!=null){
			inited=true;
		}
		
	}

}
