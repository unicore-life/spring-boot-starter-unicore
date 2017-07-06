package pl.edu.icm.unity.spring.authn;

public class UnprocessableResponseException extends RuntimeException {
    public UnprocessableResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
