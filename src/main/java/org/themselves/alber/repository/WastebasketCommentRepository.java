package org.themselves.alber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.themselves.alber.domain.Wastebasket;
import org.themselves.alber.domain.WastebasketComment;

import java.util.List;

public interface WastebasketCommentRepository extends JpaRepository<WastebasketComment, Long> {

    @Query("select wc from WastebasketComment wc where wc.wastebasket = :wid")
    List<WastebasketComment> findByWastebasketId(@Param("wid") Wastebasket wid);


}
