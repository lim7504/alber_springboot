package org.themselves.alber.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.themselves.alber.domain.Notifycation;
import org.themselves.alber.domain.User;

import java.util.Optional;

public interface NotifycationRepository extends JpaRepository<Notifycation, Long>, NotifycationRepositoryCustom {
    Optional<Notifycation> findByNotiTitle(String notiTitle);

    @Query("select n " +
            "from Notifycation n " +
            "left join fetch n.notifycationImageList ni " +
            "left join fetch ni.image " +
            "where n.id = :id")
    Optional<Notifycation> findByNotifycationWithImage(@Param("id") Long id);

    @Query( value = "select n " +
            "from Notifycation n " +
            "order by n.id desc ")
//            , countQuery = "select count(n) from Notifycation n ")
    Page<Notifycation> findAll2(Pageable pageable);

}
