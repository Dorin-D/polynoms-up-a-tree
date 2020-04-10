import java.util.Arrays;
import java.util.TreeMap;

enum States{
	START, SIGN, COEFFICIENT, PERIOD, DECIMAL, 
	STAR, VAR, CARET, POWER, RIGHT_SIDE//, BAD_CHARACTER
}

public class PolynomBuilder {
	protected TreeMap<Term, Double> termMap = new TreeMap<>();
	protected Double freeTerm = 0.0;
	
	
	protected int position = 0; //is fine
	
//	protected ArrayList<String> varsInPolynom; unneeded after rework //is fine 
	protected Term currentTerm; //resets in finaliseTheTerm
	protected String currentVarName = ""; //gets the name in state VAR, resets in finaliseTheTerm()
	protected int currentVarRank; //resets in state VAR and in finaliseTheTerm()
	
	protected States currentState = States.START; //is fine
	
	protected char currentChar; //is fine
	
	protected boolean rightSide = false; //once it is true you can not go through "=" again 
	protected boolean hasMinus = false; //resets in finaliseTheTerm()
	protected double currentCoefficient = 0; //resets in finaliseTheTerm()
	protected int decimalTenMultiplier = 0; //resets in state PERIOD
	
	
	public PolynomBuilder(String polyString) throws BadParsingException, UnstableStateException{
//		varsInPolynom = new ArrayList<String>(givenVars); unneeded after rework 
		currentTerm = new Term();

		try{
			while(position < polyString.length()){
				currentChar = polyString.charAt(this.position);
				currentState = parseChar(this.currentChar);
				position++;		
			}
			finaliseTheTerm();
			if( currentState == States.COEFFICIENT ||
				currentState == States.DECIMAL ||
				currentState == States.VAR ||
				currentState == States.POWER ){
				System.out.println("Successfully parsed!");
			} else{
				System.out.println(this.currentState);
				throw new UnstableStateException("Polynom ended in unstable state");
			}
		}catch(BadParsingException e){
			throw e;
		}
	}
	
	public States parseChar(char c) throws BadParsingException{
		int pos;
		if(c == ' '){
			return currentState;
		}
//checks if current char is part of the string and if the current state is part of the array below		
		if((pos = "+-".indexOf(c))!= -1 && 
		Arrays.asList(States.START, States.COEFFICIENT, States.DECIMAL, States.VAR, States.POWER, States.RIGHT_SIDE).contains(currentState)){
//if sign is "-" (position 1 in the String above)
//then the coefficient is negated
			if(currentState != States.START && currentState != States.SIGN && currentState != States.RIGHT_SIDE){
				finaliseTheTerm();
			}
			if(pos == 1){
				hasMinus = !hasMinus;
			}
			
			return States.SIGN;
		}
		
		if((pos = "0123456789".indexOf(c)) != -1 && Arrays.asList(States.START, States.SIGN, States.COEFFICIENT, States.RIGHT_SIDE).contains(currentState)){
			currentCoefficient = currentCoefficient * 10 + pos;
			return States.COEFFICIENT;
		}
		
		if(c == '.' && currentState == States.COEFFICIENT){
			this.decimalTenMultiplier = 10;
			return States.PERIOD;
		}
		
		if((pos = "0123456789".indexOf(c)) != -1 && Arrays.asList(States.PERIOD, States.DECIMAL, States.RIGHT_SIDE).contains(currentState)){
			currentCoefficient = currentCoefficient + pos/(double)decimalTenMultiplier;
			decimalTenMultiplier *= 10;
			return States.DECIMAL;
		}
		
		if(c == '*' && Arrays.asList(States.COEFFICIENT, States.DECIMAL, States.VAR, States.POWER).contains(currentState)){
			return States.STAR;
		}
		//I hope I made this work
		if(Operations.isLetter(c) && 
	Arrays.asList(States.START, States.SIGN, States.COEFFICIENT, States.DECIMAL, States.STAR, States.VAR, States.POWER, 
	States.RIGHT_SIDE).contains(currentState)){
			if(Arrays.asList(States.START, States.SIGN).contains(currentState)){
				currentCoefficient = 1;
			}
			
			computeCurrentVarRank();
			
			currentVarName = "" + c;
			currentVarRank = 0;
			return States.VAR;
		}
		
		if(c == '^' && currentState == States.VAR){
			return States.CARET;
		}
		
		if((pos = "0123456789".indexOf(c)) != -1 && Arrays.asList(States.VAR, States.CARET, States.POWER, States.RIGHT_SIDE).contains(currentState)){
			currentVarRank = currentVarRank * 10 + pos;
			return States.POWER;
		}
		
		if(c == '=' && Arrays.asList(States.COEFFICIENT, States.DECIMAL, States.VAR, States.POWER).contains(currentState) && !rightSide){
			finaliseTheTerm();
			rightSide = true;
			return States.RIGHT_SIDE;
		}
		throw new BadParsingException(currentState, position);
		//return States.BAD_CHARACTER;
	}
	
	
	public void finaliseTheTerm(){
		if(hasMinus){
			currentCoefficient = -currentCoefficient;
		}
		if(rightSide){
			currentCoefficient = -currentCoefficient;
		}

		computeCurrentVarRank();

		if(termMap.get(currentTerm) == null){
			if(currentCoefficient != 0){
				termMap.put(currentTerm, currentCoefficient);
			}
		} else {
			termMap.put(currentTerm, termMap.get(currentTerm) + currentCoefficient);
			if(termMap.get(currentTerm) == 0){
				termMap.remove(currentTerm);
			}
		}
		hasMinus = false;
		currentTerm = new Term();
		currentCoefficient = 0;
		currentVarRank = 0;
		currentVarName = "";

	}
	
	public void computeCurrentVarRank(){
		
		if(currentState == States.VAR || (currentState == States.STAR && currentVarRank == 0 && currentVarName != "")){
			//if var is not set in yet, then you avoid NullPointerException this way
			if(currentTerm.get(currentVarName) == null){
				currentTerm.put(currentVarName, 1);
			}
			//if the power of the previous var was not specified then it is implied that it is 1, otherwise this would've been dealt with
			else{
				currentTerm.put(currentVarName, currentTerm.get(currentVarName) + 1);
			}
		}
		
		if(currentState == States.POWER || (currentState == States.STAR && currentVarRank != 0 && currentVarName != "")){
			if(currentTerm.get(currentVarName) == null){
				//avoiding adding null with a value
				currentTerm.put(currentVarName, currentVarRank);
			}
			// if the power of the previous var was specified, then it gets added to our var
			else{
				currentTerm.put(currentVarName, currentTerm.get(currentVarName) + currentVarRank);
			}
		}
	}

	public TreeMap<Term, Double> getTermMap(){
		return this.termMap;
	}
	
	public Double getFreeTerm(){
		return this.freeTerm;
	}
}
