package wow;
import java.io.*;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;



public class FindKeyWordsInFile {
	
	// global variables 
	public static  AVLTree <String, Integer> tree = new AVLTree(); // tree with most frequent words
	public static  AVLTree <String, Integer> tree2 = new AVLTree(); // tree of the frequent words
	public static  AVLTree <String, Integer> tree3 = new AVLTree(); // tree without most frequent words
	public static int total = 0; 
	
	// method that reads the file containing all the words and puts it into a AVL Tree	
	FindKeyWordsInFile(String s){
			
		FileReader fr;
		String word_check = "";
		
		try {
			fr = new FileReader(s);
			 int i; 
		     while ((i = fr.read()) != -1)
		    	 	if (i == 10 || i == 32 || i == 46 || i == 58) {
		    	 		if (tree.get(word_check) == null) {
		    	 			
		    				tree.put(word_check, 1);
		    			}
		    	 		else {
		    	 			int value = (int)tree.get(word_check) + 1;
		    	 			
		    	 			tree.put(word_check,value );
		    	 		}
		    	 		total ++;
		    	 		word_check = "";
		    	 	}
		    	 	else {
		    	 		word_check = word_check + (char)i;
		    	 	}
		              
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	// method that returns an object array with the values of the keys from biggest to smallest
	public static Object[] Search() {
		
		PriorityQueue<String> pq = new PriorityQueue<String>(total);
		// uses a method from AVLTree.java that I created that will give the opposite of the priority queue (max heap)
		pq = tree.traversal(total);
		
		Object[] freaquant = pq.toArray(); 
		
		return freaquant;

	}
	// method that finds all the  words in the MostFrequent file and puts them into a tree
	// Then takes the k most frequent words from the object array without the ones in the most frequant file 
	// and adds them to another tree
	public static void Check(String s , int k, Object[] freaquant) {
		
		FileReader fr;
		String word_check = "";
		// puts most frequent file into tree
		try {
			fr = new FileReader(s);
			 int i; 
		     while ((i = fr.read()) != -1)
		    	 	if (i == 10 ) {
		    	 		tree2.put(word_check, 0);
		    	 		word_check = "";
		    	 	}
		    	 	else {
		    	 		word_check = word_check + (char)i;
		    	 	}
		              
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// takes k most frequant in the array without the ones in the file 
		for(int i = 0; i <= k ; i++) {
			String word = (String)freaquant[i];
			String word2 = word.substring(2); // to get the word part  
			if (word2.equals("I") || word2.equals("i")) {
				word2 = word2.toUpperCase(); // because I alone is always capital 
			}
			
			else {
				word2 = word2.toLowerCase(); //because everything on the most freaquent has no capitals other then I 
			}
			char value = word.charAt(0); // to get the number part of the string 
			
			if (tree2.get(word2) == null) {
				tree3.put(word2, Integer.parseInt(String.valueOf(value)));
			}
		}
		
	}
	
	public static void main(String[] args) {
		// make sure there's 3 arguments 
		 if (args.length != 3) {
	            System.err.println("Usage: java FindKeyWordsInFile k file.txt MostFrequentEnglishWords.txt");
	            System.exit(1);
	        }
		 
		 int k = Integer.parseInt(args[0]);
	     String inputFileName = args[1];
	     String englishWordsFileName = args[2];
		
		FindKeyWordsInFile Find = new FindKeyWordsInFile(inputFileName);
		
		Object[] freaquant = Search();
		
		Check(englishWordsFileName,k,freaquant);
		// print the list of words 
		tree3.inOrderTraversal();
		
	}
}
