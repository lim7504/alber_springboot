package org.themselves.alber.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.themselves.alber.controller.wastebasket.dto.WastebasketForMyRegistWastebasketDto;
import org.themselves.alber.domain.*;

import javax.persistence.EntityManager;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static org.themselves.alber.domain.QPin.pin;
import static org.themselves.alber.domain.QUser.user;
import static org.themselves.alber.domain.QWastebasket.wastebasket;
import static org.themselves.alber.domain.QWastebasketComment.wastebasketComment;

@RequiredArgsConstructor
public class WastebasketRepositoryImpl implements WastebasketRepositoryCustom{

    private final EntityManager em;

//    @Override
//    public List<WastebasketForMyRegistWastebasketDto> findByUserMyRegist(User user) {
//        JPAQueryFactory query = new JPAQueryFactory(em);
//
//        QWastebasketImage wastebasketImageSub = new QWastebasketImage("wastebasketImageSub");
//        QWastebasketImage wastebasketImageSub2 = new QWastebasketImage("wastebasketImageSub2");
//
//        List<WastebasketForMyRegistWastebasketDto> result = query
//                .select(Projections.fields(WastebasketForMyRegistWastebasketDto.class,
//                        wastebasket.id
//                        ,wastebasket.boxName
//                        ,wastebasket.areaSi
//                        ,ExpressionUtils.as(select(wastebasketImageSub.image.url)
//                                        .from(wastebasketImageSub)
//                                        .where(wastebasketImageSub.wastebasket.eq(wastebasket)
//                                                .and(wastebasketImageSub.createdDate.eq(select(wastebasketImageSub2.createdDate.max())
//                                                        .from(wastebasketImageSub2)
//                                                        .where(wastebasketImageSub2.wastebasket.eq(wastebasket)))))
//                                        .orderBy(wastebasketImageSub.createdDate.desc())
//                                ,"image")
//                        ,wastebasket.createdDate))
//                .from(pin)
//                .innerJoin(pin.wastebasket, wastebasket)
//                .where(pin.user.eq(user))
//                .orderBy(wastebasket.createdDate.desc())
//                .fetch();
//
//        return result;
//    }

    @Override
    public List<Pin> findByUserForMyRegistWastebasket(User findUser) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        QImage wastebasketImage = new QImage("wastebasketImage");
        QImage userImage = new QImage("userImage");

        List<Pin> result = query
                .selectDistinct(pin)
                .from(pin)
                .innerJoin(pin.wastebasket, wastebasket).fetchJoin()
                .leftJoin(wastebasket.image, wastebasketImage).fetchJoin()
                .leftJoin(wastebasket.wastebasketCommentList, wastebasketComment).fetchJoin()
                .leftJoin(wastebasketComment.user, user).fetchJoin()
                .leftJoin(user.image, userImage).fetchJoin()
                .where(pin.user.eq(findUser))
                .orderBy(wastebasket.createdDate.desc())
                .fetch();

        return result;
    }
}
