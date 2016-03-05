package pl.edu.icm.unicore.spring.central.tss;

public class UnavailableSiteException extends RuntimeException {
    public UnavailableSiteException(Throwable e) {
        super(e);
    }
}
