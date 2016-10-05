package com.corp.myxof.relation;

import com.corp.myxof.config.InfoExtractionConfig;
import com.corp.myxof.utils.Dictionary;
import com.corp.myxof.utils.Pair;

public class WordRecognizer {

	private static final Dictionary DICTIONARY = new Dictionary(InfoExtractionConfig.DICTIONARY);

	public static Pair<WordType, String> getWordType(String firstName, String word, String ne, String pos,
			String nextWord) {
		WordType type = WordType.USELESS_WORD;

		if (wordInList(word, InfoExtractionConfig.USELESS_WORDS)) {
			return new Pair<WordType, String>(WordType.REFERENCE_WORD, "");
		}

		// Sentence Word
		if (wordInList(word, InfoExtractionConfig.SENTENCE_END)) {
			return new Pair<WordType, String>(WordType.SENTENCE_END, "");
		}

		// Reference Word
		if (wordInList(word, InfoExtractionConfig.REFERENCE_WORDS)) {
			return new Pair<WordType, String>(WordType.REFERENCE_WORD, "");
		}

		// Master Word
		if (wordInList(word, InfoExtractionConfig.MASTER_WORDS)) {
			return new Pair<WordType, String>(WordType.MASTER_WORD, "");
		}
		if (word.length() > 2 && wordInList(word.substring(0, 2), InfoExtractionConfig.MASTER_WORDS)
				&& !wordInList(word, InfoExtractionConfig.APPRENTICE_WORDS)) {
			return checkMaster(word, nextWord, 2);
		}
		if (word.length() > 1 &&wordInList(word.substring(0, 1), InfoExtractionConfig.MASTER_WORDS)
				&& !wordInList(word, InfoExtractionConfig.APPRENTICE_WORDS)) {
			return checkMaster(word, nextWord, 1);
		}

		// Apprentice Word
		if (wordInList(word, InfoExtractionConfig.APPRENTICE_WORDS)) {
			return new Pair<WordType, String>(WordType.APPRENTICE_WORD, word);
		}

		// Pater Word
		if (wordInList(word, InfoExtractionConfig.PARTER_WORDS)) {
			return new Pair<WordType, String>(WordType.PARTER_WORD, word);
		}

		// Family Word
		if (wordInList(word, InfoExtractionConfig.FAMILY_WORDS)
				|| WordRecognizer.wordEndsInList(word, InfoExtractionConfig.FAMILY_WORDS)) {
			if (WordRecognizer.wordInList(nextWord, InfoExtractionConfig.TYPE_END)) {
				return new Pair<WordType, String>(WordType.USELESS_WORD, "");
			}
			return new Pair<WordType, String>(WordType.FAMILY_WORD, "");
		}
		if (word.length() > 2 && wordInList(word.substring(0, 2), InfoExtractionConfig.FAMILY_WORDS)
				&& !wordInList(word, InfoExtractionConfig.APPRENTICE_WORDS)) {
			return checkFamily(word, firstName, 2);
		}
		if (word.length() > 1 && wordInList(word.substring(0, 1), InfoExtractionConfig.FAMILY_WORDS)
				&& !wordInList(word, InfoExtractionConfig.APPRENTICE_WORDS)) {
			return checkFamily(word, firstName, 1);
		}

		// Person
		if (ne.equals("PERSON")) {
			Pair<WordType, String> result = checkPeople(word, nextWord, firstName);

			if(result != null){
				return result;
			}

			if (word.length() > 1) {
				return new Pair<WordType, String>(WordType.PEOPLE, word);
			}
		}

		if (pos.equals("NN") || pos.equals("NR") || pos.equals("AD") || pos.equals("JJ")) {
			Pair<WordType, String> result = checkPeople(word, nextWord, firstName);

			if(result != null){
				return result;
			}
			
			if (DICTIONARY.inDictionary(word + nextWord)) {
				return new Pair<WordType, String>(WordType.PEOPLE, word + nextWord);
			}

			if (DICTIONARY.inDictionary(firstName + word.substring(0, 1) + nextWord)) {
				return new Pair<WordType, String>(WordType.PEOPLE, firstName + word.substring(0, 1) + nextWord);
			}

			if (DICTIONARY.inDictionary(word) && word.length() > 1) {
				return new Pair<WordType, String>(WordType.PEOPLE, word);
			}
		}

		return new Pair<WordType, String>(type, word);
	}

