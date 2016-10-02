package com.corp.myxof.relation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corp.myxof.config.InfoExtractionConfig;
import com.corp.myxof.xml.XMLManagerReader;
import com.corp.myxof.xml.XMLManagerWriter;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class RelationshipExtractor {
	private static final Logger LOGGER = LoggerFactory.getLogger(RelationshipExtractor.class);
	
	private StanfordCoreNLP pipeline;
	private Properties properties;
	private XMLManagerReader reader;
	private XMLManagerWriter writer;

	public RelationshipExtractor() throws DocumentException {
		properties = new Properties();
	}

	public void init() throws IOException {
		properties.load(RelationshipExtractor.class.getResourceAsStream(InfoExtractionConfig.NLP_PROPS_FILE));
		pipeline = new StanfordCoreNLP(properties);
	}

	private Set<String> parsePersonName(String content) {
		Set<String> nameList = new HashSet<>();
		StringBuffer buffer = new StringBuffer();
		Annotation annotation = new Annotation(content);
		pipeline.annotate(annotation);
		List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			List<CoreLabel> coreLabelores = sentence.get(TokensAnnotation.class);
			int length = coreLabelores.size();
			for (int i = 0; i < length; i++) {
				CoreLabel token = coreLabelores.get(i);
				String ne = token.get(NamedEntityTagAnnotation.class);
				String pos = token.get(PartOfSpeechAnnotation.class);
				String name = token.get(TextAnnotation.class);
				LOGGER.debug("{}--{}--{}", name, pos, ne);
				if (ne.trim().equals("PERSON")) {
					if (name.length() == 1 && i + 1 < length) {
						CoreLabel nextToken = coreLabelores.get(i + 1);
						String nameNext = nextToken.get(TextAnnotation.class);
						buffer.append(name).append(nameNext);
						i++;
						continue;
					} else if (name.length() > 3) {
						continue;
					} else {
						nameList.add(name);
						continue;
					}
				}

				if (pos.trim().equals("NR") && ne.equals("O") && i+1<length) {
					CoreLabel nextToken = coreLabelores.get(i + 1);
					String nameNext = nextToken.get(TextAnnotation.class);
					buffer.append(name).append(nameNext);
					i++;
					continue;
				}

			}
		}
		LOGGER.debug("------------------------");
		LOGGER.debug(buffer.toString());
		LOGGER.debug("------------------------");
		annotation = new Annotation(buffer.toString());
		pipeline.annotate(annotation);
		sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			for (CoreLabel token :  sentence.get(TokensAnnotation.class)) {
				String ne = token.get(NamedEntityTagAnnotation.class);
				String pos = token.get(PartOfSpeechAnnotation.class);
				String name = token.get(TextAnnotation.class);
				LOGGER.debug("{}--{}--{}", name, pos, ne);
				if (ne.trim().equals("PERSON") && name.length() > 1 && name.length() < 4) {
					nameList.add(name);
				}

			}
		}
		return nameList;
	}

	public void getAllNameInContent(String path) throws DocumentException, IOException {
		int count = 0;
		reader = new XMLManagerReader(path);
		writer = new XMLManagerWriter(InfoExtractionConfig.PEOPLE_ROOT_LABLE);
		try {
			while (reader.hasNext()) {
				Element info = reader.getNext();
				String performer = info.attributeValue(InfoExtractionConfig.PERFORMER_NAME_LABEL);
				String content = info.getText();
				writer.addPeopleInfo(performer, parsePersonName(content));
				count++;
				LOGGER.info("{}: parse {} article",count, performer);
			}
		} finally {
			writer.writeDocument(InfoExtractionConfig.PEOPLE_FILE);
		}
	}
	
	private Relationship parseAllRelationship(String content, String name){
		Relationship relationship = new Relationship();
		
		Annotation annotation = new Annotation(content);
		pipeline.annotate(annotation);
		List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		
		
		return relationship;
	}
	
	public void getAllRelationShipInContent(String path) throws DocumentException, IOException{
		int count = 0;
		reader = new XMLManagerReader(path);
		writer = new XMLManagerWriter(InfoExtractionConfig.RELATIONSHIP_ROOT_LABLE);
		
		while (reader.hasNext()) {
			Element info = reader.getNext();
			String performer = info.attributeValue(InfoExtractionConfig.PERFORMER_NAME_LABEL);
			String content = info.getText();
			Relationship relationship = parseAllRelationship(content, performer);
			count++;
			writer.addRelationInfo(performer, relationship.getMasters(), relationship.getApprentices(), relationship.getFamily(), relationship.getParters());
			LOGGER.info("{}: parse {}'s relationship article",count, performer);
		}
		writer.writeDocument(InfoExtractionConfig.RELATIONSHIP_FILE);
	}
	
	public void getAllRelationToFile(String directory, String output) throws IOException{
		File dir = new File(directory);
		File[] fileList = dir.listFiles();
		BufferedWriter writer = new BufferedWriter(new FileWriter(output));		

		for(File file: fileList){
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			String name = file.getName();
			while((line = reader.readLine()) != null){
				Relationship relationship = parseAllRelationship(line, name);
				
				for(String master : relationship.getMasters()){
					writer.write(String.format("%s --master-- %s\n", master, name));
				}
				for(String apprentice : relationship.getApprentices()){
					writer.write(String.format("%s --apprentice-- %s\n", apprentice, name));
				}
				for(String member : relationship.getFamily()){
					writer.write(String.format("%s --member-- %s\n", member, name));
				}
				for(String parter : relationship.getParters()){
					writer.write(String.format("%s --parter-- %s\n", parter, name));
				}
			}
			reader.close();
		}
		
		writer.close();
	}
	

	
	public static void main(String[] args) throws IOException, DocumentException {
		String text = "人物：康喜寿 康喜寿 人物分类 京剧 生行演员 科班院校 富连成社 喜字科 学生 康喜寿，男，京剧武生。 能戏绝夥，"
				+ "无论长靠短打之武生戏，几无一不能。其著者，靠把戏中以《长坂坡》、《金锁阵》、《烧战船》、《阳平关》之赵云，《战渭南》、"
				+ "《冀州城》之马超，《战濮阳》之吕布，《挑滑车》之高宠，短打戏以《花蝴蝶》之姜永志，《溪皇庄》之尹亮，"
				+ "《八大拿》等戏之黄天霸，演来万分精彩，火炽异常。他如做工念白戏如《连环套》，唱工戏如《刺巴杰》、《独木关》等，"
				+ "演来亦颇脍炙人口。至武净戏如《艳阳楼》、《金钱豹》、《铁笼山》尤为精湛。被视为杨小楼真正传人。可惜未能洁身自好，过早地死去。"
				+ "活动年表 本页使用了 JavaScript 技术。若使本页能正常显示，请启用您浏览器的 JavaScript 功能。 参看 耿喜斌、金连寿、刘喜益 如果您对此人物有任何补充，欢迎提供。 最后更新：2006年01月05日 编辑整理：大戏魔 浏览次数：2453";
		RelationshipExtractor extractor = new RelationshipExtractor();
		extractor.init();
		extractor.parsePersonName(text);
	}
}
