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

public class LinkFixer {
    public static void main(String[] args) throws IOException{
        //Path fileName = Path.of(args[0]);
        
        // Getting contents of file
        String file = "toFix.html";
        Path fileName = Path.of(file);
	    List<String> contents = Files.readAllLines(fileName);
        String rawContents = Files.readString(fileName);
        ArrayList<String> relevantLines = new ArrayList<>();

        for (String line: contents){
            if (line.length() > 1){
               if (line.charAt(1) == 'p'){
                relevantLines.add(line);
                } 
            }
        }
    
        String[][] bigMaster = processLine(relevantLines)

        // Write a new file

        String fixedContents = rawContents;

        for (String[] replacePair : bigMaster){
            fixedContents.replace(replacePair[0], replacePair[1]);
        }

        System.out.println(fixedContents);
    }
    
    public static String[][] processLine(ArrayList<String> lines) {
       
        String[][] toReturn = new String[lines.size()][2];

        for (int i = 0; i < lines.size(); i++){ // for each line
            String line = lines.get(i);

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
            toReturn[i][0] = toNewLink(name);

            // Finding + processing (old, maybe wrong) link
            int linkStartInd = line.indexOf("data-id=")+8;
            int linkEndInd = line.length();
            for (int j = linkStartInd; j < line.length(); j++){
                if (line.charAt(j) == '>'){
                    linkEndInd = j+1;
                    break;
                }
            }
            String oldLink = line.substring(linkStartInd, linkEndInd);
            toReturn[i][1] = oldLink;
        }

        return toReturn;
    }

    public static String toNewLink(String name){
        String[] nameSplit = name.toLowerCase().split(" ");
        String username = nameSplit[0].charAt(0) + nameSplit[1];
        return "https://themq.org/author/" + username + "/";
    }

}
