package ru.reksoft.platformer;

public class GameProgress {

	public String currentLevel;
	public int hp;
	public int exp;
	public int speed;
	public int jumpPower;

	public GameProgress() {
		currentLevel="data/level1.tmx";
		hp=5;
		exp=0;
		speed=5;
		jumpPower=20;
	}
	
	@Override
	public String toString() {
		String level=currentLevel.replaceFirst("data/", "").replaceFirst(".tmx", "");
		return level+", "+hp+" hp, "+exp+" exp";
	}
	
}
