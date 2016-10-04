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

import com.corp.myxof.config.InfoExtractionConfig;
import com.corp.myxof.utils.Pair;
import com.corp.myxof.xml.XMLManagerReader;

public class Visualization {
	private Set<Pair<String, String>> mentorships;
	private Map<String, Integer> masterInfo;
	private Random random;

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
			List<Element> masters = perform.elements(InfoExtractionConfig.RELATIONSHIP_MASTER_LABLE);
			for (Element master : masters) {
				addMentorship(master.getText(), name);
			}

			List<Element> apprentices = perform.elements(InfoExtractionConfig.RELATIONSHIP_APPRENTICE_LABLE);
			for (Element apprentice : apprentices) {
				addMentorship(name, apprentice.getText());
			}
		}
	}

	public void createJsonData() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(InfoExtractionConfig.RELATIONSHIP_JSON_FILE));
		JSONObject data = new JSONObject();

		JSONArray nodes = new JSONArray();

		for (Map.Entry<String, Integer> entry : masterInfo.entrySet()) {

			JSONObject node = new JSONObject();
			node.put("color", "#"+Integer.toHexString(random.nextInt(0xFFFFFF)));
			node.put("label", entry.getKey());
			node.put("attributes", new JSONObject());			
			node.put("y", random.nextInt(5000)-2500);			
			node.put("x", random.nextInt(5000)-2500);			
			node.put("id", entry.getKey());
			node.put("size", entry.getValue());
			
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
