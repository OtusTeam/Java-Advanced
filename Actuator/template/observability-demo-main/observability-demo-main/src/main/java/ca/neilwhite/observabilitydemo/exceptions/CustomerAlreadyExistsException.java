package ca.neilwhite.observabilitydemo.exceptions;

public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException(String name) {
        super(String.format("Customer already exists. [name=%s]", name));
    }
}
