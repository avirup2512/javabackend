package com.example.ecommerce.Attributes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity(name = "attributevalue")
@Table(name = "attributevalue")
public class AttributeValueModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    String name;

    @ManyToOne()
    @JoinColumn(name = "attribute_id", nullable = false)
    AttributeModel parentAttribute;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setParent(AttributeModel attributeModel) {
        this.parentAttribute = attributeModel;
    }

    public AttributeModel getParent() {
        return this.parentAttribute;
    }
}
