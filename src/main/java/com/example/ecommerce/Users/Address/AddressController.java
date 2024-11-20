package com.example.ecommerce.Users.Address;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.Response.Response;
import com.example.ecommerce.Users.User;
import com.example.ecommerce.Users.UserRepository;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@Controller
public class AddressController {

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/addaddress")
    public Response addAddress(@RequestParam String street, @RequestParam String city,
            @RequestParam String state, @RequestParam String country, @RequestParam Long userid) {
        Response res = new Response();
        AddressModel address = new AddressModel();
        Optional<User> user = userRepository.findById(userid);
        if (user.isPresent() == false) {
            res.message = "User does not exists";
            return res;
        } else {
            User user2 = user.get();
            address.setCountry(country);
            address.setCity(city);
            address.setStreet(street);
            address.setState(state);
            address.setUser(user2);
            addressRepository.save(address);
            res.message = "Address has been saved successfully";
        }
        return res;
    }

}
