package ca.neilwhite.observabilitydemo.services;

import ca.neilwhite.observabilitydemo.exceptions.CustomerAlreadyExistsException;
import ca.neilwhite.observabilitydemo.exceptions.CustomerNotFoundException;
import ca.neilwhite.observabilitydemo.models.dtos.CreateCustomerRequest;
import ca.neilwhite.observabilitydemo.models.entities.Customer;
import ca.neilwhite.observabilitydemo.repositories.CustomerRepository;
import io.micrometer.observation.annotation.Observed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Observed
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;

    public List<Customer> getCustomers() {
        return this.repository.findAll();
    }

    public Customer getCustomer(long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    public Customer createCustomer(CreateCustomerRequest request) {
        this.repository.findByNameIgnoreCase(request.name())
                .ifPresent(customer -> {
                    throw new CustomerAlreadyExistsException(customer.getName());
                });

        return this.repository.save(new Customer(0L, request.name()));
    }

    public void deleteCustomer(Long id) {
        Customer customer = this.repository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        this.repository.delete(customer);
    }
}
