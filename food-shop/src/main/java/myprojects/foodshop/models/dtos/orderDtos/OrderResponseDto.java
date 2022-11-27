package myprojects.foodshop.models.dtos.orderDtos;

import myprojects.foodshop.models.dtos.ResponseDto;

public class OrderResponseDto implements ResponseDto {
    public String status;

    public OrderResponseDto(String status) {
        this.status = status;
    }
}
