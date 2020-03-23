package com.rosygamingglassesmidtier.midtier.repository;

import com.rosygamingglassesmidtier.midtier.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
}
