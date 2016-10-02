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
		String text = "梅兰芳从小和尚小云学习唱戏，后来拜程砚秋为师。";
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
