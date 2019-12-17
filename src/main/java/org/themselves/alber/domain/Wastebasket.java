package org.themselves.alber.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Wastebasket extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "box_id")
    private Long id;

    private String address;

    private String garType;

    private String agency;

    private String agency_id;

    private Boolean userRegisterYn;
}
