package org.themselves.alber.repository;

import org.hibernate.criterion.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.themselves.alber.domain.User;
import org.themselves.alber.domain.Wastebasket;
import org.themselves.alber.domain.WastebasketComment;
import org.themselves.alber.repository.projection.MyPageProjection;
import org.themselves.alber.repository.projection.UserProjection;

import java.util.List;
import java.util.Optional;

public interface WastebasketCommentRepository extends JpaRepository<WastebasketComment, Long> ,WastebasketCommentRepositoryCustom{

    @Query("select wc from WastebasketComment wc where wc.wastebasket = :wid")
    List<WastebasketComment> findByWastebasketId(@Param("wid") Wastebasket wid);

//    @Query("select wc " +
//            "from WastebasketComment wc " +
//            "join fetch wc.wastebasket w " +
//            "where wc.user = :user " +
//            "order by wc.createdDate desc ")
//    List<WastebasketComment> findByUser(@Param("user") User user);


    @Query(value = "select" +
            "        wc.contents" +
            "        ,w.box_name" +
            "        ,w.area_si" +
            "        ,i.url" +
            " from" +
            "     wastebasket_comment wc" +
            " inner join" +
            "     wastebasket w" +
            "        on wc.box_id = w.box_id" +
//            "        and wc.user_id = :user_id " +
            " left join" +
            "     wastebasket_image wi" +
            "        on wc.box_id = wi.box_id" +
            "        and wi.image_id = (select image_id" +
            "                                from wastebasket_image" +
            "                                where box_id = wi.box_id" +
            "                                order by created_date desc" +
            "                                limit 1)" +
            " left join" +
            "     image i" +
            "        on wi.image_id = i.image_id" +
            " where wc.user_id = :user_id" +
            " order by wc.created_date desc ", nativeQuery = true)
    List<MyPageProjection> findByUser(@Param("user_id") Long user_id);
}
