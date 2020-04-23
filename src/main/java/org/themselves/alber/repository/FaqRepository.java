package org.themselves.alber.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.themselves.alber.domain.Faq;

import java.util.Optional;

public interface FaqRepository extends JpaRepository<Faq, Long> , FaqRepositoryCustom {

    public Optional<Faq> findByQuestion(String question);

    @Query(" select f from Faq f order by f.id desc ")
    Page<Faq> findAll(Pageable pageable);
}
