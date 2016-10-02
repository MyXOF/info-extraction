package com.corp.myxof.config;

public class InfoExtractionConfig {
	public static final String WEB_URL = "http://history.xikao.com/";
	public static final String START_URL = "http://history.xikao.com/person/梅兰芳";
	public static final int URL_NAME = 32;
	public static final String START_PERSON = "梅兰芳";
	public static final String URL_START_FILTER = "http://history.xikao.com/person/";
	public static final String URL_END_FILTER = "/comment";
	public static final String WEB_CONTENT_ID = "article";
	public static final String NEXT_WEB_LINK = "a.exist[href]";
	public static final String HYPERLINK_NAME = "abs:href";
	public static final int MAX_WEB_NUM = 1000;
	
	public static final String PERFORMER_ROOT_LABEL = "root";
	public static final String PERFORMER_LABEL = "performer";
	public static final String PERFORMER_NAME_LABEL = "name";
	public static final String PERFORMER_WEB_URL_LABEL = "url";
	public static final String PERFORMER_ARTICLE_LABEL = "article";
	public static final String PERFORMER_FILE = "web-content.xml";
	
	public static final String PEOPLE_ROOT_LABLE = "root";
	public static final String PEOPLE_LABEL = "performer";
	public static final String PEOPLE_NAME_LABEL = "name";
	public static final String PEOPLE_FILE = "people.xml";
	
	public static final String RELATIONSHIP_ROOT_LABLE = "root";
	public static final String RELATIONSHIP_MASTER_LABLE = "master";
	public static final String RELATIONSHIP_APPRENTICE_LABLE = "apprentice";
	public static final String RELATIONSHIP_FAMILY_LABLE = "family";
	public static final String RELATIONSHIP_PARTER_LABLE = "parter";
	public static final String RELATIONSHIP_FILE = "relationship.xml";
	
	public static final String NLP_PROPS_FILE = "/StanfordCoreNLP-chinese.properties";
	
}
