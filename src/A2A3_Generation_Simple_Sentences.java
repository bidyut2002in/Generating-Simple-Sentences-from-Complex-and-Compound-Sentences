package SimpleSentence;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;

class Dependency1 {
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

public class A2A3_Generation_Simple_Sentences {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		BufferedReader in = new BufferedReader(new FileReader("D:/4.txt"));
		PrintWriter out = new PrintWriter(new FileWriter("D:/5.txt"));
		Set<Dependency1> d = new LinkedHashSet<Dependency1>();
		Map<Integer, String> d_map = new TreeMap<Integer, String>();
		int[][] subj_matrix = new int[10000][2];
		int[][] adjacency_matrix = new int[10000][2];
		Set<Integer> sentence_array = new TreeSet<Integer>();
		Queue<Integer> q = new LinkedList<Integer>();
		String line;
		int count_line=1;
		int flag=0;
		int count_dep = 0;
		int count_simple=0;
		int count_compound=0;
		int count_compound_complex=0;
		int count_nsubj=0;
		int count=0;
		int c=0;
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
						// System.out.println(s);
						// System.out.println(s.size());
					Dependency1 obj = new Dependency1();
					obj.get(s.get(0), s.get(1), Integer.parseInt(s.get(2)), s.get(3), Integer.parseInt(s.get(4)));
					// obj.display();
					if(s.get(0).equalsIgnoreCase("cc")){flag=1;}
					if (s.get(0).equalsIgnoreCase("nsubj")||s.get(0).equalsIgnoreCase("nsubjpass")||s.get(0).equalsIgnoreCase("csubj")){count_nsubj++;}
		/*			if (!s.get(0).equalsIgnoreCase("cc") && !s.get(0).equalsIgnoreCase("advcl")
							&& !s.get(0).equalsIgnoreCase("mark") && !s.get(0).equalsIgnoreCase("ccomp")
							&& !s.get(0).equalsIgnoreCase("dep") && !s.get(0).equalsIgnoreCase("appos")
							&& !s.get(0).equalsIgnoreCase("parataxis") && !s.get(0).startsWith("conj")
							&& !s.get(0).startsWith("ref") && !s.get(0).startsWith("acl")) {			*/
						// System.out.println(s[0]);
						d.add(obj);
						d_map.put(Integer.parseInt(s.get(2)), s.get(1));
						d_map.put(Integer.parseInt(s.get(4)), s.get(3));
					} // end if
		/*		} // end if																				*/
				else {
					break;
				}
			} // end while
			
					
			if(count_dep!=0){
			System.out.println("Line Number: " + count_line++);
			System.out.println("No.of Subjects: " + count_nsubj);
			System.out.println("No.of Dependencies: " + count_dep);
			}
			
			//if(count_nsubj==1){count_simple++;}
			//if(count_nsubj>1 && flag==1) {count_compound++;}
			//if(count_nsubj>1) {count_compound_complex++;}
			
			if(count_nsubj>0){ 	// if(5)  
			// System.out.println(d_map);
			// Subject Matrix (nsubj)
			int x = 0;
			for (Dependency1 object : d) {
				if (object.type.equalsIgnoreCase("nsubj") || object.type.equalsIgnoreCase("nsubjpass")|| object.type.equalsIgnoreCase("csubj")) {
					// object.display();
					if (object.v2 > object.v1) {
						subj_matrix[x][0] = object.v1;
						subj_matrix[x][1] = object.v2;
					} else {
						subj_matrix[x][0] = object.v2;
						subj_matrix[x][1] = object.v1;
					}
					x = x + 1;
				}
			} // end for
			// Output of Subject Matrix
			/*
			 * System.out.println("Subject(nsubj/nsubjpass): "); for(int
			 * i=0;i<x;i++){ for(int j=0;j<2;j++) {
			 * 
			 * System.out.print(subj_matrix[i][j]+" "); } System.out.println();
			 * }
			 */

				// Dependency1 Matrix (!nsubj)
				int y = 0;
				for (Dependency1 object : d) {
					if (!object.type.equalsIgnoreCase("nsubj")|| !object.type.equalsIgnoreCase("nsubjpass")|| !object.type.equalsIgnoreCase("csubj")) {
						//if (!object.type.equalsIgnoreCase("nsubjpass")) {
							// object.display();
							adjacency_matrix[y][0] = object.v1;
							adjacency_matrix[y][1] = object.v2;
							y = y + 1;
						//} // end if
					} // end if
				} // end for
				// Output of Dependency1 Matrix
				/*
				 * System.out.println("Not Subject Dependency1: "); for(int
				 * i=0;i<y;i++){ for(int j=0;j<2;j++) {
				 * System.out.print(adjacency_matrix[i][j]+" "); }
				 * System.out.println(); }
				 */
				
				 // Sentence TreeSet
				 for (int p = 0; p < x; p++) {
					for (int i = 0; i < y; i++) {
						if (subj_matrix[p][1] == adjacency_matrix[i][0])
							q.add(adjacency_matrix[i][1]);
						if (subj_matrix[p][0] == adjacency_matrix[i][0])
							q.add(adjacency_matrix[i][1]);
					/*
						 * if(subj_matrix[p][0]==adjacency_matrix[i][1])
						 * q.add(adjacency_matrix[i][0]);
						 * if(subj_matrix[p][1]==adjacency_matrix[i][1])
						 * q.add(adjacency_matrix[i][0]);
						 */
					} // end for
						// System.out.println(q);
					sentence_array.add(subj_matrix[p][0]);
					sentence_array.add(subj_matrix[p][1]);
					// System.out.println(subj_matrix[p][0]+"
					// "+subj_matrix[p][1]);
					while (!q.isEmpty()) {
						int search = q.element();
						// System.out.println(search);
						sentence_array.add(search);
						for (int i = 0; i < y; i++) {
							if (search == adjacency_matrix[i][0])
								q.add(adjacency_matrix[i][1]);
						}
						// System.out.println(q);
						q.remove();
					} // end while
					// Output of Sentence TreeSet
					System.out.println(sentence_array);
					count=count+1;
					
					if(count_nsubj==1 /*&& flag==0*/)  {	
/****************** Change the condition for Output:(Simple/Compound/Compound_Complex) ************/
					Iterator<Integer> it = sentence_array.iterator();
					while (it.hasNext()) {
						Integer inn = it.next();
						out.print(d_map.get(inn) + " ");
						// System.out.print(d_map.get(inn)+" ");
					}
					out.print(".");
					out.println();
					// System.out.println();
					c=c+1;
					}//end if
					
				q.clear();
				sentence_array.clear();
				d.clear();
				} // end for
		
		}// end if (5)
		else{
		d.clear();
		}
		flag=0;	
		count_dep = 0;
		count_nsubj=0;
		} while (line != null); // end of do-while
		//System.out.println("Total number of Sentences are: "+(count_simple+count_compound_complex));
		//System.out.println("Number of Simple Sentences are: "+count_simple);
		//System.out.println("Number of Compound Sentences are: "+count_compound);
		//System.out.println("Number of Complex Sentences are: "+(count_compound_complex-count_compound));
		//System.out.println("Total Complex and Compound Sentences are: "+count_compound_complex);
		System.out.println("No. of Sentences="+(count_line-1));
		System.out.println("No. of Simple Sentences(Existing+Generated)="+count);
		System.out.println("No. of Sentence in the Output File="+c);
		in.close();
		out.close();
	}
}
