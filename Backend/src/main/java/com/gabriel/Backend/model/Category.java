package com.gabriel.Backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    @Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
    public class Category {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "category_id")
        private Long id;
        private String name;
        private boolean is_deleted;
        private boolean is_activated;

        public Category(String name){
            this.name = name;
            this.is_activated = true;
            this.is_deleted = false;
        }
    }

