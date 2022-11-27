package myprojects.foodshop.service;

import myprojects.foodshop.models.Product;
import myprojects.foodshop.models.dtos.StatusResponseDto;
import myprojects.foodshop.models.dtos.productDtos.ProductCreateDto;
import myprojects.foodshop.models.dtos.productDtos.ProductRequestDto;
import myprojects.foodshop.models.dtos.productDtos.ProductResponseDto;


public interface ProductService {
    //ProductResponseDto store(ProductRequestDto dto);
    Product saveProduct(Product product);
    ProductResponseDto store(ProductCreateDto dto);
    StatusResponseDto destroy(Long id);
    ProductResponseDto update(Long id, ProductRequestDto dto);
}
