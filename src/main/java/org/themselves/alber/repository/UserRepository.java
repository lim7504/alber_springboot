package org.themselves.alber.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.themselves.alber.domain.User;
import org.themselves.alber.repository.projection.UserProjection;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByNickname(String nickname);

    //일반 join은 inner table에 where 조건을 위해 사용

    //JPQL 방식 FETCH 조인
    //FETCH 조인은 where 를  쓰면 안된다 그로므로 알리야스도 쓰지 않는다
    //알리야스를 쓰는경우가 있는데 중첩으로 fetch join 을 사용할 때 쓴다.
    //일대다는 fetch Join 하지 않는다
    @Query("select u " +
            "from User u " +
            "left join fetch u.image " +
            "where u.email = :email")
     User findByUserAndImageUrl(@Param("email") String email);

    @Query("select u " +
            "from User u " +
            "left join fetch u.image " +
            "left join fetch u.userPinList " +
            "where u.id = :user_id")
    User findByUserAndImageUrlAndPinList(@Param("user_id") Long user_id);

    @Query("select u " +
            "from User u " +
            "left join fetch u.image " +
            "left join fetch u.userPinList " +
            "where u.email = :email")
    User findByUserJPQL3(@Param("email") String email);


    //NativeQuery
    @Query(value = "select u.nickname" +
            "             ,i.url" +
            "         from user u" +
            "         join image i" +
            "           on u.image_id = i.image_id" +
            "        where u.email = ?", nativeQuery = true)
    Object findByNativeQuery(@Param("email") String email);



    //NativeQuery
    @Query(value = "select u.nickname" +
            "             ,i.url" +
            "         from user u" +
            "         join image i" +
            "           on u.image_id = i.image_id" +
            "        where u.email = ?", nativeQuery = true)
    UserProjection findByNativeQuery2(@Param("email") String email);

    //NativeQuery
    @Query(value = "select u.nickname" +
            "             ,i.url" +
            "         from user u" +
            "         join image i" +
            "           on u.image_id = i.image_id", nativeQuery = true)
    List<UserProjection> findByNativeQuery3();

}
