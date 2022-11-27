package myprojects.foodshop.service;


import lombok.extern.slf4j.Slf4j;
import myprojects.foodshop.exceptions.DuplicateProductException;
import myprojects.foodshop.exceptions.EmptyBodyException;
import myprojects.foodshop.exceptions.IdNotFoundException;
import myprojects.foodshop.exceptions.InvalidIdException;
import myprojects.foodshop.exceptions.InvalidInputException;
import myprojects.foodshop.exceptions.UnknownErrorException;
import myprojects.foodshop.models.Product;
import myprojects.foodshop.models.dtos.StatusResponseDto;
import myprojects.foodshop.models.dtos.productDtos.ProductCreateDto;
import myprojects.foodshop.models.dtos.productDtos.ProductRequestDto;
import myprojects.foodshop.models.dtos.productDtos.ProductResponseDto;
import myprojects.foodshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional // I need everything in this class to be transactional
@Slf4j // for log
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product saveProduct(Product product) {
        log.info("Saving new product {} to the database",product.getName());
        return productRepository.save(product);
    }

    @Override
    //public ProductResponseDto store(ProductRequestDto dto) {
    public ProductResponseDto store(ProductCreateDto dto) {
        if(dto.name.isEmpty() || dto.name == null){
            throw new EmptyBodyException();
        }

        boolean existingProduct = productRepository.findByName(dto.name).isPresent();
        if(existingProduct){
            throw new DuplicateProductException();
        }

        try {
            Product product = new Product(dto.name,dto.description,dto.price,dto.quantity);
            productRepository.save(product);
            ProductResponseDto productResponseDto = new ProductResponseDto(product.getId(),product.getName(),product.getDescription(),product.getPrice(),product.getQuantity());
            return productResponseDto;
        } catch (Exception e){
            throw new UnknownErrorException();
        }
    }

    @Override
    public StatusResponseDto destroy(Long id) {
        if (id <= 0){
            throw new InvalidIdException();
        }

        Product product = productRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
        productRepository.deleteById(product.getId());
        StatusResponseDto status = new StatusResponseDto("ok");
        return status;
    }

    @Override
    public ProductResponseDto update(Long id, ProductRequestDto dto) {
        if(id <= 0){
            throw new InvalidIdException();
        }
        if ((dto.name.isEmpty() || dto.name.isBlank()) || // dto.name == null
                (dto.description.isEmpty() || dto.description.isBlank()) ||
                (dto.price <= 0 || dto.price == null) ||
                (dto.quantity < 0|| dto.quantity == null)
        ){
            throw new InvalidInputException();
        }

        Product product = productRepository.findById(id).orElseThrow(() -> new IdNotFoundException());
        try {
            product.setDescription(dto.description);
            product.setName(dto.name);
            product.setQuantity(dto.quantity);
            product.setPrice(dto.price);
            productRepository.save(product);
            ProductResponseDto productResponseDto = new ProductResponseDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription(),
                    product.getPrice(),
                    product.getQuantity());
            return productResponseDto;
        } catch (Exception e){
            throw new UnknownErrorException();
        }
    }
}
