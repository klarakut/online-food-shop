package myprojects.foodshop.models.dtos.roleDtos;

public class RoleToUserForm {

    private String email;
    private String roleName;

    public RoleToUserForm(String email, String roleName) {
        this.email = email;
        this.roleName = roleName;
    }

    public String getEmail() {
        return email;
    }

    public String getRoleName() {
        return roleName;
    }

}