package com.corp.myxof.web;

import java.io.IOException;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corp.myxof.config.InfoExtractionConfig;
import com.corp.myxof.utils.Utils;
import com.corp.myxof.xml.XMLManagerWriter;

public class Crawler {
	private static final Logger LOGGER = LoggerFactory.getLogger(Crawler.class);

	private Set<String> visitedWeb;
	private Queue<String> unVisitedWeb;
	private int index;
	private XMLManagerWriter xmlManager;

	public Crawler() {
		unVisitedWeb = new LinkedBlockingQueue<String>();
		unVisitedWeb.add(InfoExtractionConfig.START_URL);

		visitedWeb = new HashSet<String>();
		index = 1;
		xmlManager = new XMLManagerWriter(InfoExtractionConfig.PERFORMER_ROOT_LABEL);
	}
	
	public void service() throws IOException{
		try {
			while(visitedWeb.size() <= InfoExtractionConfig.MAX_WEB_NUM && unVisitedWeb.size() > 0){
				String next = unVisitedWeb.poll();
				if(next != null && !visitedWeb.contains(next)){
					String reponseBody = getWebPage(next);
					visitedWeb.add(next);
					String content = parseWebPage(reponseBody, next);
					
					xmlManager.addPerformerInfo(next, next.substring(InfoExtractionConfig.URL_NAME), content);
				}
			}
		} finally {
			xmlManager.writeDocument(InfoExtractionConfig.PERFORMER_FILE);
		}
	}

	private String getWebPage(String url) {
		String responseBody = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);
			LOGGER.info("executing request {}--{}", index, httpget.getURI());
			index++;
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

				public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					} else {
						throw new ClientProtocolException("Unexpected response status: " + status);
					}
				}
			};
			responseBody = java.net.URLDecoder.decode(httpClient.execute(httpget, responseHandler),"utf-8");

		} catch (Exception e) {
			LOGGER.error("");
		}
		return responseBody;
	}
	
	private String parseWebPage(String webBody, String url){
		String content = null;
		if(webBody == null || url == null){
			return content;
		}
		Document document = Jsoup.parse(webBody, url);
		Elements links = document.select(InfoExtractionConfig.NEXT_WEB_LINK);

		for (Element link : links) {
			String nextLink = link.attr(InfoExtractionConfig.HYPERLINK_NAME).trim();
			if(Utils.isURLLegal(nextLink)){
				if(!visitedWeb.contains(nextLink)){
					unVisitedWeb.add(nextLink);
				}
			}
		}
		content = document.getElementById(InfoExtractionConfig.WEB_CONTENT_ID).text();
		return content;
	}
	
	public static void main(String[] args) throws IOException {
		Crawler crawler = new Crawler();
		crawler.service();
	}
}
