package SimpleSentence;
/*
 * Step1: Remove Only Special Characters 
 */

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.io.*;

public class RemoveHyphenWithUnderscore{
	/**
	 * @param args
	 */
	static FileReader FileIn;
	static PrintWriter FileOut;
	
	public static void main(String[] args) throws IOException {
		List<Character>chs=new LinkedList<Character>();
		
		chs.add('-');
		//chs.add('(');
		//chs.add(')');
						
		FileIn = new FileReader("D:/1.txt");
		BufferedReader br=new BufferedReader(FileIn);
		FileOut=new PrintWriter(new FileWriter("D:/2.txt"));
		int str;
		while((str=br.read())!=-1){
			if((char)str=='\n'){FileOut.println();continue;}
			else if((char)str==','){continue;}
			else if((char)str=='\''){continue;}
			if(!chs.contains((char)str)){
				FileOut.print((char)str);
				}
				else{
					if((char)str=='-'){FileOut.print('_');}
					//if((char)str=='('){FileOut.print("LRB_");}
					//if((char)str==')'){FileOut.print("_RRB");}
					}
		} //end while
		FileIn.close();
		FileOut.close();
	}
}
