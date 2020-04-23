package org.themselves.alber.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    @ApiModelProperty("생성일")
    private LocalDateTime createdDate;

    @LastModifiedDate
    @ApiModelProperty("수정일")
    private LocalDateTime updatedDate;

    @CreatedBy
    @Column(updatable = false)
    @ApiModelProperty("생성자")
    private Long createdBy;

    @LastModifiedBy
    @ApiModelProperty("수정자")
    private Long updatedBy;


}
