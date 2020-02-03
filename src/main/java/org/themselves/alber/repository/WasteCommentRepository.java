package org.themselves.alber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.themselves.alber.domain.WastebasketComment;

public interface WasteCommentRepository extends JpaRepository<WastebasketComment, Long> {
}
