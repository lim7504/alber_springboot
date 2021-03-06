package org.themselves.alber.repository;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentForMyRegistWastebasketDto;
import org.themselves.alber.controller.wastebasketcomment.dto.WastebasketCommentForMyRegistCommentDto;
import org.themselves.alber.domain.*;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;

import static com.querydsl.jpa.JPAExpressions.select;
import static org.themselves.alber.domain.QImage.image;
import static org.themselves.alber.domain.QUser.user;
import static org.themselves.alber.domain.QWastebasket.*;
import static org.themselves.alber.domain.QWastebasketComment.wastebasketComment;
import static org.themselves.alber.domain.QWastebasketImage.wastebasketImage;

@RequiredArgsConstructor
public class WastebasketCommentRepositoryImpl implements WastebasketCommentRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<WastebasketComment> findByUserQueryDSL(User user) {
        JPAQueryFactory query = new JPAQueryFactory(em);

//        QWastebasketImage wastebasketImageSub = new QWastebasketImage("wastebasketImageSub");

        List<WastebasketComment> result = query
                .selectDistinct(wastebasketComment)
                .from(wastebasketComment)
                .innerJoin(wastebasketComment.wastebasket, wastebasket).fetchJoin()
                .where(wastebasketComment.user.eq(user))
//                        .and(wastebasketImage.createdDate.eq(select(wastebasketImageSub.createdDate.max())
//                                                            .from(wastebasketImageSub)
//                                                            .where(wastebasket.eq(wastebasketImageSub.wastebasket)))))
                .orderBy(wastebasketComment.createdDate.desc())
                .fetch();

        return result;
    }

    @Override
    public List<WastebasketImage> findByUserWastebasketImage(List<Wastebasket> wastebaskets) {
        JPAQueryFactory query = new JPAQueryFactory(em);
        QWastebasketImage wastebasketImageSub = new QWastebasketImage("wastebasketImageSub");

        return query
                .selectFrom(wastebasketImage)
                .innerJoin(wastebasketImage.image,image).fetchJoin()
                .where(wastebasketImage.wastebasket.in(wastebaskets)
                        .and(wastebasketImage.createdDate.eq(select(wastebasketImageSub.createdDate.max())
                                                .from(wastebasketImageSub)
                                                .where(wastebasketImageSub.wastebasket.eq(wastebasketImage.wastebasket)))))
                .fetch();
    }


    @Override
    public List<WastebasketCommentForMyRegistCommentDto> findByUserForMyRegistComment(User user) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        QWastebasketImage wastebasketImageSub = new QWastebasketImage("wastebasketImageSub");
        QWastebasketImage wastebasketImageSub2 = new QWastebasketImage("wastebasketImageSub2");

        List<WastebasketCommentForMyRegistCommentDto> result = query
                .select(Projections.fields(WastebasketCommentForMyRegistCommentDto.class,
                        wastebasketComment.contents
                        ,wastebasket.boxName
                        ,wastebasket.areaSi
                        ,ExpressionUtils.as(select(wastebasketImageSub.image.url)
                                        .from(wastebasketImageSub)
                                        .where(wastebasketImageSub.wastebasket.eq(wastebasket)
                                                .and(wastebasketImageSub.createdDate.eq(select(wastebasketImageSub2.createdDate.max())
                                                                                        .from(wastebasketImageSub2)
                                                                                        .where(wastebasketImageSub2.wastebasket.eq(wastebasket)))))
                                        .orderBy(wastebasketImageSub.createdDate.desc())
                                ,"image")))
                .from(wastebasketComment)
                .innerJoin(wastebasketComment.wastebasket, wastebasket)
                .where(wastebasketComment.user.eq(user))
                .orderBy(wastebasketComment.createdDate.desc())
                .fetch();

        return result;
    }


    @Override
    public List<WastebasketCommentForMyRegistWastebasketDto> findByUserForMyRegistWastebasket(Set<Long> wastebasketList) {
        JPAQueryFactory query = new JPAQueryFactory(em);

//        QWastebasketImage wastebasketImageSub = new QWastebasketImage("wastebasketImageSub");
//        QWastebasketImage wastebasketImageSub2 = new QWastebasketImage("wastebasketImageSub2");

        List<WastebasketCommentForMyRegistWastebasketDto> result = query
                .select(Projections.fields(WastebasketCommentForMyRegistWastebasketDto.class,
                        wastebasketComment.id
                        ,wastebasketComment.contents
                        ,wastebasketComment.createdDate
                        ,user.nickname
                        ,image.url.as("image")))
                .from(wastebasketComment)
                .innerJoin(wastebasketComment.wastebasket, wastebasket)
                .innerJoin(wastebasketComment.user, user)
                .innerJoin(user.image, image)
                .where(wastebasketComment.wastebasket.id.in(wastebasketList))
                .orderBy(wastebasket.createdDate.desc()
                        ,wastebasketComment.createdDate.desc())
                .fetch();

        return result;
    }

}
