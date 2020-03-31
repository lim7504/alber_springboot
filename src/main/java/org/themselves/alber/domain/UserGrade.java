package org.themselves.alber.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter @Setter
@ToString(of = {"grade","point"})
public class UserGrade {

    @Id
    private String grade;

    private Long point;

    private Boolean useYn;

}
