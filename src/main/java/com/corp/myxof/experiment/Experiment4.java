package com.corp.myxof.experiment;

import java.io.IOException;

import org.dom4j.DocumentException;

import com.corp.myxof.relation.RelationshipExtractor;

public class Experiment4 {

	public static void main(String[] args) throws DocumentException, IOException {
		RelationshipExtractor extractor = new RelationshipExtractor();
		extractor.init();
		extractor.getAllRelationToFile("people-introduction", "result");
	}

}
