package org.themselves.alber.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.themselves.alber.domain.User;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user){
        em.persist(user);
    }

    public User findOne(Long id){
        return em.find(User.class, id);
    }

}
