package org.themselves.alber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.themselves.alber.domain.User;

public interface UserRepository extends JpaRepository<User,Long> {

}
