package org.themselves.alber.domain;

import lombok.Getter;
import lombok.Setter;
import org.themselves.alber.domain.common.BaseCreatedEntity;
import org.themselves.alber.domain.common.BaseEntity;

import javax.persistence.*;

@Entity
@TableGenerator(name="SEQ_IMAGE", table="SEQUENCES", pkColumnValue="IMAGE_SEQ", allocationSize=1)
@Getter @Setter
public class Image extends BaseCreatedEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator="SEQ_IMAGE")
    @Column(name = "image_id")
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    private Wastebasket wastebasket;
}