package SimpleSentence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

class Dependency2 {
	String type;
	String w1;
	int v1;
	String w2;
	int v2;

	void get(String t, String m, int n, String o, int p) {
		type = t;
		w1 = m;
		v1 = n;
		w2 = o;
		v2 = p;
	}

	void display() {
		System.out.println(type + " " + w1 + " " + v1 + " " + w2 + " " + v2);
	}

}

public class SentencesCategorization{

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader in = new BufferedReader(new FileReader("D:/3.txt"));
		PrintWriter out1 = new PrintWriter(new FileWriter("D:/5.SimpleSentences.txt"));
		PrintWriter out2 = new PrintWriter(new FileWriter("D:/6.CompoundSentences.txt"));
		PrintWriter out3 = new PrintWriter(new FileWriter("D:/7.ComplexSentences.txt"));
		PrintWriter out4 = new PrintWriter(new FileWriter("D:/8.UnCategorizedSentences.txt"));
		Map<Integer, String> d_map = new TreeMap<Integer, String>();
		String line="";
		int count_line=0;
		int flag=0;
		int count_dep = 0;
		int count_simple=0;
		int count_compound=0;
		int count_uncatagorized=0;
		//int count_compound_complex=0;
		int count_complex=0;
		int count_nsubj=0;
		do {
			while ((line = in.readLine()) != null) {
				// System.out.println(line.length());
				if (line.length() > 0) {
					count_dep = count_dep + 1;
					StringTokenizer tokenizer = new StringTokenizer(line);
					ArrayList<String> s = new ArrayList<String>();
					while (tokenizer.hasMoreTokens()) {
						String str = tokenizer.nextToken();
						s.add(str);
					} // end while
						 //System.out.println(s);
						// System.out.println(s.size());
					Dependency2 obj = new Dependency2();
					obj.get(s.get(0), s.get(1), Integer.parseInt(s.get(2)), s.get(3), Integer.parseInt(s.get(4)));
					// obj.display();
					if(s.get(0).equalsIgnoreCase("cc") || s.get(0).equalsIgnoreCase("conj")){flag=1;}
					//Limitation of StanfordParser for conjunction. "For" and "So" not treated as conjunction
					//if(s.get(1).equalsIgnoreCase("for")|| s.get(3).equalsIgnoreCase("for")){flag=1;}
					//if(s.get(1).equalsIgnoreCase("so") || s.get(3).equalsIgnoreCase("so")){flag=1;}
							
					//if(s.get(0).equalsIgnoreCase("ccomp") || s.get(0).equalsIgnoreCase("mark")||s.get(0).equalsIgnoreCase("xcomp")){flag=2;}
					if (s.get(0).equalsIgnoreCase("csubj")||s.get(0).equalsIgnoreCase("csubjpass")||s.get(0).equalsIgnoreCase("nsubj")||s.get(0).equalsIgnoreCase("nsubjpass")){count_nsubj++;}
					if(!s.get(0).equalsIgnoreCase("root")){
					d_map.put(Integer.parseInt(s.get(2)), s.get(1));
					d_map.put(Integer.parseInt(s.get(4)), s.get(3));
					}
				} // end if
				else {
					break;
				}
			} // end while
			
			if(count_dep!=0){
			count_line=count_line+1;
			System.out.println("Line Number: " + count_line);
			System.out.println("No.of Subjects: " + count_nsubj);
			System.out.println("No.of Dependencies: " + count_dep);
			}	
			
			String S="";
			int wordCount=0;
			Iterator<String> it = d_map.values().iterator();
			while (it.hasNext()) {
				String W = it.next();
				wordCount=wordCount+1;
				S=S+W+" ";
				//System.out.println(S);
			}
			if(wordCount>0 && wordCount<=25)
			{
			if(count_nsubj==1)  //Rule Fixed for Simple Sentences. subject=0 but verb=1 (simple)
								//For Basic Dependencies: flag=1 When "CC" Present
			{
				count_simple+=1;
				//out1.println("Line Number: " + count_line);
				out1.println(S+".");
			}
			//else if(count_nsubj>1 /* && flag==1 */ ) 
			//{
			//	count_compound+=1;
			//	//out2.println("Line Number: " + count_line);
			//	out2.println(S+".");
			//}
			/*else if(count_nsubj>=1 && flag==2) 
			{
				count_complex+=1;
				//out3.println("Line Number: " + count_line);
				out3.println(S+".");
			}*/
			else if(count_dep>0)
			{
				count_uncatagorized=count_uncatagorized+1;
				out4.println(S+".");
			}
			}
		flag=0;
		count_dep = 0;
		count_nsubj=0;
		d_map.clear();
		} while (line != null); // end of do-while
		
		//System.out.println("Total number of Sentences are: "+(count_simple+count_compound+count_complex));
		System.out.println("Number of Simple Sentences are: "+count_simple);
		//System.out.println("Number of Compound Sentences are: "+count_compound);
		//System.out.println("Number of Complex Sentences are: "+count_complex);
		System.out.println("Number of UnCatagorized Sentences are: "+(count_uncatagorized));
		
		in.close();
		out1.close();
		out2.close();
		out3.close();
		out4.close();
	}
}
