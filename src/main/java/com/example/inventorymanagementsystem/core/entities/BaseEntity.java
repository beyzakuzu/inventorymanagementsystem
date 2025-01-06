package com.example.inventorymanagementsystem.core.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity<TPrimaryKey> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private TPrimaryKey id;

    public TPrimaryKey getId() {
        return id;
    }

    public void setId(TPrimaryKey id) {
        this.id = id;
    }
}
