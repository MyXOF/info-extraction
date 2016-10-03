package com.corp.myxof.relation;

import com.corp.myxof.config.InfoExtractionConfig;
import com.corp.myxof.utils.Dictionary;
import com.corp.myxof.utils.Pair;

public class Word {

	private static final Dictionary DICTIONARY = new Dictionary(InfoExtractionConfig.DICTIONARY);
	public static Pair<WordType, String> getWordType(String firstName, String word, String ne, String pos, String nextWord) {
		WordType type = WordType.USELESS_WORD;
		
		if (Word.wordInList(word, InfoExtractionConfig.FAMILY_WORDS)) {
			return new Pair<WordType, String>(WordType.FAMILY_WORD, word);
		}
		
		if (word.length() > 1 && Word.wordInList(word.substring(0,1), InfoExtractionConfig.FAMILY_WORDS) && !Word.wordInList(word, InfoExtractionConfig.APPRENTICE_WORDS)) {
			return new Pair<WordType, String>(WordType.FAMILY_WORD, word);
		}
		
		if (word.length() > 2 && Word.wordInList(word.substring(0,2), InfoExtractionConfig.FAMILY_WORDS) && !Word.wordInList(word, InfoExtractionConfig.APPRENTICE_WORDS)) {
			return new Pair<WordType, String>(WordType.FAMILY_WORD, word);
		}
		
		
		
		if (ne.equals("PERSON") && word.length() > 1) {
			if(DICTIONARY.inDictionary(firstName+word.substring(1))){
				return new Pair<WordType, String>(WordType.PEOPLE, firstName+word.substring(1));
			}
			if(DICTIONARY.inDictionary(firstName+word)){
				return new Pair<WordType, String>(WordType.PEOPLE, firstName+word);
			}
			if(DICTIONARY.inDictionary(firstName+word.substring(1)+nextWord)){
				return new Pair<WordType, String>(WordType.PEOPLE, firstName+word.substring(1)+nextWord);
			}
			if(word.length() > 1 && DICTIONARY.inDictionary(word.substring(0,word.length()-1)) && !DICTIONARY.inDictionary(word)){
				return new Pair<WordType, String>(WordType.PEOPLE, word.substring(0,word.length()-1));
			}
			if(word.length() > 1 && DICTIONARY.inDictionary(word.substring(1,word.length())+nextWord)){
				return new Pair<WordType, String>(WordType.PEOPLE, word.substring(1,word.length())+nextWord);
			}
			return new Pair<WordType, String>(WordType.PEOPLE, word);
		}

		if ((pos.equals("NN") || pos.equals("NR") || pos.equals("AD") || pos.equals("JJ")) && ne.equals("O")){
			if(DICTIONARY.inDictionary(word+nextWord)){
				return new Pair<WordType, String>(WordType.PEOPLE, word + nextWord);
			}
			if(DICTIONARY.inDictionary(firstName+word)){
				return new Pair<WordType, String>(WordType.PEOPLE, firstName+word);
			}
			if(DICTIONARY.inDictionary(firstName+word.substring(1))){
				return new Pair<WordType, String>(WordType.PEOPLE, firstName+word.substring(1));
			}
			if(DICTIONARY.inDictionary(firstName+word.substring(1)+nextWord)){
				return new Pair<WordType, String>(WordType.PEOPLE, firstName+word.substring(1)+nextWord);
			}
			if(word.length() > 1 && DICTIONARY.inDictionary(word.substring(0,word.length()-1)) && !DICTIONARY.inDictionary(word)){
				return new Pair<WordType, String>(WordType.PEOPLE, word.substring(0,word.length()-1));
			}
			if(word.length() > 1 && DICTIONARY.inDictionary(word.substring(1,word.length())+nextWord)){
				return new Pair<WordType, String>(WordType.PEOPLE, word.substring(1,word.length())+nextWord);
			}
			if(DICTIONARY.inDictionary(word)){
				return new Pair<WordType, String>(WordType.PEOPLE, word);
			}
		}

		if (Word.wordInList(word, InfoExtractionConfig.MASTER_WORDS)) {
			return new Pair<WordType, String>(WordType.MASTER_WORD, word);
		}

		if (Word.wordInList(word, InfoExtractionConfig.APPRENTICE_WORDS)) {
			return new Pair<WordType, String>(WordType.APPRENTICE_WORD, word);
		}
		



		if (Word.wordInList(word, InfoExtractionConfig.PARTER_WORDS)) {
			return new Pair<WordType, String>(WordType.PARTER_WORD, word);

		}

		else if (Word.wordInList(word, InfoExtractionConfig.SENTENCE_END)) {
			return new Pair<WordType, String>(WordType.SENTENCE_END, word);
		}

		return new Pair<WordType, String>(type, word);
	}

	public static boolean wordInList(String word, String[] wordFilterList) {
		for (String wordFilter : wordFilterList) {
			if (wordFilter.equals(word)) {
				return true;
			}
		}
		return false;
	}
}
