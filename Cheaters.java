/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Michael
 */
public class Cheaters {
    
    File dir = new File(System.getProperty("user.dir") + "\\sm_doc_set\\sm_doc_set");
    File[] directoryListing = dir.listFiles();
    static Map<String, String> files = new HashMap<>();
    static Map<String, ArrayList<String>> nGramWords = new HashMap<>();
    int i = 0;
    
    public void cleanFile() throws IOException {
        for (File child : directoryListing) {
            i++;
            BufferedReader in = (new BufferedReader(new FileReader(child)));
            String line;
            String processedLine="";
            while ((line = in.readLine()) != null) {
                processedLine += line.replaceAll("[^a-zA-Z0-9\' ]", "").toLowerCase().replaceAll("( )+", " ");
            }
            
            files.put(child.getName(), processedLine);
            //System.out.println(processedLine);
            
        }
    }
    
    public void createNGramMap(int N){
        String[] wordList;
        String consecutiveWords;
        String cleanedDoc;
        ArrayList<String> mapValues;
        for(String file : files.keySet()){
            cleanedDoc = files.get(file);
            wordList = cleanedDoc.split(" ");
            mapValues = new ArrayList<>();
            for(int i = 0; i < (wordList.length - N); i++){
                consecutiveWords = "";
                for(int j = i; j < i + N; j++){
                    consecutiveWords += wordList[j] + " ";
                }
                if(nGramWords.containsKey(consecutiveWords)){
                    mapValues = nGramWords.get(consecutiveWords);
                }
                if(!mapValues.contains(file)){
                    mapValues.add(file);
                }
                nGramWords.put(consecutiveWords, mapValues);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        Cheaters cheat = new Cheaters();
        cheat.cleanFile();
        cheat.i++;
        cheat.createNGramMap(6);
        System.out.println("finished!");
//        System.out.println("hello");
//        System.out.println(System.getProperty("user.dir") + "\\sm_doc_set\\sm_doc_set");
    }
    
    
    
}
