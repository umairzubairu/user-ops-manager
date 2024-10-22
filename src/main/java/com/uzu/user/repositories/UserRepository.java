package com.uzu.user.repositories;

import com.uzu.user.entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByFirstNameIgnoreCaseAndLastNameIgnoreCase(String firstName, String lastName);

    @Query("SELECT u FROM User u WHERE (CONCAT(u.firstName, ' ', u.lastName)) IN :fullNames")
    List<User> findByFullNameInIgnoreCase(@Param("fullNames") List<String> fullNames);

}
