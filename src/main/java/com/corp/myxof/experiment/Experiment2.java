package com.corp.myxof.experiment;

import java.io.IOException;

import org.dom4j.DocumentException;

import com.corp.myxof.config.InfoExtractionConfig;
import com.corp.myxof.relation.RelationshipExtractor;

public class Experiment2 {

	public static void main(String[] args) throws IOException, DocumentException {
		RelationshipExtractor extractor = new RelationshipExtractor();
		extractor.init();
		extractor.getAllNameInContent(InfoExtractionConfig.PERFORMER_FILE);
//		extractor.getAllNameInContent("test.xml");

	}

}
