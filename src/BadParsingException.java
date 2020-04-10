
public class BadParsingException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6784593017315727381L;

	public BadParsingException(States state, int position){
		super("Polynom poorly parsed; state " + state + " position " + position);
	}
}
