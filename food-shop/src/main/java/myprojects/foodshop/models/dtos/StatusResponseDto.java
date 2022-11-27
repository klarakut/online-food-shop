package myprojects.foodshop.models.dtos;

public class StatusResponseDto implements ResponseDto{

    public final String status;


    public StatusResponseDto(String status) {
        this.status = status;
    }
}
