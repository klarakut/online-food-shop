package myprojects.foodshop.models.dtos.cartDtos;

public class RemoveFromCartDto {
    public Long id;
    public int quantity;

    public RemoveFromCartDto(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }
}
