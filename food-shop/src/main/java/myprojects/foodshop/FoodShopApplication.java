package myprojects.foodshop;

import myprojects.foodshop.models.Product;
import myprojects.foodshop.models.Role;
import myprojects.foodshop.models.User;
import myprojects.foodshop.service.ProductService;
import myprojects.foodshop.service.RoleService;
import myprojects.foodshop.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class FoodShopApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodShopApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	/*
    @Bean
    CommandLineRunner run (UserService userService, RoleService roleService, ProductService productService){
        return args -> {

        // create roles:
            roleService.saveRole(new Role("ROLE_USER"));
            roleService.saveRole(new Role("ROLE_MANAGER"));
            roleService.saveRole(new Role("ROLE_ADMIN"));
            roleService.saveRole(new Role("ROLE_SUPER_ADMIN"));

		// create some users
            userService.saveUser(new User("alex@gmail.com","1234","Alex","A","777","Prague",new ArrayList<>()));
            userService.saveUser(new User("jana@gmail.com","1234","Jana","J","999","Prague",new ArrayList<>()));
            userService.saveUser(new User("petr@gmail.com","1234","Petr","A","777","Prague",new ArrayList<>()));
            userService.saveUser(new User("ilona@gmail.com","1234","Ilona","J","999","Prague",new ArrayList<>()));

		// assign roles to the users
            userService.addRoleToUser("petr@gmail.com","ROLE_USER");
            userService.addRoleToUser("ilona@gmail.com","ROLE_USER");
            userService.addRoleToUser("alex@gmail.com","ROLE_ADMIN");
            userService.addRoleToUser("jana@gmail.com","ROLE_USER");

		// create some items
            productService.saveProduct(new Product("rohlik","bakery", 2.0,200));
            productService.saveProduct(new Product("cheese","dairy",35.0,270));
            productService.saveProduct(new Product("milk","dairy",20.0,100));
            productService.saveProduct(new Product("cucumber","dairy",10.0,90));
            productService.saveProduct(new Product("apricot","dairy",17.5,50));
            productService.saveProduct(new Product("spaghetti","pasta",15.0,50));
            productService.saveProduct(new Product("avocado","fruit",30.0,70));
        };
    } */

	/*
    @Bean
	CommandLineRunner run (UserService userService, RoleService roleService){
        return args -> {

		// create some users
            userService.saveUser(new User("petr@gmail.com","1234","Petr","A","777","Prague",new ArrayList<>()));
            userService.saveUser(new User("ilona@gmail.com","1234","Ilona","J","999","Prague",new ArrayList<>()));

		// assign the roles to the users
            userService.addRoleToUser("petr@gmail.com","ROLE_USER");
            userService.addRoleToUser("ilona@gmail.com","ROLE_USER");
        };
    }*/
}
