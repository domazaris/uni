
// TestHash
// Expects an input file where each line of input includes the following:
//     p/g  int String
//
// where either "p" or "g" indicates a put() or get() operation
// on a hash table, <int> is the key and String is the data.
// 
// Arguments to this program are three-fold: an integer specifying the
// initial size of the open-addressing hash table, a string either "L" or "K"
// indicating whether collision resolution is via linear-probing or
// key-offset, respectively, and third is the name of an input file
// with each line having the format as given above.
//
 
import java.io.FileReader;
import java.io.BufferedReader;

public class TestHash{


  public static void main(String [] args){
    if(args.length != 3){
      System.err.println("Usage: java TestHash <int size> <String type> <String filename>");
      return;
    }
    try{

      int size = Integer.parseInt(args[0]);
      String type = args[1];
      BufferedReader br = new BufferedReader(new FileReader(args[2]));
      
      if(size<1 || !(type.matches("[LK]"))){
	System.err.println("Invalid arguments ... aborting.");
	return;
      }

      Hashtable ht = new Hashtable(size, type);
      int count=0; /// Count lines of input

      String s=br.readLine();
      String [] token;

      while(s!=null){
	count++;
        token = s.split("\\s+"); // split on whitespace
	//1st token is operation, 2nd is integer key, 3rd is a
	//one token string as the data for the table
	if(token.length == 3 && token[0].matches("p") && token[1].matches("[0-9]+"))
	  ht.put(Integer.parseInt(token[1]),token[2]);
	else if(token.length == 2 && token[0].matches("g") && token[1].matches("[0-9]+"))
	  ht.get(Integer.parseInt(token[1]));
	else
	  System.err.println("Malformed input detected in file .. ignoring this line");

	// Every ten lines, output performance 
	if(count%10==0) System.out.println("Probes per key after "+count+" accesses: "+ht.performance());

	if(ht.load()>0.80){
	  System.out.println("Excess load on hashtable .... aborting: ");
	  return;
	}
	s=br.readLine();
      }
    }catch(Exception e){
      System.err.println("Data error: "+e + " .... aborting!");
    }
  }
}
