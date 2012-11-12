import java.util.List;

/**
 * Knuth-Morris-Pratt Search Algorithm
 * 
 * */
public class KMPSearch implements SearchAlgorithm{

	@Override
	public Object doSearch(List<String> input, String string) {

		return null;
	}

	public int searchKMP(char[] w, char[] s, int[] t)
	 {
	  int m=0;
	  int i=0;
	  while( ((m + i) < s.length) && (i<w.length) )
	  {
	   if(s[m+i] == w[i]) i++;
	   else
	   {
	    m += i - t[i];
	    if (i > 0) i = t[i];
	    i++;
	   }
	  }
	  if(i == w.length) return m;
	  else return -1;
	 }
}
