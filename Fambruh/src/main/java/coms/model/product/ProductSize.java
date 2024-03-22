package coms.model.product;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import coms.repository.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSize {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sizeId;

    @Enumerated(EnumType.STRING)
    private Size sizeName;

    @NotNull(message = "isAvailable cannot be null")
    private boolean isAvailable;

    @ManyToOne
    private Product product;


}
