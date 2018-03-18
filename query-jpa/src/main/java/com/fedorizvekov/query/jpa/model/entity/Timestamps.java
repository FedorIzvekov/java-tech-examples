package com.fedorizvekov.query.jpa.model.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Timestamps {

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;


    @PrePersist
    private void beforeCreate() {
        created = LocalDateTime.now();
        updated = LocalDateTime.now();
    }


    @PreUpdate
    private void beforeUpdate() {
        updated = LocalDateTime.now();
    }

}
