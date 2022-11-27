package myprojects.foodshop.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {



    @GetMapping("/")
    public void mainPage(){
        System.out.println("Hello there");
    }
}
