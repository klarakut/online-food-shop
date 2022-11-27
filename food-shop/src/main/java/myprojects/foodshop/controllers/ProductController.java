package myprojects.foodshop.controllers;

import myprojects.foodshop.models.dtos.ResponseDto;
import myprojects.foodshop.models.dtos.StatusResponseDto;
import myprojects.foodshop.models.dtos.productDtos.ProductCreateDto;
import myprojects.foodshop.models.dtos.productDtos.ProductRequestDto;
import myprojects.foodshop.models.dtos.productDtos.ProductResponseDto;
import myprojects.foodshop.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    // -> creation of the product
    // -> deleting product
    // -> actualisation of the product - actualisation of product quantity in stock, name, ...


    @PostMapping("/products")
    //public ResponseEntity<? extends ResponseDto> store(@RequestBody ProductRequestDto dto){
    public ResponseEntity<? extends ResponseDto> store(@RequestBody ProductCreateDto dto){
        ProductResponseDto product = productService.store(dto);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<? extends ResponseDto> destroy(@PathVariable("id") Long id) {

        StatusResponseDto status = productService.destroy(id);
        return new ResponseEntity<>(status, HttpStatus.resolve(201));
    }

    @PatchMapping("/products/{id}")
    public ResponseEntity<? extends ResponseDto> update(@PathVariable("id") Long id,
                                                        @RequestBody ProductRequestDto dto) {
        ProductResponseDto responseDto = productService.update(id, dto);
        return new ResponseEntity<>(responseDto, HttpStatus.resolve(200));
    }
}
