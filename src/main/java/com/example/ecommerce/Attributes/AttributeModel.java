package com.example.ecommerce.Attributes;

import java.util.List;

import com.example.ecommerce.Category.CategoryModel;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

@Entity(name = "attribute")
@Table(name = "attribute")
public class AttributeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "type")
    String type;

    @OneToMany(mappedBy = "parentAttribute")
    List<AttributeValueModel> values;

    @ManyToMany
    @JoinTable(name = "Attribute_Category", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "attr_id"))
    List<CategoryModel> parentCategories;

    public void setCategories(List<CategoryModel> parentCategories) {
        this.parentCategories = parentCategories;
    }

    public List<CategoryModel> getCategory() {
        return this.parentCategories;
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

    public void setValues(List<AttributeValueModel> values) {
        this.values = values;
    }

    public List<AttributeValueModel> getValues() {
        return this.values;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
