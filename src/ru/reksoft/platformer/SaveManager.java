package ru.reksoft.platformer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import com.thoughtworks.xstream.XStream;

public class SaveManager {
	private static SaveManager instance = new SaveManager();
	private XStream stream;
	File folder = new File("saves");

	public static SaveManager getInstance() {
		return instance;
	}

	private SaveManager() {
		stream = new XStream();
	}

	public void saveProgress(GameProgress progress) {
		String filename = Long.toString(System.currentTimeMillis());
		progress.saveDate=new Date();
		try {
			stream.toXML(progress, new FileOutputStream(new File(folder, filename)));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public List<GameProgress> getProgressList() {
		List<GameProgress> saves = new ArrayList<GameProgress>();
		File[] files = folder.listFiles();
		for (File file : files) {
			try {
				Object o = stream.fromXML(file);
				if (o instanceof GameProgress) {
					saves.add((GameProgress) o);
				}
			} catch (Exception e) {

			}
		}

		return saves;
	}
	private void cleanup(){
		File[] files = folder.listFiles();
		if(files.length<6){
			return;
		}
		Arrays.sort(files, new Comparator<File>() {

			@Override
			public int compare(File o1, File o2) {
				long l1=Long.parseLong(o1.getName());
				long l2=Long.parseLong(o1.getName());
				if(l1>l2){
					return 1;
				}else{
					return -1;
				}
				
			}
		});
		files[0].delete();
	}
}
