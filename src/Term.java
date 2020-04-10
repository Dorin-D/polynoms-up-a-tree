import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class Term extends TreeMap<String, Integer> implements Comparable<Term>{
	private static final long serialVersionUID = 313314663102134081L;

	
	public Term(){
		
	}
	
	public Term(final String name, final Integer rank){
		this.put(name, rank);
	}
	
//constructor hard-copying a TreeMap
	public Term(final Term obj){
	//same as constructor above except it takes a Term object as an argument
		for(Map.Entry<String, Integer> entry : obj.entrySet()){
			final String myKey = new String(entry.getKey());
			final Integer myValue = new Integer(entry.getValue());
			this.put(myKey, myValue);
		}
	}	

	@Override
//comparator, prioritate variabile X
	public int compareTo(final Term t1) {
		ArrayList<String> allVars = new ArrayList<String>();
//		for(Map.Entry<String, Integer> entry : this.entrySet()){
//			allVars.add(entry.getKey());
//		}
//		for(Map.Entry<String, Integer> entry : t1.entrySet()){
//			if(!allVars.contains(entry.getKey())){
//				allVars.add(entry.getKey());
//			}
//		}
//		Collections.sort(allVars);
		
		
		final Iterator<Map.Entry<String, Integer>> it = this.entrySet().iterator();
		final Iterator<Map.Entry<String, Integer>> it1 = t1.entrySet().iterator();
		String v, v1;
		
		if(it1.hasNext()){
			v1 = it1.next().getKey();
		} else{ //if t1 is empty then we ensure it will never be smaller than v
			v1 = String.valueOf(Character.MAX_VALUE);
		}
		
		//merging the list of variables of the two terms 
		while(it.hasNext()){
			v = it.next().getKey();
			//warning; never set v to MAX_VALUE
			while(v.compareTo(v1) > 0){
//				System.out.println(v1);
				allVars.add(v1);
				if(it1.hasNext()){
					v1 = it1.next().getKey();
				}
				else{
					v1 = String.valueOf(Character.MAX_VALUE);
					break;
				}
			}
			if (v.compareTo(v1) < 0){
				allVars.add(v);
			}
			else{
//				System.out.println(v);
				allVars.add(v);
				if(it1.hasNext()){
					v1 = it1.next().getKey();
				}
				else{
					v1 = String.valueOf(Character.MAX_VALUE);
				}
			}
		}
		//if the first term has no variables, then you add only the second term's variables to the list
//		if(this.entrySet().size() == 0 && t1.entrySet().size() > 0){
//			allVars.add(v1);
//			while(it1.hasNext()){
//				allVars.add(it1.next().getKey());
//			}
//		}
		if(!it.hasNext() && (!v1.equals(Character.toString(Character.MAX_VALUE))) || it1.hasNext()){
			allVars.add(v1);
			while(it1.hasNext()){
				allVars.add(it1.next().getKey());
			}
		}
		
		if(allVars.contains("x")){
			allVars.remove("x");
			allVars.add(0, "x");
		}
//		System.out.println(allVars);
		for(String currentVar : allVars){
			//if first Term doesn't have a variable, then the other Term has it and thus is "greater"; same goes for the second Term
			if(this.get(currentVar) == null){
//				System.out.println("hello");
//				System.out.println(currentVar);
				return 1;
			}
			if(t1.get(currentVar) == null){
				return -1;
			}
			if(this.get(currentVar) > t1.get(currentVar)){
				return -1;
			}
			if(this.get(currentVar) < t1.get(currentVar)){
				return 1;
			}
		}
		return 0;
	}
}
