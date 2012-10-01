package ru.reksoft.platformer;

import java.text.DateFormat;
import java.util.Date;

public class GameProgress {
	
	public static final DateFormat sdf = DateFormat.getInstance();

	public String currentLevel;
	public PersonStats player;
	public Date saveDate=new Date();

	public GameProgress() {
		currentLevel = "data/level1.tmx";
		player = new PersonStats();
	}

	@Override
	public String toString() {
		String level = currentLevel.replaceFirst("data/", "").replaceFirst(
				".tmx", "");
		return level + ", "+sdf.format(saveDate);
	}
}
