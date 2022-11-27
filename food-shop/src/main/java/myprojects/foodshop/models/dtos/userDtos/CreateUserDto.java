package myprojects.foodshop.models.dtos.userDtos;

public class CreateUserDto {

    public String email;
    public String password;
    public String passwordAgain;

    public String name;
    public String surname;
    public String phoneNumber;
    public String city;

    public CreateUserDto(String email, String password,String passwordAgain, String name, String surname, String phoneNumber, String city) {
        this.email = email;
        this.password = password;
        this.passwordAgain = passwordAgain;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.city = city;
    }
}
