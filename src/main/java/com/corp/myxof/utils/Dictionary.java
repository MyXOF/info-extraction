package com.corp.myxof.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class Dictionary {
	private Set<String> dictionary;
	
	public Dictionary(){
		dictionary = new HashSet<>();
	}
	
	public Dictionary(String path){
		dictionary = new HashSet<>();
		try {
			readFromFile(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void readFromFile(String path) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(path));
		String line;
		while((line = reader.readLine()) != null){
			dictionary.add(line);
		}
		reader.close();
	}
	
	public Set<String> getDictionary(){
		return dictionary;
	}
	
	public boolean inDictionary(String word){
		return dictionary.contains(word);
	}
	
	public static void main(String[] args) throws IOException {
		Dictionary dictionary = new Dictionary("src/main/resources/people");
		System.out.println(dictionary.inDictionary("任志秋"));
		
//		BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/people"));
//		for(String word : dictionary.getDictionary()){
//			writer.write(word+"\n");
//		}
//		writer.close();
	}
}
