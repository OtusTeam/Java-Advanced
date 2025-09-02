package ca.neilwhite.observabilitydemo.repositories;

import ca.neilwhite.observabilitydemo.models.entities.Customer;
import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Observed
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Override
    <S extends Customer> S save(S entity);

    @Override
    List<Customer> findAll();

    @Override
    Optional<Customer> findById(Long aLong);

    Optional<Customer> findByNameIgnoreCase(String name);
}
