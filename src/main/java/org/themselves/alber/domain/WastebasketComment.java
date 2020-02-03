package org.themselves.alber.domain;

import lombok.Getter;
import lombok.Setter;
import org.themselves.alber.domain.common.BaseCreatedEntity;

import javax.persistence.*;

@Entity
@IdClass(WastebasketCommentId.class)
@TableGenerator(name="SEQ_WASTEBASKET_COMMENT", table="SEQUENCES", pkColumnValue="WASTEBASKET_COMMENT_SEQ", allocationSize=1)
@Getter @Setter
public class WastebasketComment extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="SEQ_WASTEBASKET_COMMENT")
    @Column(name = "comment_id")
    private Long id;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    private Wastebasket wastebasket;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
