package coms.dto;

import coms.model.product.ProductImage;
import coms.model.product.ProductSize;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class ProductDto
{
    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "brand cannot be blank")
    private String brand;

    @Column(length = 2000)
    @NotBlank(message = "description cannot be blank")
    private String description;

    @NotBlank(message = "salt cannot be blank")
    private String salt;

    @NotNull(message = "available cannot be null")
    private int totalAvailable;

    @NotNull(message = "price cannot be null")
    private Double price;

    private Double productDiscountedPrice;

    @NotNull(message = "isAvailable cannot be null")
    private boolean isAvailable;


    private String mainImage;

    private String detailImage;

    //    @ElementCollection
//    @CollectionTable(name = "product_sizes", joinColumns = @JoinColumn(name = "pid"))
//    @Enumerated(EnumType.STRING)

    private Set<ProductSize> sizes = new HashSet<>();
}
