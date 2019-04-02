package com.sangbill.kafka.util;

public class LangUtils {
	private final static String LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static String randomWords() {
	    StringBuilder words = new StringBuilder();
	    int length = 1 + (int) (Math.random() * 5);
	    for (int i = 0; i < length; i++) {
	    	words.append(randomWord()+" ");
	    }
		return words.toString();
	}
	
	/**
	 * 随机获取长度为1~10的大小写字母混杂的“单词”
	 */
	private static String randomWord() {		
		StringBuilder word = new StringBuilder();
	    int length = 1 + (int) (Math.random() * 10);
	    for (int i = 0; i < length; i++) {
	    	int index = (int) (Math.random() * LETTERS.length());
	        word.append(LETTERS.charAt(index));
	    }
	    return word.toString();
	}
	
	public static void main(String[] args) {
		for(;;){
			System.out.println(randomWords());
		}
	}
}
