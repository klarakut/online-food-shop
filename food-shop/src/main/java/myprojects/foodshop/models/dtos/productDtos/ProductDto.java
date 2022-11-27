package myprojects.foodshop.models.dtos.productDtos;

public abstract class ProductDto {
    public final Long id;

    public final String name;
    public final String description;
    public final Double price;
    public final Integer quantity;

    public ProductDto(Long id, String name, String description, Double price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}
