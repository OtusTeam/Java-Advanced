package ca.neilwhite.observabilitydemo.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(long id) {
        super(String.format("Customer with not found. [id=%s]", id));
    }
}
