package com.example.ecommerce.Product;

import java.lang.classfile.ClassFile.Option;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.Product.Rating.RatingModel;
import com.example.ecommerce.Product.Rating.RatingRepository;
import com.example.ecommerce.Response.Response;
import com.example.ecommerce.Users.User;
import com.example.ecommerce.Users.UserRepository;

import io.jsonwebtoken.lang.Arrays;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Controller
public class ProductController {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    UserRepository userRepository;

    DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    @PostMapping("/addProduct")
    public Response postMethodName(@RequestBody Product entity) {
        Response res = new Response();
        if (entity.getName() == null || entity.getPrice() == null) {
            res.message = "Params are missing";
            return res;
        } else {
            productRepository.save(entity);
            res.message = "Product save success fully";
            res.data = entity;
        }

        return res;
    }

    @PostMapping("/addrating")
    public Response postMethodName(@RequestParam String value, @RequestParam Long productId) {
        Response res = new Response();
        Optional<Product> pOptional = productRepository.findById(productId);
        if (pOptional.isPresent() == false) {
            res.message = " Product does not exists";
            return res;
        } else {
            Product product = pOptional.get();
            Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ArrayList<User> user = userRepository.findUserByEmail((String) email);
            if (user.get(0) != null) {
                RatingModel ratingModel = new RatingModel();
                List<User> list = new ArrayList<User>();
                System.out.println(user.get(0));
                list.add(user.get(0));
                ratingModel.setUser(list);
                List<Product> pList = new ArrayList<Product>();
                pList.add(product);
                ratingModel.setProduct(pList);
                ratingModel.setValue(Integer.parseInt(value));
                ratingModel.setPublishedOn(time.format(LocalDateTime.now()));
                ratingRepository.save(ratingModel);
                res.message = "Rating has been saved successfully";
                res.data = ratingModel;
            }
        }
        return res;
    }

    @GetMapping("/product/{id}")
    public Response getMethodName(@PathVariable Long id) {
        Response res = new Response();
        Optional<Product> pOptional = productRepository.findById(id);
        if (pOptional.isPresent() == false) {
            res.message = "Product does not exists";
            return res;
        } else {
            res.data = pOptional.get();
            res.message = "Product found Successfully";
        }
        return res;
    }

}
