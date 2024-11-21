package com.example.ecommerce.Attributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.Attributes.RequestBody.AttributeRequest;
import com.example.ecommerce.Response.Response;

import jakarta.websocket.server.PathParam;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@Controller
public class AttributeController {

    @Autowired
    AttributeValueRepository aValueRepository;

    @Autowired
    AttributeRepository aRepository;

    @PostMapping("/addattribute")
    public Response postMethodName(@RequestBody AttributeRequest entity) {
        Response res = new Response();
        AttributeModel aModel = new AttributeModel();
        aModel.setName(name);
        aModel.setType(type);
        aRepository.save(aModel);
        res.message = "Attribute is saved successfully";
        res.data = aModel;
        return res;
    }

    @PostMapping("/addattributevalue")
    public Response addAttributeValue(@RequestParam Long aId, @RequestParam String value) {
        Response res = new Response();
        Optional<AttributeModel> aMOptional = aRepository.findById(aId);
        System.out.println(aId);
        if (aMOptional.isPresent() == false) {
            res.message = "Attribute does not exists";
            return res;
        } else {
            AttributeValueModel aValueModel = new AttributeValueModel();
            aValueModel.setName(value);
            aValueModel.setParent(aMOptional.get());
            aValueRepository.save(aValueModel);
            res.message = "Attribute value is saved successfully";
            res.data = aValueModel;
            return res;
        }
    }

    @GetMapping("/attributesvalue/{id}")
    public Response getMethodName(@PathVariable Long id) {
        Response res = new Response();
        Optional<AttributeModel> aMOptional = aRepository.findById(id);
        if (aMOptional.isPresent() == false) {
            res.message = "Attribute does not exists";
            return res;
        } else {
            AttributeModel aModel = aMOptional.get();
            res.data = aModel;
            res.message = "Attribute found Successfully";
        }

        return res;
    }

}