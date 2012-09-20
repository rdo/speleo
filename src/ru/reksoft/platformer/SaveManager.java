package ru.reksoft.platformer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.XStream;

public class SaveManager {
	private static SaveManager instance = new SaveManager();
	private XStream stream;

	public static SaveManager getInstance() {
		return instance;
	}

	private SaveManager() {
		 stream =  new XStream();
	}
	
	public void saveProgress(GameProgress progress){
		String filename = Long.toString(System.currentTimeMillis());
		try {
			stream.toXML(progress, new FileOutputStream(new File("saves/"+filename)));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public List<GameProgress> getProgressList(){
		List<GameProgress> saves = new ArrayList<GameProgress>();
		File folder = new File("saves");
		if(folder.exists() && folder.isDirectory()){
			File[] filse=folder.listFiles();
			for (File file : filse) {
				try {
					Object o=stream.fromXML(file);
					if(o instanceof GameProgress){
						saves.add((GameProgress)o);
					}
				} catch (Exception e) {
					
				}
			}
		}
		return saves;
	}
}
