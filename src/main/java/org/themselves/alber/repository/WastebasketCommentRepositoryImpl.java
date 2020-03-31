package org.themselves.alber.repository;

import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.themselves.alber.domain.*;
import org.themselves.alber.repository.projection.MyPageProjection;

import javax.persistence.EntityManager;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.*;
import static org.themselves.alber.domain.QImage.image;
import static org.themselves.alber.domain.QWastebasket.*;
import static org.themselves.alber.domain.QWastebasketComment.wastebasketComment;
import static org.themselves.alber.domain.QWastebasketImage.wastebasketImage;

@RequiredArgsConstructor
public class WastebasketCommentRepositoryImpl implements WastebasketCommentRepositoryCustom{

    private final EntityManager em;

    @Override
    public List<WastebasketComment> findByUserQueryDSL(User user) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        QWastebasketImage wastebasketImageSub = new QWastebasketImage("wastebasketImageSub");

        List<WastebasketComment> result = query
                .select(wastebasketComment)
                .from(wastebasketComment)
                .join(wastebasketComment.wastebasket, wastebasket).fetchJoin()
                .leftJoin(wastebasket.wastebasketImageList, wastebasketImage).fetchJoin()
                .leftJoin(wastebasketImage.image, image).fetchJoin()
                .where(wastebasketComment.user.eq(user))
//                        .and(wastebasketImage.createdDate.eq(select(wastebasketImageSub.createdDate.max())
//                                                            .from(wastebasketImageSub)
//                                                            .where(wastebasket.eq(wastebasketImageSub.wastebasket)))))
                .orderBy(wastebasketComment.createdDate.desc())
                .fetch();

        return result;
    }
}
