package org.themselves.alber.domain;

import lombok.Getter;
import lombok.Setter;
import org.themselves.alber.controller.faq.dto.FaqDto;
import org.themselves.alber.domain.common.BaseEntity;

import javax.persistence.*;

@Entity
@Getter @Setter
@TableGenerator(name="SEQ_FAQ", table="SEQUENCES", pkColumnValue="FAQ_SEQ", allocationSize=1)
public class Faq extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="SEQ_FAQ")
    @Column(name = "faq_id")
    private Long id;

    private String question;

    private String answer;

    public static Faq createFaq(FaqDto dto) {
        Faq faq = new Faq();
        faq.question = dto.getQuestion();
        faq.answer = dto.getAnswer();
        return faq;
    }

    public void updateFaq(FaqDto dto) {
        this.question = dto.getQuestion();
        this.answer = dto.getAnswer();
    }
}
