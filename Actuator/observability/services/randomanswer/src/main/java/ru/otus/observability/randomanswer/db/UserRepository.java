package ru.otus.observability.randomanswer.db;

import io.micrometer.observation.annotation.Observed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Observed
@Repository
public interface UserRepository extends JpaRepository<UserDetails, Long> {

    @Override
    <S extends UserDetails> S save(S entity);

    @Override
    List<UserDetails> findAll();
}
