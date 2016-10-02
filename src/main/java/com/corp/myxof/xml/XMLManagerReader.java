package com.corp.myxof.xml;

import java.io.File;
import java.util.Iterator;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLManagerReader extends AbstractXMLManager{
	private Iterator<Element> childNodes;

	@SuppressWarnings("unchecked")
	public XMLManagerReader(String path) throws DocumentException{
		SAXReader reader = new SAXReader();
		document = reader.read(new File(path));
		root = document.getRootElement();  
		childNodes = root.elements().iterator();
	}
	
	public boolean hasNext(){
		if(childNodes == null) return false;
		return childNodes.hasNext();
	}
	
	public Element getNext(){
		if(childNodes == null) return null;
		return childNodes.next();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
