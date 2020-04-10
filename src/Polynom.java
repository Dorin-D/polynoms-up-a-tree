import java.util.Map;
import java.util.TreeMap;

public class Polynom extends TreeMap<Term, Double> {
	private static final long serialVersionUID = 936654978042384827L;
	
//	public Double freeTerm; //unnecessary
//	protected TreeMap<String, Integer> varList = new TreeMap<String, Integer>();
	
	
//constructor with no arguments
	public Polynom(){
		
	}
//constructor for a single term
	public Polynom(double coefficient, Term singleTerm){
		this.put(new Term(singleTerm), new Double(coefficient));
	}
//constructor for a Polynom without variables, useful for operations between numbers and polynoms
	public Polynom(double number){
		this.put(new Term(), new Double(number));
	}//^must Be Checked
	
//build Polynom object using another Polynom object, hard copy	
	public Polynom(Polynom p){
//		this.freeTerm = new Double(p.freeTerm);
		for(Map.Entry<Term, Double> entry : p.entrySet()){
			final Term keyTerm = new Term(entry.getKey());
			final Double valueDouble = new Double(entry.getValue());
			this.put(keyTerm, valueDouble);
		}
	}
	
	public Polynom(String polyString) throws BadParsingException, UnstableStateException{
		try{
			PolynomBuilder myPoly = new PolynomBuilder(polyString);
			this.putAll(myPoly.getTermMap());
//			this.freeTerm = myPoly.getFreeTerm();
//			this.varList = myPoly.getVarList();
		} catch(BadParsingException e){
			throw e;
		} catch(UnstableStateException e){
			throw e;
		}

	}
	

	//function to add terms, takes key out of the object
	//unusable atm due to mapping rework
//	public void addTerm(Term t){
//		if(containsKey(t.treeMapKey())){
//			termMap.get(t.treeMapKey()).addCoefficient(t.getCoefficient());
//		}
//		else{
//			termMap.put(t.treeMapKey(), t);
//		}
//	}
	


	
//needs rework
//todos: if tempDouble is integer, then only show integer part
//		 if tempDouble is +1, then don't show it
//		 if rank of var is 0, then don't show var
//		 if rank of var is 1, then don't show the rank
	public String toString(){
		String output = "";
		Integer finalInt=0;
		for(Map.Entry<Term, Double> term : this.entrySet()){
			final Double tempDouble = term.getValue();
			final Integer tempInteger = term.getValue().intValue();
			boolean isInteger = false;
			if (tempDouble.doubleValue()%1==0)
			{
				isInteger=true;
			}
			if(tempDouble > 0 && isInteger==false){
				output += "+" + tempDouble;
			}
			else if(tempDouble < 0 && isInteger==false){
				output += tempDouble;
			}
			else
			 if(tempInteger > 0 && isInteger==true)
				{
					output+= "+" + tempInteger;
				}
				else if (tempInteger < 0 && isInteger==true)
				{
					output+= tempInteger;
				}
			for(Map.Entry<String, Integer> var : term.getKey().entrySet()){
				if (var.getValue()!=0 && var.getValue()!=1) {
				output += "*" + var.getKey() + "^" + var.getValue();
				}
				else
					if (var.getValue()==1)
					{
						output+= "*" + var.getKey();
					}
				
			}
		}
		if (output.charAt(0)!='-') {
		return output.substring(1);
		}
		else
		{
			return output;
		}
	}
}
