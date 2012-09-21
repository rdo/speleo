package ru.reksoft.platformer;

public class GameProgress {

	public String currentLevel;
	public CharacterInfo player;

	public GameProgress() {
		currentLevel = "data/level1.tmx";
		player = new CharacterInfo();
	}

	@Override
	public String toString() {
		String level = currentLevel.replaceFirst("data/", "").replaceFirst(
				".tmx", "");
		return level + ", " + player.hp + " hp, " + player.exp + " exp";
	}
}
