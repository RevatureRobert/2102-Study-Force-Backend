package com.revature.studyforce.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProfanityFilter loads a set of pre-defined words that are not allowed into a buffer.
 * It has helper methods to determine if a given input contains profanity.
 * @author Brandon Pinkerton
 */
@Component
public class ProfanityFilter {
    static Map<String, String[]> words = new HashMap<>();

    private static String url = "https://docs.google.com/spreadsheets/d/12Br4zV7Xmk3OQp2oiGrMO3T4lWdJtqNOrmPfcipTSLQ";
    static int largestWordLength = 0;

    public ProfanityFilter(){
        loadConfigs();
    }

    /**
     * loadConfigs is called by the constructor and loads the buffer with pre-defined words that we disallow
     */
    public static void loadConfigs() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url + "/export?format=csv").openConnection().getInputStream()));
            String line = "";
            while((line = reader.readLine()) != null) {
                String[] content = null;
                try {
                    content = line.split(",");
                    if(content.length == 0) {
                        continue;
                    }
                    String word = content[0];
                    String[] wordsToIgnore = new String[]{};
                    if(content.length > 1) {
                        wordsToIgnore = content[1].split("_");
                    }

                    if(word.length() > largestWordLength) {
                        largestWordLength = word.length();
                    }
                    words.put(word.replaceAll(" ", ""), wordsToIgnore);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Iterates over a String input and checks whether a cuss word was found in a list, then checks if the word should be ignored (e.g. bass contains the word *ss).
     * @param input The input string to be checked
     * @return A list of bad words that were found within the input
     */

    public List<String> badWordsFound(String input) {
        if(input == null) {
            return new ArrayList<>();
        }

        input = convertLeetSpeak(input);
        List<String> badWords = new ArrayList<>();
        input = input.toLowerCase().replaceAll("[^a-zA-Z]", "");

        // iterate over each letter in the word
        for(int start = 0; start < input.length(); start++) {
            // from each letter, keep going to find bad words until either the end of the sentence is reached, or the max word length is reached.
            for(int offset = 1; offset < (input.length()+1 - start) && offset < largestWordLength; offset++)  {
                String wordToCheck = input.substring(start, start + offset);
                if(words.containsKey(wordToCheck)) {
                    // for example, if you want to say the word bass, that should be possible.
                    String[] ignoreCheck = words.get(wordToCheck);
                    boolean ignore = false;
                    for(int s = 0; s < ignoreCheck.length; s++ ) {
                        if(input.contains(ignoreCheck[s])) {
                            ignore = true;
                            break;
                        }
                    }
                    if(!ignore) {
                        badWords.add(wordToCheck);
                    }
                }
            }
        }
        return badWords;
    }

    /**
     *
     * @param input The input to convert from leetspeak.
     * @return A string with the leetspeak replace.
     */
    private String convertLeetSpeak(String input){
        input = input.replaceAll("1","i");
        input = input.replaceAll("!","i");
        input = input.replaceAll("3","e");
        input = input.replaceAll("4","a");
        input = input.replaceAll("@","a");
        input = input.replaceAll("5","s");
        input = input.replaceAll("7","t");
        input = input.replaceAll("0","o");
        input = input.replaceAll("9","g");
        return input;
    }

    public boolean isBad(String input){
        return !badWordsFound(input).isEmpty();
    }
}
