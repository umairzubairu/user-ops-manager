package com.uzu.user.repositories;

import com.uzu.user.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);
}
