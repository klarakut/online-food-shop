package myprojects.foodshop.models.dtos.orderDtos;

import myprojects.foodshop.models.dtos.ResponseDto;

public class StripeResponse implements ResponseDto {
    private String sessionId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public StripeResponse(String sessionId) {
        this.sessionId = sessionId;
    }

    public StripeResponse() {
    }
}
