package myprojects.foodshop.models.dtos.cartDtos;

public class AddToCartDto {
    public Long id;
    public int quantity;

    public AddToCartDto(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
