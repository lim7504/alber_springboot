package org.themselves.alber.repository;

import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.themselves.alber.controller.notifycation.dto.NotifycationAdminListRequestDto;
import org.themselves.alber.controller.notifycation.dto.NotifycationAdminListResponseDto;

import javax.persistence.EntityManager;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static org.themselves.alber.domain.QNotifycation.*;
import static org.themselves.alber.domain.QUser.*;

@RequiredArgsConstructor
public class NotifycationRepositoryImpl implements NotifycationRepositoryCustom {

    private final EntityManager em;

    @Override
    public Page<NotifycationAdminListResponseDto> findAllWithCondition(
            NotifycationAdminListRequestDto notiDto,
            Pageable pageable) {

        JPAQueryFactory query = new JPAQueryFactory(em);

        QueryResults<NotifycationAdminListResponseDto> results = query.select(Projections.fields(NotifycationAdminListResponseDto.class,
                notifycation.id,
                notifycation.notiTitle.as("title"),
                notifycation.notiContents.as("contents"),
                ExpressionUtils.as(select(user.nickname)
                                    .from(user)
                                    .where(user.id.eq(notifycation.createdBy))
                                    ,  "userNickname"),
                notifycation.createdDate))
                .from(notifycation)
                .where(notiIdEq(notiDto.getId()),
                        notiTitleEq(notiDto.getTitle()),
                        notiContentsEq(notiDto.getContents()))
                .orderBy(notifycation.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<NotifycationAdminListResponseDto> content = results.getResults();
        Long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private Predicate notiIdEq(Long id) {
        return id == null ? null : notifycation.id.eq(id);
    }

    private Predicate notiTitleEq(String title) {
        return title == null ? null : notifycation.notiTitle.contains(title);
    }

    private Predicate notiContentsEq(String contents) {
        return contents == null ? null : notifycation.notiContents.contains(contents);
    }
}
