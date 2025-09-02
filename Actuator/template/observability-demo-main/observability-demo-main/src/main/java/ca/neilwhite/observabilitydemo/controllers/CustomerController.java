package ca.neilwhite.observabilitydemo.controllers;

import ca.neilwhite.observabilitydemo.models.dtos.CreateCustomerRequest;
import ca.neilwhite.observabilitydemo.models.entities.Customer;
import ca.neilwhite.observabilitydemo.services.CustomerService;
import io.micrometer.observation.annotation.Observed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Observed
@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService service;

    @GetMapping
    public List<Customer> getCustomers() {
        return this.service.getCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        return this.service.getCustomer(id);
    }

    @PostMapping
    public Customer createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        return this.service.createCustomer(request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        this.service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
