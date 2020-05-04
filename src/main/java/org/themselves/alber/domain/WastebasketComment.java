package org.themselves.alber.domain;

import lombok.Getter;
import lombok.Setter;
import org.themselves.alber.domain.common.BaseCreatedEntity;

import javax.persistence.*;

@Entity
//@IdClass(WastebasketCommentId.class)
@TableGenerator(name="SEQ_WASTEBASKET_COMMENT", table="SEQUENCES", pkColumnValue="WASTEBASKET_COMMENT_SEQ", allocationSize=1)
@Getter @Setter
public class WastebasketComment extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.TABLE, generator="SEQ_WASTEBASKET_COMMENT")
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    private Wastebasket wastebasket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String contents;

    public static WastebasketComment createWastebasketComment(User user, Wastebasket wastebasket, String contents){
        WastebasketComment wastebasketComment = new WastebasketComment();
        wastebasketComment.setUser(user);
        wastebasketComment.setWastebasket(wastebasket);
        wastebasketComment.setContents(contents);
        return wastebasketComment;
    }

    public void updateContents(String contents){
        this.contents = contents;
    }
}
