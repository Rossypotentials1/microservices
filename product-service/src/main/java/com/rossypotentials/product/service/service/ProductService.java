package com.rossypotentials.product.service.service;

import com.rossypotentials.product.service.dto.ProductRequest;
import com.rossypotentials.product.service.dto.ProductResponse;
import com.rossypotentials.product.service.model.Prodcut;
import com.rossypotentials.product.service.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    public void createProduct(ProductRequest productRequest) {
        Prodcut product = Prodcut.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProduct() {
        List<Prodcut> products = productRepository.findAll();
        return  products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Prodcut prodcut) {
        return ProductResponse.builder()
                .id(prodcut.getId())
                .name(prodcut.getName())
                .description(prodcut.getDescription())
                .price(prodcut.getPrice())
                .build();
    }
}
