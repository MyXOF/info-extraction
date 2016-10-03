package com.corp.myxof.utils;

import java.io.*;
import java.util.*;

import edu.stanford.nlp.hcoref.data.CorefChain;
import edu.stanford.nlp.hcoref.CorefCoreAnnotations;
import edu.stanford.nlp.io.*;
import edu.stanford.nlp.ling.*;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.util.*;

public abstract class Test {

	public static void main(String[] args) throws IOException {
		String text = "人物：华慧麟（华月英） 华慧麟 出生：1913年，农历癸丑年 逝世：1964年，农历甲辰年 人物分类 京剧 旦行演员 京剧 教师 "
				+ "华慧麟，女，京剧旦角。原名华月英。江苏无锡人。 最初在上海向名旦冯子和、李琴仙学戏，之后拜师王瑶卿、梅兰芳。"
				+ "她的嗓音清亮甜润，在吐字发音、用气行腔上，得王瑶卿传授。演唱周正大方，寓华丽于纯朴之中。"
				+ "往往是一段平常的唱腔，她却唱得通达流畅、韵味醇厚。例如她在《法门寺》中饰宋巧姣唱“小贫妇家住在郿坞小县，遵父命与傅朋匹配良缘”的唱词，她在上句句尾的“县”字，使用了高拖腔，下句句尾的“缘”字则用的是低腔。她高腔轻扬，唱得圆满到家，低腔唱得坚实沉着，浑厚有力。这一上一下、一扬一抑，给人以坚实饱满、圆润和谐之感。这种在简中求繁、在顺畅中见神彩的效果，显示了她在演唱上的浑厚功力。"
				+ "由于她领会王瑶卿的表演艺术深入、全面，凡王瑶卿所擅演的剧目，她均能演得出色，并常代师教授徒弟。新中国建立后她曾在中国戏曲学校任教，毕业于该校的刘秀荣、杨秋玲、曹佛生、曹毅琳、艾美君、周长云、刘长瑜等均得其教益。 活动年表 本页使用了 JavaScript 技术。若使本页能正常显示，请启用您浏览器的 JavaScript 功能。 参看 鲍吉祥、曹毅琳、程玉菁、陈少霖、方连元、黄孝慈、李开屏、刘秀荣、李维康、梅兰芳、沈健瑾、王瑶卿、魏希云、阎宝泉、杨秋玲、叶盛兰、张德俊、张曼玲 如果您对此人物有任何补充，欢迎提供。 最后更新：2005年12月02日 编辑整理：大戏魔 浏览次数：4836";
		// set up optional output files
		PrintWriter out = new PrintWriter("optional");
		
		PrintWriter xmlOut = new PrintWriter("test.xml");
		

		// Create a CoreNLP pipeline. To build the default pipeline, you can
		// just use:
		// StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
		// Here's a more complex setup example:
		 Properties props = new Properties();
		 props.load(Test.class.getResourceAsStream("/StanfordCoreNLP-chinese.properties"));
		 System.out.println(props.getProperty("annotators"));
//		 props.put("annotators", "tokenize, ssplit, pos, ner");
//		 props.put("ner.model", "edu/stanford/nlp/models/ner/chinese.misc.distsim.crf.ser.gz");
//		 props.put("ner.applyNumericClassifiers", "false");
//		 props.put("pos.model", "edu/stanford/nlp/models/pos-tagger/chinese-distsim/chinese-distsim.tagger");

		// StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		// Add in sentiment
//		Properties props = new Properties();
//		props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref, sentiment");

		StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		// Initialize an Annotation with some text to be annotated. The text is
		// the argument to the constructor.
//		Annotation annotation = new Annotation("Kosgi Santosh sent an email to Stanford University. He didn't get a reply.");
		Annotation annotation = new Annotation(text);
		

		// run all the selected Annotators on this text
		pipeline.annotate(annotation);

		// this prints out the results of sentence analysis to file(s) in good
		// formats
		pipeline.prettyPrint(annotation, out);
		if (xmlOut != null) {
			pipeline.xmlPrint(annotation, xmlOut);
		}

		// Access the Annotation in code
		// The toString() method on an Annotation just prints the text of the
		// Annotation
		// But you can see what is in it with other methods like
		// toShorterString()
		out.println();
		out.println("The top level annotation");
		out.println(annotation.toShorterString());
		out.println();

		// An Annotation is a Map with Class keys for the linguistic analysis
		// types.
		// You can get and use the various analyses individually.
		// For instance, this gets the parse tree of the first sentence in the
		// text.
		List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
		
		for(CoreMap sentence: sentences) {
			  // traversing the words in the current sentence
			  // a CoreLabel is a CoreMap with additional token-specific methods
			  for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
			    // this is the text of the token
			    String word = token.get(TextAnnotation.class);
			    // this is the POS tag of the token
			    String pos = token.get(PartOfSpeechAnnotation.class);
			    // this is the NER label of the token
			    String ne = token.get(NamedEntityTagAnnotation.class);
			    System.out.println(String.format("%s--%s--%s", word,pos,ne));
			  }

			  // this is the parse tree of the current sentence
			  Tree tree = sentence.get(TreeAnnotation.class);

			  // this is the Stanford dependency graph of the current sentence
			  SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
			}
//		if (sentences != null && !sentences.isEmpty()) {
//			CoreMap sentence = sentences.get(0);
//			out.println("The keys of the first sentence's CoreMap are:");
//			out.println(sentence.keySet());
//			out.println();
//			out.println("The first sentence is:");
//			out.println(sentence.toShorterString());
//			out.println();
//			out.println("The first sentence tokens are:");
//			for (CoreMap token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
//				out.println(token.toShorterString());
//			}
//			Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
//			out.println();
//			out.println("The first sentence parse tree is:");
////			tree.pennPrint(out);
//			out.println();
//			out.println("The first sentence basic dependencies are:");
//			out.println(sentence.get(SemanticGraphCoreAnnotations.BasicDependenciesAnnotation.class)
//					.toString(SemanticGraph.OutputFormat.LIST));
//			out.println("The first sentence collapsed, CC-processed dependencies are:");
//			SemanticGraph graph = sentence
//					.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
//			out.println(graph.toString(SemanticGraph.OutputFormat.LIST));
//
//			// Access coreference. In the coreference link graph,
//			// each chain stores a set of mentions that co-refer with each
//			// other,
//			// along with a method for getting the most representative mention.
//			// Both sentence and token offsets start at 1!
//			out.println("Coreference information");
//			Map<Integer, CorefChain> corefChains = annotation.get(CorefCoreAnnotations.CorefChainAnnotation.class);
//			if (corefChains == null) {
//				return;
//			}
//			for (Map.Entry<Integer, CorefChain> entry : corefChains.entrySet()) {
//				out.println("Chain " + entry.getKey() + " ");
//				for (CorefChain.CorefMention m : entry.getValue().getMentionsInTextualOrder()) {
//					// We need to subtract one since the indices count from 1
//					// but the Lists start from 0
//					List<CoreLabel> tokens = sentences.get(m.sentNum - 1).get(CoreAnnotations.TokensAnnotation.class);
//					// We subtract two for end: one for 0-based indexing, and
//					// one because we want last token of mention not one
//					// following.
//					out.println("  " + m + ", i.e., 0-based character offsets ["
//							+ tokens.get(m.startIndex - 1).beginPosition() + ", "
//							+ tokens.get(m.endIndex - 2).endPosition() + ")");
//				}
//			}
//			out.println();
//
//			out.println("The first sentence overall sentiment rating is "
//					+ sentence.get(SentimentCoreAnnotations.SentimentClass.class));
//		}
		IOUtils.closeIgnoringExceptions(out);
		IOUtils.closeIgnoringExceptions(xmlOut);

	}

}
