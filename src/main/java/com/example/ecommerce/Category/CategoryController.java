package com.example.ecommerce.Category;

import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.Response.Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RestController
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @PostMapping("/createcategory")
    public Response createCategory(@RequestParam String name, @RequestParam String desc,
            @RequestParam Optional<Long> parentId) {
        Response res = new Response();
        if (name == null) {
            res.message = "Category name cannot be empty";
            return res;
        } else {
            CategoryModel c = categoryRepository.findByName(name);
            if (c != null) {
                res.message = "Category Already exists";
                return res;
            } else {
                CategoryModel category = new CategoryModel();
                category.setName(name);
                category.setDescription(desc);
                if (parentId.isPresent() == true) {
                    Optional<CategoryModel> parent = categoryRepository.findById(parentId.get());
                    if (parent.isPresent())
                        category.setParent(Arrays.asList(parent.get()));
                }
                categoryRepository.save(category);
                res.data = category;
                res.message = "Category has been created successfully";
            }
        }
        return res;
    }

    @PutMapping("category/addParent/{id}")
    public Response editParentCategory(@PathVariable Long id, @RequestParam Long parent_id) {
        Response res = new Response();
        System.out.println(id);
        Optional<CategoryModel> child = categoryRepository.findById(id);
        Optional<CategoryModel> parent = categoryRepository.findById(parent_id);
        System.out.println(child);
        if (child.isPresent() == false || parent.isPresent() == false) {
            res.message = "Category does not exists";
            return res;
        } else {
            CategoryModel childCategory = child.get();
            CategoryModel parentCategory = parent.get();
            // List<CategoryModel> list = new ArrayList<>();
            List<CategoryModel> existingParent = childCategory.getParent();
            existingParent.add(parentCategory);
            childCategory.setParent(existingParent);
            categoryRepository.save(childCategory);
        }
        return res;
    }

    @PutMapping("category/edit/{id}")
    public Response editCategory(@PathVariable Long id, @RequestParam String name, @RequestParam String desc) {
        Response res = new Response();
        Optional<CategoryModel> child = categoryRepository.findById(id);
        if (child.isPresent() == false) {
            res.message = "Category does not exists";
            return res;
        } else {
            CategoryModel childCategory = child.get();
            if (name != null)
                childCategory.setName(name);
            if (desc != null)
                childCategory.setDescription(desc);
            categoryRepository.save(childCategory);
        }
        return res;
    }

    @GetMapping("/getcategory")
    public Response getCategoryById(@RequestParam Long id) {
        Response res = new Response();
        if (id == null) {
            res.message = "Category ID is required";
            return res;
        } else {
            Optional<CategoryModel> cOptional = categoryRepository.findCategoryModel(id);
            if (!cOptional.isPresent()) {
                res.message = "Category does not exists";
                return res;
            } else {
                CategoryModel categoryModel = cOptional.get();
                res.data = categoryModel;
                res.message = "Category has been found";
                return res;
            }
        }
    }

    @DeleteMapping("category/delete/{id}")
    public Response deleteCategory(@PathVariable Long id) {
        Response res = new Response();
        if (id == null) {
            res.message = "Category id is required";
            return res;
        } else {
            Optional<CategoryModel> c = categoryRepository.findById(id);
            if (c.isPresent() == false) {
                res.message = "Category does not present";
                return res;
            } else {
                CategoryModel categoryModel = c.get();
                categoryRepository.delete(categoryModel);
                res.message = "Category has been deleted successfully";
            }
        }
        return res;
    }

}
