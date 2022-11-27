package myprojects.foodshop.models.dtos.productDtos;

public class ProductCreateDto {
    public final Long id;

    public final String name;
    public final String description;
    public final Double price;
    public final Integer quantity;

    public ProductCreateDto(Long id, String name, String description, Double price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }
}
