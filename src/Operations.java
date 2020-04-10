//import java.util.ArrayList;
import java.util.Map;

public class Operations {
	
	public static Polynom sum(Polynom p1, Polynom p2)
	{
		Polynom p3=new Polynom(p1);
		for (Map.Entry<Term, Double> m:p2.entrySet())
		{
			double d1 = m.getValue();
			if (p3.containsKey(m.getKey())==true)
			{
				double d2 = p3.get(m.getKey());
				if(d1+d2 == 0)
				{
					p3.remove(m.getKey());
				}
				else
				{
				p3.put(m.getKey(), d2+d1);
				}
			}
			else
			{
				p3.put(m.getKey(),d1);
			}
		}
		return p3;
	}
	
	public static Polynom diff(Polynom p1, Polynom p2)
	{
		Polynom p3=new Polynom(p1);
		for (Map.Entry<Term, Double> m:p2.entrySet())
		{
			double d1 = m.getValue();
			if (p3.containsKey(m.getKey())==true)
			{
				double d2 = p3.get(m.getKey());
				if(d2-d1 == 0)
				{
					p3.remove(m.getKey());
				}
				else
				{
				p3.put(m.getKey(), d2-d1);
				}
			}
			else
			{
				p3.put(m.getKey(),-d1);
			}
		}
		return p3;
	}
	
	public static Polynom divideByNumber(Polynom p1, double d)
	{
		Polynom p3 =new Polynom();
		for (Map.Entry<Term, Double> m :p1.entrySet())
		{
			p3.put(m.getKey(),m.getValue()/d);
		}
		return p3;
	}
	
	public static Polynom multiply(Polynom p1, Polynom p2)
	{
		Polynom p3= new Polynom();
		for (Map.Entry<Term,Double> a:p1.entrySet())
		{
			Term x=new Term(a.getKey());
			for (Map.Entry<Term, Double> b:p2.entrySet())
			{
				Term y= new Term(b.getKey());
				Term z = new Term(x);
					for (Map.Entry <String,Integer> k:y.entrySet())
					{
						if (z.containsKey(k.getKey()))
						{
							z.put(k.getKey(), z.get(k.getKey())+k.getValue());
						}
						else
						{
							z.put(k.getKey(),k.getValue());
						}
					}
				Double kk = Double.valueOf(a.getValue().doubleValue() * b.getValue().doubleValue());
				if (kk.doubleValue()!=0) {
				if (p3.containsKey(z))
				{
					if (p3.get(z)+kk!=0) {
					p3.put(z,p3.get(z)+kk);
					}
					else
					{
						p3.remove(z);
					}
				}
				else
				{
					p3.put(z,kk);
				}
				}
					
			}
		}
		return p3;
	}
	
	public static Polynom generatePolynom(final Polynom root, final int nOrder){
		Polynom resultPolynom = new Polynom();
		int counter = 1;
		int m = nOrder;
		
		Polynom[] b = new Polynom[nOrder + 1];
		b[m] = new Polynom(0);
		Polynom toBeRationalised = new Polynom(root);
		
		while(m > 0){
			b[m-1] = divideByNumber(multiply(new Polynom(nOrder), rationalTerms(toBeRationalised, nOrder)), counter);
			Polynom p1 = new Polynom(1.0, new Term("x", m-1));
			Polynom p3 = multiply(new Polynom(-1.0), multiply(p1, b[m-1]));
			resultPolynom.putAll(p3);
			toBeRationalised = multiply(diff(toBeRationalised, b[m-1]), root);
			counter++;
			m--;

		}
		b[nOrder] = new Polynom(1.0, new Term("x", nOrder));
		resultPolynom.putAll(b[nOrder]);
		
		return resultPolynom;
	}
	
	public static Polynom rationalTerms(final Polynom toBeRationalised, final int nOrder){
		Polynom rational = new Polynom();
		for(Map.Entry<Term, Double> entry : toBeRationalised.entrySet()){
			if(entry.getKey().size() == 0){
				rational.put(new Term(entry.getKey()), entry.getValue());
			}
			else if(entry.getKey().firstEntry().getValue() % nOrder == 0){
				rational.put(new Term(entry.getKey()), entry.getValue());
			}
		}
		if(rational.isEmpty()){
			rational.put(new Term(), 0.0);
		}
		return rational;
	}
	
	public static boolean isLetter(char myChar){
		if((myChar >= 'a' && myChar<= 'z') || (myChar >= 'A' && myChar <= 'Z')){
			return true;
		}
		return false;
	}
	
}

