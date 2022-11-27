package myprojects.foodshop.models.dtos.userDtos;

import myprojects.foodshop.models.dtos.ResponseDto;

public class UserResponseDto implements ResponseDto {

        public String name;

    public UserResponseDto(String name) {
            this.name = name;
        }

    public String getName() {
        return name;
    }
}
