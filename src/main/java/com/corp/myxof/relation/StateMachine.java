package com.corp.myxof.relation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.corp.myxof.utils.Pair;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.util.CoreMap;

public class StateMachine {

	private static final Logger LOGGER = LoggerFactory.getLogger(StateMachine.class);
	private Relationship relationship;
	private STATE state;
	private Set<String> peopleUnKnown;
	
	public StateMachine(String name){
		this.relationship = new Relationship(name);
		this.state = STATE.INIT;
		this.peopleUnKnown = new HashSet<>();
	}
	
	public void transform(CoreMap sentence,String firstName){
		List<CoreLabel> tokens = sentence.get(TokensAnnotation.class);
		int length = tokens.size();
		for(int i = 0; i < length; i++){
			CoreLabel currentToken = tokens.get(i);
			
			String currentNE = currentToken.get(NamedEntityTagAnnotation.class);
			String currnetPOS = currentToken.get(PartOfSpeechAnnotation.class);
			String currentName = currentToken.get(TextAnnotation.class);
			
			String nextName = null;
			if(i+1<length){
				nextName = tokens.get(i+1).get(TextAnnotation.class);
			}
			LOGGER.debug("{}--{}--{}--{}", currentName, currnetPOS, currentNE, nextName);
			Pair<WordType, String> pair = WordRecognizer.getWordType(firstName, currentName, currentNE, currnetPOS, nextName);
			nextState(pair.right, pair.left);
		}
		endState();
	}
	
	private void nextState(String word, WordType type){
		switch (type) {
		case PEOPLE:
			checkPeople(word);
			break;
		case MASTER_WORD:
			checkMasterWord(word);
			break;
		case APPRENTICE_WORD:
			checkApprenticeWord(word);
			break;
		case FAMILY_WORD:
			checkFamilyWord(word);
			break;
		case PARTNER_WORD:
			checkParterWord(word);
			break;			
		case SENTENCE_END:
			checkSentenceEnd(word);
			break;
		case REFERENCE_WORD:
			checkReferenceWord(word);
			break;
		case USELESS_WORD:
			checkUselessWord(word);
			break;
		default:
			break;
		}
	}
	
	private void endState(){
		switch (state) {
		case INIT:
			break;
		case MASTER:
			recongizePeople(relationship.getMasters());
			break;
		case APPRENTICE:
			recongizePeople(relationship.getApprentices());
			break;
		case FAMILY:
			recongizePeople(relationship.getFamily());
			break;
		case PARTER:
			recongizePeople(relationship.getParters());
			break;
		case REFERENCE:
			
			return;	
		default:
			break;
		}
	}
	
	private void checkPeople(String people){
		switch (state) {
		case INIT:
			peopleUnKnown.add(people);
			break;
		case MASTER:
			relationship.addMaster(people);
			LOGGER.debug("add master {}",people);
			break;
		case APPRENTICE:
			relationship.addApprentice(people);
			LOGGER.debug("add Apprentice {}",people);
			break;
		case FAMILY:
			relationship.addMember(people);
			LOGGER.debug("add member {}",people);
			break;
		case PARTER:
			relationship.addParter(people);
			LOGGER.debug("add parter {}",people);
			break;
		case REFERENCE:
			break;	
		default:
			break;
		}
	}
	
	private void checkMasterWord(String word){
		switch (state) {
		case INIT:
			if(word.length() >= 2){
				relationship.addMaster(word);
			}
			break;
		case MASTER:
			break;
		case APPRENTICE:
			recongizePeople(relationship.getApprentices());
			break;
		case FAMILY:
			recongizePeople(relationship.getFamily());
			break;
		case PARTER:
			recongizePeople(relationship.getParters());
			break;
		case REFERENCE:
			
			return;	
		default:
			break;
		}
		state = STATE.MASTER;
	}
	
	private void checkApprenticeWord(String word){
		switch (state) {
		case INIT:	
			break;
		case MASTER:
			recongizePeople(relationship.getMasters());
			break;
		case APPRENTICE:
			
			break;
		case FAMILY:
			recongizePeople(relationship.getFamily());
			break;
		case PARTER:
			recongizePeople(relationship.getParters());
			break;
		case REFERENCE:
			return;	
		default:
			break;
		}
		state = STATE.APPRENTICE;
	}
	
	private void checkFamilyWord(String word){
		switch (state) {
		case INIT:
			if(word.length() >= 2){
				relationship.addMember(word);
			}
			break;
		case MASTER:
			recongizePeople(relationship.getMasters());
			if(word.length() >= 2){
				relationship.addMember(word);
			}
			break;
		case APPRENTICE:
			return;
		case FAMILY:
			
			break;
		case PARTER:
			recongizePeople(relationship.getParters());
			break;
		case REFERENCE:
			return;	
		default:
			break;
		}
		state = STATE.FAMILY;
	}
	
	private void checkParterWord(String word){
		switch (state) {
		case INIT:
			state = STATE.PARTER;
			break;
		case MASTER:
			break;
		case APPRENTICE:
			break;
		case FAMILY:
			break;
		case PARTER:

			break;
		case REFERENCE:
			return;
		default:
			break;
		}
		
	}
	
	private void checkSentenceEnd(String word){
		switch (state) {
		case INIT:
			
			break;
		case MASTER:
			recongizePeople(relationship.getMasters());
			break;
		case APPRENTICE:
			recongizePeople(relationship.getApprentices());
			break;
		case FAMILY:
			recongizePeople(relationship.getFamily());
			break;
		case PARTER:
			recongizePeople(relationship.getParters());
			break;
		case REFERENCE:
			break;	
		default:
			break;
		}
		peopleUnKnown.clear();
		state = STATE.INIT;
	}
	
	private void checkReferenceWord(String word){
		switch (state) {
		case INIT:
			
			break;
		case MASTER:
			recongizePeople(relationship.getMasters());
			break;
		case APPRENTICE:
			recongizePeople(relationship.getApprentices());
			break;
		case FAMILY:
			recongizePeople(relationship.getFamily());
			break;
		case PARTER:
			recongizePeople(relationship.getParters());
			break;
		case REFERENCE:
			break;	
		default:
			break;
		}
		peopleUnKnown.clear();
		state = STATE.REFERENCE;
	}
	
	private void checkUselessWord(String word){

	}
	
	private void recongizePeople(Set<String> people){
		for(String person : peopleUnKnown){
			people.add(person);
		}
		peopleUnKnown.clear();
	}
	
	public enum STATE{
		INIT, MASTER, APPRENTICE, FAMILY, PARTER, REFERENCE
	}


	public Relationship getRelationship() {
		return relationship;
	}

	public void setRelationship(Relationship relationship) {
		this.relationship = relationship;
	}
}
