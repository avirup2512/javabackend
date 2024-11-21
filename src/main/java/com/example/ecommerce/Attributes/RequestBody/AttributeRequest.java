package com.example.ecommerce.Attributes.RequestBody;

import java.util.List;

public class AttributeRequest {

    String name;
    String type;
    List<Long> categoryIds;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setCategories(List<Long> ids) {
        this.categoryIds = ids;
    }

    public List<Long> getCategories() {
        return this.categoryIds;
    }
}
