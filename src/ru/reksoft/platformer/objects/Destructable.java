package ru.reksoft.platformer.objects;

public interface Destructable {
	
	public int getHp();

	public void changeHp(int value);

	public void onDeath();
}
