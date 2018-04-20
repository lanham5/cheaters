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

/**
 *
 * @author Michael
 */
public class Cheaters {
    
    File dir = new File("C:/Users/Michael/Documents/NetBeansProjects/Assignment7/sm_doc_set/sm_doc_set");
    File[] directoryListing = dir.listFiles();
    int i = 0;
    
    public void cleanFile() throws IOException {
        for (File child : directoryListing) {
        
            i++;
            BufferedReader in = (new BufferedReader(new FileReader(child)));
            String line;
            String processedLine="";
            while ((line = in.readLine()) != null) {
                processedLine = line.replaceAll("[^a-zA-Z0-9]"," ").toLowerCase().replaceAll("( )+", " ");
                System.out.println(processedLine);
            }
            
            System.out.println("\n \n File " + i);
        
        }
    }
    
    
    public static void main(String[] args) throws IOException {
        Cheaters obj = new Cheaters();
        obj.cleanFile();
    }
    
    
    
}
