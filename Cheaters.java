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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 *
 * @author Michael
 */
public class Cheaters {
    
    File dir = new File(System.getProperty("user.dir") + "\\sm_doc_set\\sm_doc_set");
    File[] directoryListing = dir.listFiles();
    static Map<String, String> files = new HashMap<>();
    static Map<String, ArrayList<String>> nGramWords = new HashMap<>();
    static double[][] similarities;
    static List<String> fileList;
    int numFiles = 0;
    
    public void cleanFile() throws IOException {
        for (File child : directoryListing) {
            numFiles++;
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
    
    public void createSimilarityMatrix(){
        similarities = new double[files.keySet().size()][files.keySet().size()];
        fileList = new ArrayList<>();
        int[] totalOccurences = new int[files.keySet().size()];
        for(String file : files.keySet()){
            fileList.add(file);  
            similarities[fileList.size()-1][fileList.size()-1] = 0;
        }
        for(String s : nGramWords.keySet()){
            for(String f : nGramWords.get(s)){
                similarities[fileList.indexOf(f)][fileList.indexOf(f)] += 1;
            }
            for(int i = 0; i < nGramWords.get(s).size() - 1; i++){
                for(int j = i+1; j < nGramWords.get(s).size(); j++){
                    int file1 = fileList.indexOf(nGramWords.get(s).get(i));
                    int file2 = fileList.indexOf(nGramWords.get(s).get(j));           
                    similarities[file1][file2] += 1;
                    similarities[file2][file1] += 1;                                   
                }
            }
        }
//        for(int i = 0; i < similarities.length; i++){
//            for(int j = 0; j < similarities[0].length; j++){
//                if(j > i){
//                    similarities[i][j] = similarities[i][j] / similarities[i][i];
//                }
//                else if( j < i){
//                    similarities[i][j] = similarities[i][j] / similarities[j][j];
//                }
//            }
//        }       
    }
    /**
     * 
     * @param numberofLines == -1 will output all similarities in the matrix
     */
    public void outputSimilarities(int numberofLines){
        SortedMap<Double, List<String>> similarityPairs = new TreeMap<>();
        for(int i = 0; i < similarities.length; i++){
            for(int j = 0; j < similarities[0].length; j++){
                if(j > i){
                    List<String> pair = new ArrayList<>();
                    pair.add(fileList.get(i));
                    pair.add(fileList.get(j));
                    similarityPairs.put(similarities[i][j], pair);
                }
            }
        }
        int counter = 0;
        for(int i = similarityPairs.keySet().size() - 1; i > -1; i--){
            if(numberofLines == -1 || counter < numberofLines){
                System.out.println(similarityPairs.keySet().toArray()[i] + ": " + similarityPairs.values().toArray()[i].toString());
            }
            counter++;
        }
    }
    public static void main(String[] args) throws IOException {
        Cheaters cheat = new Cheaters();
        cheat.cleanFile();
        cheat.numFiles++;
        cheat.createNGramMap(3);
        cheat.createSimilarityMatrix();
        cheat.outputSimilarities(10);

    }
    
    
    
}
