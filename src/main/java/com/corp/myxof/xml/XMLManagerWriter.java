package com.corp.myxof.xml;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.corp.myxof.config.InfoExtractionConfig;

public class XMLManagerWriter extends AbstractXMLManager{

	public XMLManagerWriter(String rootName) {
		document = DocumentHelper.createDocument();
		root = DocumentHelper.createElement(rootName);
		document.setRootElement(root);
	}

	public void addPerformerInfo(String url, String name, String content) {
		root.addElement(InfoExtractionConfig.PERFORMER_LABEL)
			.addAttribute(InfoExtractionConfig.PERFORMER_NAME_LABEL, name)
			.addAttribute(InfoExtractionConfig.PERFORMER_WEB_URL_LABEL, url)
			.addText(content);
	}
	
	public void addPeopleInfo(String name, Set<String> people){
		Element actor = root.addElement(InfoExtractionConfig.PEOPLE_LABEL).addAttribute(InfoExtractionConfig.PEOPLE_NAME_LABEL, name);
		for(String person : people){
			actor.addElement(InfoExtractionConfig.PEOPLE_NAME_LABEL).addText(person);
		}
	}

	public void addRelationInfo(String name, Set<String> masters, Set<String> apprentices, Set<String> family, Set<String> parters){
		Element actor = root.addElement(InfoExtractionConfig.PEOPLE_LABEL).addAttribute(InfoExtractionConfig.PEOPLE_NAME_LABEL, name);
		if(masters != null && !masters.isEmpty()){
			for(String master : masters){
				actor.addElement(InfoExtractionConfig.RELATIONSHIP_MASTER_LABLE).addText(master);
			}
		}
		
		if(apprentices != null && !apprentices.isEmpty()){
			for(String apprentice : apprentices){
				actor.addElement(InfoExtractionConfig.RELATIONSHIP_APPRENTICE_LABLE).addText(apprentice);
			}
		}
		
		if(family != null && !family.isEmpty()){
			for(String member : family){
				actor.addElement(InfoExtractionConfig.RELATIONSHIP_FAMILY_LABLE).addText(member);
			}
		}
		
		if(parters != null && !parters.isEmpty()){
			for(String parter : parters){
				actor.addElement(InfoExtractionConfig.RELATIONSHIP_PARTER_LABLE).addText(parter);
			}
		}
	}
	
	public void writeDocument(String path) throws IOException {
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(new FileWriter(path),format);
		writer.write(document);
		writer.close();
	}

	public static void main(String[] args) throws IOException {
		Set<String> names = new HashSet<>();
		names.add("1");
		names.add("2");
		names.add("3");
		XMLManagerWriter writer = new XMLManagerWriter("root");
		writer.addRelationInfo("xuyi", names, names, null, null);
		writer.addRelationInfo("yixu", names, names, null, null);

		writer.writeDocument("text.xml");
	}
}
