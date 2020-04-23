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
import org.themselves.alber.controller.faq.dto.FaqAdminListRequestDto;
import org.themselves.alber.controller.faq.dto.FaqAdminListResponseDto;

import javax.persistence.EntityManager;
import java.util.List;

import static com.querydsl.jpa.JPAExpressions.select;
import static org.themselves.alber.domain.QFaq.*;
import static org.themselves.alber.domain.QUser.user;

@RequiredArgsConstructor
public class FaqRepositoryImpl implements FaqRepositoryCustom {

    private final EntityManager em;

    @Override
    public Page<FaqAdminListResponseDto> findAllWithCondition(
            FaqAdminListRequestDto dto,
            Pageable pageable) {

        JPAQueryFactory query = new JPAQueryFactory(em);

        QueryResults<FaqAdminListResponseDto> results = query.select(Projections.fields(FaqAdminListResponseDto.class,
                faq.id,
                faq.question.as("question"),
                faq.answer.as("answer"),
                ExpressionUtils.as(select(user.nickname)
                                    .from(user)
                                    .where(user.id.eq(faq.createdBy))
                                    ,  "userNickname"),
                faq.createdDate))
                .from(faq)
                .where(faqIdEq(dto.getId()),
                        faqQuestionEq(dto.getQuestion()),
                        faqAnswerEq(dto.getAnswer()))
                .orderBy(faq.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<FaqAdminListResponseDto> content = results.getResults();
        Long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    private Predicate faqIdEq(Long id) {
        return id == null ? null : faq.id.eq(id);
    }

    private Predicate faqQuestionEq(String question) {
        return question == null ? null : faq.question.contains(question);
    }

    private Predicate faqAnswerEq(String answer) {
        return answer == null ? null : faq.answer.contains(answer);
    }
}
