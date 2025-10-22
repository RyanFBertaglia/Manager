package com.manager.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import lombok.Data;

@Entity
@Data
public class Categories {
    @Id
    private Integer category_id;
    private String category_name;
    private String description;

    @Column(name = "picture", columnDefinition = "bytea")
    private byte[] picture;

}
