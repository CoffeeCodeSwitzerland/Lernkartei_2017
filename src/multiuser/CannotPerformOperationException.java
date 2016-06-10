package multiuser;

public class CannotPerformOperationException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CannotPerformOperationException(String message) {
        super(message);
    }
    public CannotPerformOperationException(String message, Throwable source) {
        super(message, source);
    }
}
