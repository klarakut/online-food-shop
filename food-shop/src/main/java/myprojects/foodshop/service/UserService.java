package myprojects.foodshop.service;

import myprojects.foodshop.models.User;
import myprojects.foodshop.models.dtos.userDtos.CreateUserDto;
import myprojects.foodshop.models.dtos.userDtos.UserResponseDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    void addRoleToUser(String email, String name);
    User getUser(String username);
    List<User> getUsers();
    User getUserFromToken(HttpServletRequest request);
    UserResponseDto registerUser(CreateUserDto createUserDto);
}