package com.corp.myxof.visualization;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corp.myxof.config.InfoExtractionConfig;
import com.corp.myxof.utils.Pair;
import com.corp.myxof.xml.XMLManagerReader;

public class Visualization {
	private static final Logger LOGGER = LoggerFactory.getLogger(Visualization.class);
	private Set<Pair<String, String>> mentorships;
	private Map<String, Integer> masterInfo;
	private Random random;
	private String colors[] = { "#c71969", "#c71919", "#FE2EC8", "#8419c7",
								"#138DF8", "#39F813", "#13F86B", "#13F8D6",
								"#BFC821", "#1919c7", "#8E1AC8", "#F88A13"};
	
	private final int colorNum = colors.length;
	private final int majorColor = 4;

	public Visualization() {
		mentorships = new HashSet<>();
		masterInfo = new HashMap<>();
		random = new Random(System.currentTimeMillis());
	}

	@SuppressWarnings("unchecked")
	public void readMentorship(String path) throws DocumentException {
		XMLManagerReader reader = new XMLManagerReader(path);

		while (reader.hasNext()) {
			Element perform = reader.getNext();
			String name = perform.attributeValue(InfoExtractionConfig.PERFORMER_NAME_LABEL);
			
			List<Element> apprentices = perform.elements(InfoExtractionConfig.RELATIONSHIP_APPRENTICE_LABLE);
			List<Element> masters = perform.elements(InfoExtractionConfig.RELATIONSHIP_MASTER_LABLE);
			if(apprentices.size() < 6) continue;
			
			for (Element apprentice : apprentices) {
				addMentorship(name, apprentice.getText());
			}			
			
			for (Element master : masters) {
				addMentorship(master.getText(), name);
			}
		}
		LOGGER.info("mentor ship size : {}",mentorships.size());
	}

	public void createJsonData() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(InfoExtractionConfig.RELATIONSHIP_JSON_FILE));
		JSONObject data = new JSONObject();

		JSONArray nodes = new JSONArray();

		for (Map.Entry<String, Integer> entry : masterInfo.entrySet()) {

			JSONObject node = new JSONObject();
			node.put("size", entry.getValue());
			node.put("label", entry.getKey());
			node.put("attributes", new JSONObject());			
			node.put("id", entry.getKey());
			
			if(entry.getValue() > 10){
				node.put("color", colors[random.nextInt(majorColor)]);
			}else{
				node.put("color", colors[random.nextInt(colorNum-majorColor)+majorColor]);
			}
			
			node.put("y", random.nextInt(5000)-2500);			
			node.put("x", random.nextInt(10000)-5000);	
			
			nodes.put(node);
		}
		data.put("nodes", nodes);

		JSONArray edges = new JSONArray();
		for (Pair<String, String> mentorship : mentorships) {
			JSONObject edge = new JSONObject();
			edge.put("sourceID", mentorship.left);
			edge.put("attributes", new JSONObject());
			edge.put("targetID", mentorship.right);
			edge.put("size", 1);
			edges.put(edge);
		}
		data.put("edges", edges);
		writer.write(data.toString());
		writer.close();
	}

	private void addMentorship(String master, String apprentice) {
		if (!mentorships.contains(new Pair<String, String>(master, apprentice))) {
			mentorships.add(new Pair<String, String>(master, apprentice));
			if (masterInfo.containsKey(master)) {
				int num = masterInfo.get(master);
				masterInfo.put(master, num + 1);
			} else {
				masterInfo.put(master, 2);
			}

			if (!masterInfo.containsKey(apprentice)) {
				masterInfo.put(apprentice, 1);
			}
		}
	}

	public static void main(String[] args) throws DocumentException, IOException {
		Visualization visualization = new Visualization();
		visualization.readMentorship(InfoExtractionConfig.RELATIONSHIP_FILE);
		visualization.createJsonData();
	}

}
