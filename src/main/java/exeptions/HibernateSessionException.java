package exeptions;

public class HibernateSessionException extends RuntimeException {
    public HibernateSessionException(String message, Throwable cause) {
        super(message, cause);
    }
}
