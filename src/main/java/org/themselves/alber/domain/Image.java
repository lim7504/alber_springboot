package org.themselves.alber.domain;

import lombok.Getter;
import lombok.Setter;
import org.themselves.alber.domain.common.BaseCreatedEntity;
import org.themselves.alber.domain.common.BaseEntity;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Image extends BaseCreatedEntity {

    @Id
    @GeneratedValue
    @Column(name = "image_id")
    private Long id;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id")
    private Wastebasket wastebasket;
}