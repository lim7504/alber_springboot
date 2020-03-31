package org.themselves.alber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.themselves.alber.domain.UserGrade;

public interface UserGradeRepository extends JpaRepository<UserGrade,String> {

}
