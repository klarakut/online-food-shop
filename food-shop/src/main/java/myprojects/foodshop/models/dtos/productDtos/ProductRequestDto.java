package myprojects.foodshop.models.dtos.productDtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import myprojects.foodshop.models.dtos.RequestDto;

public class ProductRequestDto extends ProductDto implements RequestDto {

    @JsonCreator
    public ProductRequestDto(Long id,String name, String description, Double price, Integer quantity) {
        super(id, name, description, price, quantity);
    }

    @JsonCreator
    public ProductRequestDto(String name, String description, Double price, Integer quantity){
        super(null,name,description,price,quantity);
    }
}
