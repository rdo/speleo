package ru.reksoft.platformer.states.play;

import org.newdawn.fizzy.CollisionEvent;
import org.newdawn.fizzy.WorldListener;

import ru.reksoft.platformer.objects.DynamicGameObject;

public class DynamicObjectCollisionListener implements WorldListener{
	
	private PlatformerLevel world;
	
	public DynamicObjectCollisionListener(PlatformerLevel world) {
		super();
		this.world = world;		
	}

	@Override
	public void collided(CollisionEvent event) {
		Object ud1=event.getBodyA().getUserData();
		Object ud2=event.getBodyB().getUserData();
		DynamicGameObject a=null;
		DynamicGameObject b=null;
		
		//String string1="null";
		//String string2="null";
		//if(ud1!=null){
		//	string1= ud1.toString();
		//}
		//if(ud2!=null){
		//	string2= ud2.toString();
		//}
		//System.out.println(string1+" - "+string2);
		
		
		if(ud1 instanceof DynamicGameObject){
			a = (DynamicGameObject)ud1;
		}
		if(ud2 instanceof DynamicGameObject){
			b = (DynamicGameObject)ud2;
		}
		if(a!=null){
			a.collidesWith(b);
		}
		if(b!=null){
			b.collidesWith(a);
		}
		
	}

	@Override
	public void separated(CollisionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
