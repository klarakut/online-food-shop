package myprojects.foodshop.models.dtos.productDtos;

import myprojects.foodshop.models.Product;
import myprojects.foodshop.models.dtos.ResponseDto;

public class ProductResponseDto extends ProductDto implements ResponseDto {

    // public final Long id;
    public ProductResponseDto(Long id, String name, String description, Double price, Integer quantity) {
        super(id, name, description, price, quantity);
    }

    /*public ProductResponseDto(String name, String description, Integer price, Integer quantity) {
        super(name, description, price, quantity);
    }*/

    public ProductResponseDto(Product product) {
        super(product.getId(),product.getName(),product.getDescription(),product.getPrice(), product.getQuantity());
    }

    /*public ProductResponseDto(Product product) {
        super(product.getName(),product.getDescription(),product.getPrice(), product.getQuantity());
        this.id = product.getId();
    }*/
}
