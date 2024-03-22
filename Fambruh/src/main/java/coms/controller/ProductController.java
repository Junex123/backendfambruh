package coms.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import coms.dto.ProductDto;
import coms.util.FtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import coms.configuration.ImageUtil;
import coms.model.product.Product;
import coms.model.product.ProductImage;
import coms.model.product.comboproduct;
import coms.service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
@CrossOrigin(origins = "*")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping
    public ResponseEntity<?> addNewProduct(@RequestPart ProductDto productDto, @RequestPart List<MultipartFile> images, @RequestPart MultipartFile mainImage, @RequestPart MultipartFile detailImage) throws IOException {
        images.add(mainImage);
        images.add(detailImage);
        FtpUtil.uploadFile(images);
        Product product = Product.builder()
                .brand(productDto.getBrand())
                .price(productDto.getPrice())
                .description(productDto.getDescription())
                .productDiscountedPrice(productDto.getProductDiscountedPrice())
                .salt(productDto.getSalt())
                .sizes(productDto.getSizes())
                .isAvailable(productDto.isAvailable())
                .name(productDto.getName())
                .totalAvailable(productDto.getTotalAvailable())
                .build();
        Set<ProductImage> productImages = new HashSet<>();
        for (MultipartFile image : images) {
            ProductImage img = new ProductImage();
            img.setName(image.getOriginalFilename());
            img.setType(image.getContentType());
            img.setImageUrl(image.getOriginalFilename());
            productImages.add(img);
        }
        product.setProductImages(productImages);
        product.setMainImage(mainImage.getOriginalFilename());
        product.setDetailImage(detailImage.getOriginalFilename());


        Product savedProduct = this.productService.addProduct(product);
        return ResponseEntity.ok(savedProduct);
    }

    
    // Update existing product
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") Long id, @Valid @RequestBody Product product){
        Product updateProduct = this.productService.findProduct(id);
        updateProduct.setName(product.getName());
        updateProduct.setBrand(product.getBrand());
        updateProduct.setDescription(product.getDescription());
        updateProduct.setSalt(product.getSalt());
        updateProduct.setTotalAvailable(product.getTotalAvailable());
        updateProduct.setPrice(product.getPrice());
        this.productService.addProduct(updateProduct);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable("id") Long id){
        Product product = this.productService.findProduct(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        // Decompress image and set it to the product
//        ProductImage img =  new ProductImage();
//        img.setImageData(ImageUtil.decompressImage(product.getProductImages().iterator().next().getImageData()));
//        img.setImgId(product.getProductImages().iterator().next().getImgId());
//        img.setName(product.getProductImages().iterator().next().getName());
//        img.setType(product.getProductImages().iterator().next().getType());
//        product.getProductImages().clear();
//        product.getProductImages().add(img);
        return ResponseEntity.ok(product);
    }
    
    // Get all products
    @GetMapping
    public ResponseEntity<?> getAllProducts(){
        List<Product> allProducts = this.productService.findAllProducts();
        if(allProducts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
//        for (Product product : allProducts) {
//            // Decompress image and set it to each product
//            ProductImage img =  new ProductImage();
//            img.setImageData(ImageUtil.decompressImage(product.getProductImages().iterator().next().getImageData()));
//            img.setImgId(product.getProductImages().iterator().next().getImgId());
//            img.setName(product.getProductImages().iterator().next().getName());
//            img.setType(product.getProductImages().iterator().next().getType());
//            product.getProductImages().clear();
//            product.getProductImages().add(img);
//        }
        return ResponseEntity.ok(allProducts);
    }
    
    // Get products by name
    @GetMapping(value = {"/get/products/{name}"})
    public ResponseEntity<?> getProductByName(@PathVariable("name") String name,@PathVariable("name") String salt){
        List<Product> products = this.productService.findByNameOrSalt(name, salt);
        if(products.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
//        for (Product product : products) {
//            // Decompress image and set it to each product
//            ProductImage img =  new ProductImage();
//            img.setImageData(ImageUtil.decompressImage(product.getProductImages().iterator().next().getImageData()));
//            img.setImgId(product.getProductImages().iterator().next().getImgId());
//            img.setName(product.getProductImages().iterator().next().getName());
//            img.setType(product.getProductImages().iterator().next().getType());
//            product.getProductImages().clear();
//            product.getProductImages().add(img);
//        }
        return ResponseEntity.ok(products);
    }

    // Delete product by ID
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/product/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id){
        this.productService.deleteProductById(id);
        return ResponseEntity.ok().build();
    }
    
    // Set product availability
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/set-availability/product/{id}")
    public ResponseEntity<?> setAvailability(@PathVariable("id") Long id, @RequestBody Product product){
        Product updateProduct = this.productService.findProduct(id);
        if (updateProduct == null) {
            return ResponseEntity.notFound().build();
        }
        updateProduct.setAvailable(product.isAvailable());
        this.productService.addProduct(updateProduct);
        return ResponseEntity.ok().build();
    }
    
    // Get available products
    @GetMapping("/get/{name}")
    public ResponseEntity<?> getAvailable(@PathVariable("name") String name){
        List<Product> products = this.productService.findTrueProduct(name);
        if(products.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(products);
        }
    }

    // Add new combo product
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add/comboproduct")
    public ResponseEntity<?> addNewComboProduct(@RequestBody comboproduct comboProduct) {
        comboproduct savedComboProduct = productService.addComboProduct(comboProduct);
        return ResponseEntity.ok(savedComboProduct);
    }

    // Update existing combo product
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update/comboproduct/{id}")
    public ResponseEntity<?> updateComboProduct(@PathVariable("id") Long id, @Valid @RequestBody comboproduct comboProduct) {
        comboproduct existingComboProduct = productService.findComboProduct(id);
        if (existingComboProduct != null) {
            existingComboProduct.setProduct1(comboProduct.getProduct1());
            existingComboProduct.setProduct2(comboProduct.getProduct2());
            productService.addComboProduct(existingComboProduct);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get combo product by ID
    @GetMapping("/get/comboproduct/{id}")
    public ResponseEntity<?> getComboProductById(@PathVariable("id") Long id) {
        comboproduct comboProduct = productService.findComboProduct(id);
        if (comboProduct != null) {
            return ResponseEntity.ok(comboProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Get all combo products
    @GetMapping("/get/all-comboproducts")
    public ResponseEntity<?> getAllComboProducts() {
        List<comboproduct> comboProducts = productService.findAllComboProducts();
        return ResponseEntity.ok(comboProducts);
    }

    // Delete combo product by ID
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete/comboproduct/{id}")
    public ResponseEntity<?> deleteComboProduct(@PathVariable("id") Long id) {
        productService.deleteComboProductById(id);
        return ResponseEntity.ok().build();
    }
}
