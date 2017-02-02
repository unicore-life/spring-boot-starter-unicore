package pl.edu.icm.unity.spring.authn;

public class UnprocessableResponseException extends RuntimeException {
    UnprocessableResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
