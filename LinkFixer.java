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

public class LinkFixer {
    public static void main(String[] args) throws IOException{
        //Path fileName = Path.of(args[0]);
        // Getting contents of file
        Path fileName = Path.of("toFix.html");
	    List<String> contents = Files.readAllLines(fileName);
        System.out.println(contents);
    }
    
    // public static ArrayList<String> getLinks(String[] contents) {
        
    // }

}
