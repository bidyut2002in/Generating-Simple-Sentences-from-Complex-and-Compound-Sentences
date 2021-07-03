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
class Dependency 
{
	String type;
	String w1;
	int v1;
	String w2;
	int v2;
	void get(String t,String m,int n,String o,int p)
	{
		type=t;
		w1=m;
		v1=n;
		w2=o;
		v2=p;
	}
	void display()
	{
		System.out.println(type+" "+w1+" "+v1+" "+w2+" "+v2);
	}
	
}
public class A2B1_OneComplexCompoundToSimpleSentence {

	public static void main(String[] args) throws IOException {
		// John, who was the CEO of a company, played golf.
		
		// nsubj CEO 6  John 1  
		// nsubj played 11  John 1  
		// cop CEO 6  was 4  
		// det CEO 6  the 5  
		// rcmod John 1  CEO 6  
		// det company 9  a 8  
		// prep CEO 6  company 9  
		// root ROOT 0  played 11  
		// dobj played 11  golf 12 	
		
		BufferedReader in=new BufferedReader(new FileReader("D:/4.txt"));
		
		// John was the CEO a company 
		// John played golf 
		
		PrintWriter out = new PrintWriter(new FileWriter("D:/7.txt"));
		
			Set<Dependency> d=new LinkedHashSet<Dependency>();
			Map<Integer,String> d_map= new TreeMap<Integer,String>();
			int [][] subj_matrix = new int [10000][2]; 
			int [][] adjacency_matrix = new int [10000][2];
			Set<Integer> sentence_array=new TreeSet<Integer>();
			Queue<Integer> q = new LinkedList<Integer>();
			String line;
			int count=0;
			while((line=in.readLine())!=null){
				if(line.length()>0){
				count=count+1;
				StringTokenizer tokenizer=new StringTokenizer(line);
				ArrayList<String> s = new ArrayList<String>();
				while(tokenizer.hasMoreTokens())
		    	{
		    		String str=tokenizer.nextToken();
		    		s.add(str);
		    	} // end while
				//System.out.println(s);
				//System.out.println(s.size());
				Dependency obj=new Dependency();
				obj.get(s.get(0),s.get(1),Integer.parseInt(s.get(2)), s.get(3),Integer.parseInt(s.get(4)));
				//obj.display();
				if(!s.get(0).equalsIgnoreCase("cc")&&!s.get(0).equalsIgnoreCase("cc")&&!s.get(0).equalsIgnoreCase("advcl")&&!s.get(0).equalsIgnoreCase("mark")&&!s.get(0).equalsIgnoreCase("ccomp")&&!s.get(0).equalsIgnoreCase("appos")&&!s.get(0).equalsIgnoreCase("dep")&&!s.get(0).startsWith("conj")&&!s.get(0).startsWith("ref")&&!s.get(0).startsWith("acl")&&!s.get(0).startsWith("parataxis")&&!s.get(0).startsWith("rcmod")){
				//if(!s.get(0).equalsIgnoreCase("appos")){
					//if(!s.get(0).equalsIgnoreCase("conj")){
						//System.out.println(s[0]);
						d.add(obj);	
						d_map.put(Integer.parseInt(s.get(2)), s.get(1));
						d_map.put(Integer.parseInt(s.get(4)), s.get(3));
					}//end if
				}//end if
				}//end while
			System.out.println("No.of Lines of Dependency: "+count);
			//System.out.println(d_map);
			
			//Subject Matrix (nsubj)
			int x=0;
			for(Dependency object : d) {
				if(object.type.equalsIgnoreCase("nsubj")||object.type.equalsIgnoreCase("nsubjpass")){
				//object.display();
			    if(object.v2>object.v1){
			   	subj_matrix[x][0]=object.v1;
			   	subj_matrix[x][1]=object.v2;
			    }
			    else
			    {
			    	subj_matrix[x][0]=object.v2;
			    	subj_matrix[x][1]=object.v1;	
			    }
				x=x+1;
				}	
			}//end for
			
			//Output of Subject Matrix
			/* System.out.println("Subject(nsubj/nsubjpass): ");
			 for(int i=0;i<x;i++){
				for(int j=0;j<2;j++)
				{
				
				System.out.print(subj_matrix[i][j]+" ");
				}
				System.out.println();
				} */	
			
			//Dependency Matrix	(!nsubj)
				int y=0;
			    for(Dependency object : d) {
			    	if(!object.type.equalsIgnoreCase("nsubj")){
			    		if(!object.type.equalsIgnoreCase("nsubjpass")){
			    			//object.display();
			    			adjacency_matrix[y][0]=object.v1;
			    			adjacency_matrix[y][1]=object.v2;
			    			y=y+1;
			    		}//end if
			    	}//end if								    	
				}//end for			    
			  	      			
		   //Output of Dependency Matrix	
		     /* System.out.println("Not Subject Dependency: ");
			   for(int i=0;i<y;i++){
				for(int j=0;j<2;j++)
				{
				System.out.print(adjacency_matrix[i][j]+" ");
				}
				System.out.println();
				} */
			 
			  // Sentence TreeSet
			  for(int p=0;p<x;p++){
				  for(int i=0;i<y;i++)
				    {				  
						if(subj_matrix[p][1]==adjacency_matrix[i][0])
							q.add(adjacency_matrix[i][1]);
						if(subj_matrix[p][0]==adjacency_matrix[i][0]) 
							q.add(adjacency_matrix[i][1]);
						/*if(subj_matrix[p][1]==adjacency_matrix[i][1]) 
							q.add(adjacency_matrix[i][0]);
						if(subj_matrix[p][0]==adjacency_matrix[i][1]) 
							q.add(adjacency_matrix[i][0]);*/
					}//end for
				  System.out.println("Dependencies:"+q);
				  sentence_array.add(subj_matrix[p][0]);
				  sentence_array.add(subj_matrix[p][1]);
				  //System.out.println(subj_matrix[p][0]+" "+subj_matrix[p][1]);
				  System.out.println("Head:"+sentence_array);
				  
				  while(!q.isEmpty())
				  {
				  int search=q.element();
				  //System.out.println(search);
				  sentence_array.add(search);
				  for(int i=0;i<y;i++){ 
					  if(search==adjacency_matrix[i][0])
					    q.add(adjacency_matrix[i][1]); 
					   	}
				   //System.out.println(q);
				   q.remove();
				  }//end while
			//Output of Sentence TreeSet	
			System.out.println("Sentence:"+sentence_array);  
			    
			Iterator<Integer> it = sentence_array.iterator();
			while (it.hasNext()){
					 Integer inn = it.next();
					 out.print(d_map.get(inn)+" ");
					 //System.out.print(d_map.get(inn)+" ");
			}
			out.println();
			//System.out.println();
			q.clear();
			sentence_array.clear();
			d.clear();
			}//end for
			in.close();
			out.close();
		}
}	

	



