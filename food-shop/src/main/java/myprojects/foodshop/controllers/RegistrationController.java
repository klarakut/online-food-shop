package myprojects.foodshop.controllers;

import myprojects.foodshop.models.dtos.ResponseDto;
import myprojects.foodshop.models.dtos.userDtos.CreateUserDto;
import myprojects.foodshop.models.dtos.userDtos.UserResponseDto;
import myprojects.foodshop.service.RoleService;
import myprojects.foodshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public RegistrationController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/registration")      // ({"/register", "/users"}) // store
    public ResponseEntity<? extends ResponseDto> registration(@RequestBody CreateUserDto dto){
        //URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/user/registration").toUriString());
        //return ResponseEntity.created(uri).body(userService.registerUser(dto));

        UserResponseDto userResponse = userService.registerUser(dto);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }
}
