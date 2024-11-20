package com.example.ecommerce.Category;

import java.util.List;

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
import jakarta.persistence.Table;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity(name = "category")
@Table(name = "category")
public class CategoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "name")
    String name;

    @Column(name = "description")
    String description;

    @ManyToMany
    @JoinTable(name = "Category_Parent_Child", joinColumns = @JoinColumn(name = "child_id"), inverseJoinColumns = @JoinColumn(name = "parent_id"))
    private List<CategoryModel> parent;

    @ManyToMany(mappedBy = "parent")
    private List<CategoryModel> children;

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

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setChild(List<CategoryModel> subcategories) {
        this.children = subcategories;
    }

    public List<CategoryModel> getChild() {
        return this.children;
    }

    public void setParent(List<CategoryModel> parent) {
        this.parent = parent;
    }

    public List<CategoryModel> getParent() {
        return this.parent;
    }
}
