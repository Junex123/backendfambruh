package coms.model.product;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import coms.repository.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pid;

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
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "product_size_table",
            joinColumns = {
                    @JoinColumn(name = "product_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "size_id")
            }
    )
    private Set<ProductSize> sizes;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "product_images",
            joinColumns = {
                    @JoinColumn(name = "product_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "image_id")
            }
    )
    private Set<ProductImage> productImages = new HashSet<>();

}
