/*
* Jeannie Kim // January 2022
* Program that parses an HTML file containing 
* the authors + incorrect links (copy-pasted from Wordpress)
* and returns a copy of the file, but with FIXED LINKS. wOO
* (https://themq.org/2020-2021-staff/)
*/

// Credit to Joe Politz's CSE 15L lab on Markdown Parsing: 
//  https://ucsd-cse15l-w22.github.io/week/week3/

// File reading code from 
//  https://howtodoinjava.com/java/io/java-read-file-to-string-examples/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays; 
import java.text.Normalizer;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class LinkFixer {
    public static void main(String[] args) throws IOException{
        //Path fileName = Path.of(args[0]);
        
        // Getting contents of file
        String file = "toFix.html";
        Path fileName = Path.of(file);
	    List<String> contents = Files.readAllLines(fileName);

        List<String> fixedContents = contents;
                
        for (int i = 0; i < contents.size(); i++){
            // each line
            String line = contents.get(i);
            if (line.length() > 1){
               if (line.charAt(1) == 'p'){
                   // replace line with fixed line
                    contents.set(i, processLine(line));
                } 
            }
        }
    
        // Write a new file
        BufferedWriter writer = new BufferedWriter(new FileWriter("FIXED.html"));
        for (String line : fixedContents){
            writer.write(line);
            writer.newLine();
        }
        writer.close();
        System.out.println(fixedContents);
    }
    
    public static String processLine(String line) {
       
        String oldLink = "";
        String newLink = "";

        // Finding + processing name
        
        int nameStartInd = 0;
        int nameEndInd = line.length()-8; // </a></p>
        
        // traversing backwards from EndIndex
        for (int k = nameEndInd; k > 0; k--){
            // starts after first >
            if (line.charAt(k) == '>'){
                nameStartInd = k+1;
                break;
            }
        }
        String name = line.substring(nameStartInd, nameEndInd);
        newLink = toNewLink(name);

        // Finding + processing (old, maybe wrong) link
        int linkStartInd = line.indexOf("data-id=")+9;
        int linkEndInd = line.length();
        for (int j = linkStartInd; j < line.length(); j++){
            if (line.charAt(j) == '>'){
                linkEndInd = j-1;
                break;
            }
        }
        oldLink = line.substring(linkStartInd, linkEndInd);
        
        System.out.println(oldLink + " " + newLink);
        
        String fixedLine = line.replace(oldLink, newLink);

        return fixedLine;
    }

    //https://stackoverflow.com/questions/3322152/is-there-a-way-to-get-rid-of-accents-and-convert-a-whole-string-to-regular-lette/15191508
    public static String toNewLink(String name){
        // Get rid of accents
        String normaName = Normalizer.normalize(name, Normalizer.Form.NFD);
        normaName = normaName.replaceAll("\\p{M}", "");
        
        String[] nameSplit = normaName.toLowerCase().split(" ");
        String username = nameSplit[0].charAt(0) + nameSplit[1];
        return "https://themq.org/author/" + username + "/";
    }

}
