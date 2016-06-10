package multiuser;

public class InvalidHashException extends Exception
{
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public InvalidHashException(String message) {
         super(message);
     }
     public InvalidHashException(String message, Throwable source) {
         super(message, source);
     }
}
