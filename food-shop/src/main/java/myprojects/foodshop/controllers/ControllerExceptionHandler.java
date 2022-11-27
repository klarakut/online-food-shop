package myprojects.foodshop.controllers;


import myprojects.foodshop.exceptions.DuplicateProductException;
import myprojects.foodshop.exceptions.EmptyBodyException;
import myprojects.foodshop.exceptions.EmptyBucketException;
import myprojects.foodshop.exceptions.IdNotFoundException;
import myprojects.foodshop.exceptions.InvalidIdException;
import myprojects.foodshop.exceptions.InvalidInputException;
import myprojects.foodshop.exceptions.NameMissingException;
import myprojects.foodshop.exceptions.NotEnoughItemsException;
import myprojects.foodshop.exceptions.NotMatchingPasswords;
import myprojects.foodshop.exceptions.OrderNotFoundException;
import myprojects.foodshop.exceptions.PasswordMissingException;
import myprojects.foodshop.exceptions.ProductNotFoundException;
import myprojects.foodshop.exceptions.ShortPasswordException;
import myprojects.foodshop.exceptions.ShortUsernameException;
import myprojects.foodshop.exceptions.UnknownErrorException;
import myprojects.foodshop.exceptions.UsernameAlreadyTakenException;
import myprojects.foodshop.exceptions.UsernameMissingException;
import myprojects.foodshop.models.dtos.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(DuplicateProductException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleDuplicateProductException(){
        return new ErrorResponseDto("Product already exists");
    }

    @ExceptionHandler(UnknownErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleUnknownErrorException() {
        return new ErrorResponseDto("Server error");
    }

    @ExceptionHandler(InvalidIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleRoleNotFoundException() {
        return new ErrorResponseDto("Invalid id");
    }

    @ExceptionHandler(IdNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleIdNotFoundException() {
        return new ErrorResponseDto("Product not found");
    }

    @ExceptionHandler(EmptyBodyException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponseDto handleEmptyBodyException() {
        return new ErrorResponseDto("Product is required");
    }

    @ExceptionHandler(InvalidInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleInvalidInputException() {
        return new ErrorResponseDto("Invalid data");
    }

    // order exceptions:
    @ExceptionHandler(NotEnoughItemsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleNotEnoughItemsException() {
        return new ErrorResponseDto("Not enough items");
    }

    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleProductNotFoundException() {
        return new ErrorResponseDto("Product not found");
    }

    @ExceptionHandler(EmptyBucketException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleEmptyBucketException() {
        return new ErrorResponseDto("Your bucket is empty");
    }

    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleOrderNotFoundException() {
        return new ErrorResponseDto("Order not found");
    }


    // user exceptions:
    @ExceptionHandler(UsernameMissingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleUsernameMissingException() {
        return new ErrorResponseDto("Username is required");
    }

    @ExceptionHandler(PasswordMissingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handlePasswordMissingException() {
        return new ErrorResponseDto("Password is required");
    }

    @ExceptionHandler(NameMissingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleNameMissingException() {
        return new ErrorResponseDto("Name is required");
    }

    @ExceptionHandler(NotMatchingPasswords.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleNotMatchingPasswords() {
        return new ErrorResponseDto("Not matching passwords");
    }

    @ExceptionHandler(UsernameAlreadyTakenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleUsernameAlreadyTakenException() {
        return new ErrorResponseDto("Username already exists");
    }

    @ExceptionHandler(ShortUsernameException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleShortUsernameException() {
        return new ErrorResponseDto("Username must be longer");
    }

    @ExceptionHandler(ShortPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleShortPasswordException() {
        return new ErrorResponseDto("Password must be longer");
    }

}
