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

public class A2_RemoveSpecialChars{
	/**
	 * @param args
	 */
	static FileReader FileIn;
	static PrintWriter FileOut;
	
	public static void main(String[] args) throws IOException {
		List<Character>chs=new LinkedList<Character>();
		//chs.add(';');
		chs.add(',');
		//chs.add('[');
		//chs.add(']');
		//chs.add('/');
		//chs.add('\\');
		//chs.add('\n');
		//chs.add('.');   
		chs.add('-');
		//chs.add(':');
		//chs.add('"');
		//chs.add('<');
		//chs.add('>');
		chs.add('(');
		chs.add(')');
		//chs.add('\'');
		//chs.add('\"');
		//chs.add(' ');
		//chs.add('\0');
		//chs.add('+');
		//chs.add('|');
		//chs.add('@');
		//chs.add('?');
		//chs.add('!');
		//chs.add('#');
		//chs.add('=');
		//chs.add('%');
		//chs.add('\b');
		//chs.add('_');
		//chs.add('0');
		//chs.add('1');
		//chs.add('2');
		//chs.add('3');
		//chs.add('4');
		//chs.add('5');
		//chs.add('6');
		//chs.add('7');
		//chs.add('8');
		//chs.add('9');
		//System.out.print("tokens"+chs);
		FileIn = new FileReader("D:/3.txt");
		BufferedReader br=new BufferedReader(FileIn);
		FileOut=new PrintWriter(new FileWriter("D:/4.txt"));
		int str;
		while((str=br.read())!=-1){
			if((char)str=='\n'){FileOut.println();continue;}
			if(!chs.contains((char)str)){
				FileOut.print((char)str);
				}
				else{
					FileOut.print(' ');
				}
		} //end while
		FileIn.close();
		FileOut.close();
	}
}
