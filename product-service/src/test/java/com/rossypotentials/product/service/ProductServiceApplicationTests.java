package com.rossypotentials.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rossypotentials.product.service.dto.ProductRequest;
import com.rossypotentials.product.service.dto.ProductResponse;
import com.rossypotentials.product.service.repository.ProductRepository;
import com.rossypotentials.product.service.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {
	@Container
	static MongoDBContainer mongoDBContainer =  new MongoDBContainer("mongo:4.4.2");
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private MockMvc mockMvc;



	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}
	@Test
	void shouldCreateProduct() throws Exception {
	ProductRequest productRequest =	getProductRequest();
	String productRequestString = objectMapper.writeValueAsString(productRequest);
	mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
			.contentType(MediaType.APPLICATION_JSON)
			.content(productRequestString))
			.andExpect(status().isCreated());
        Assertions.assertEquals(1, productRepository.findAll().size());

	}
	@Test
	void shouldGetAllProject() throws Exception {
		ProductResponse productResponse = getProductResponse();
		String productResponseString = objectMapper.writeValueAsString(productResponse);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productResponseString))
				.andExpect(status().isOk());
	}

	private ProductResponse getProductResponse() {
		return ProductResponse.builder()
				.id("669c37b68852c400f224795e")
				.name("brush")
				.description("Brushing off dirts")
				.price(BigDecimal.valueOf(1290))
				.build();
	}

	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("Brush")
				.description("Brushing off dirts")
				.price(BigDecimal.valueOf(1290))
				.build();
	}

}