	private static Pair<WordType, String> checkFamily(String word, String firstName, int beginIndex) {
		if (DICTIONARY.inDictionary(word.substring(beginIndex))) {
			return new Pair<WordType, String>(WordType.FAMILY_WORD, word.substring(beginIndex));
		}
		if (DICTIONARY.inDictionary(firstName + word.substring(beginIndex))) {
			return new Pair<WordType, String>(WordType.FAMILY_WORD, firstName + word.substring(beginIndex));
		}
		if (DICTIONARY.inDictionary(word.substring(beginIndex))) {
			return new Pair<WordType, String>(WordType.FAMILY_WORD, word.substring(beginIndex));
		}
		return new Pair<WordType, String>(WordType.FAMILY_WORD, "");
	}

	private static Pair<WordType, String> checkMaster(String word, String nextWord, int beginIndex) {
		if (DICTIONARY.inDictionary(word.substring(beginIndex))) {
			return new Pair<WordType, String>(WordType.MASTER_WORD, word.substring(beginIndex));
		}
		if (DICTIONARY.inDictionary(word.substring(beginIndex) + nextWord)) {
			return new Pair<WordType, String>(WordType.MASTER_WORD, word.substring(beginIndex) + nextWord);
		}
		return new Pair<WordType, String>(WordType.MASTER_WORD, "");
	}
	
	private static Pair<WordType, String> checkPeople(String word, String nextWord, String firstName){
		if (DICTIONARY.inDictionary(firstName + word.substring(1) + nextWord)) {
			return new Pair<WordType, String>(WordType.PEOPLE, firstName + word.substring(1) + nextWord);
		}
		if (DICTIONARY.inDictionary(firstName + word.substring(1)) && !DICTIONARY.inDictionary(word)) {
			return new Pair<WordType, String>(WordType.PEOPLE, firstName + word.substring(1));
		}
		if (DICTIONARY.inDictionary(firstName + word) && !DICTIONARY.inDictionary(word)) {
			return new Pair<WordType, String>(WordType.PEOPLE, firstName + word);
		}
		if (DICTIONARY.inDictionary(word + nextWord)) {
			return new Pair<WordType, String>(WordType.PEOPLE, word + nextWord);
		}
		
		if (word.length() > 1 && DICTIONARY.inDictionary(word.substring(0, word.length() - 1))
				&& !DICTIONARY.inDictionary(word)) {
			return new Pair<WordType, String>(WordType.PEOPLE, word.substring(0, word.length() - 1));
		}
		if (word.length() > 1 && DICTIONARY.inDictionary(word.substring(1, word.length()) + nextWord)) {
			return new Pair<WordType, String>(WordType.PEOPLE, word.substring(1, word.length()) + nextWord);
		}
		
		if (word.length() > 1 && DICTIONARY.inDictionary(word.substring(0, 2)) && !DICTIONARY.inDictionary(word)) {
			return new Pair<WordType, String>(WordType.PEOPLE, word.substring(0, 2));
		}
		if (word.length() > 2 && DICTIONARY.inDictionary(word.substring(0, 3)) && !DICTIONARY.inDictionary(word)) {
			return new Pair<WordType, String>(WordType.PEOPLE, word.substring(0, 3));
		}
		
		if (nextWord != null && DICTIONARY.inDictionary(word + nextWord.substring(0, 1))) {
			return new Pair<WordType, String>(WordType.PEOPLE, word + nextWord.substring(0, 1));
		}
		return null;
	}

	private static boolean wordInList(String word, String[] wordFilterList) {
		for (String wordFilter : wordFilterList) {
			if (wordFilter.equals(word)) {
				return true;
			}
		}
		return false;
	}

	private static boolean wordEndsInList(String word, String[] wordFilterList) {
		for (String wordFilter : wordFilterList) {
			if (word.endsWith(wordFilter)) {
				return true;
			}
		}
		return false;
	}
}
