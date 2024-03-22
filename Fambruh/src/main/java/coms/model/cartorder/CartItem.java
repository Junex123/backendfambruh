package coms.model.cartorder;

import javax.persistence.*;

import coms.model.user.User;
import coms.repository.Size;
import coms.model.product.*;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @OneToOne
    private Product product;

    @OneToOne
    private comboproduct comboproduct;

    @Enumerated(EnumType.STRING) // Assuming Size is an Enum
    private Size size; // Add this field to store the size of the product
    
    @ManyToOne(cascade = CascadeType.ALL)
    private ProductQuantity productQuantity;
    
	public CartItem() {
		super();
	}


	


	public CartItem(User user, Product product, coms.model.product.comboproduct comboproduct, Size size,
			ProductQuantity productQuantity, int quantity) {
		super();
		
		this.user = user;
		this.product = product;
		this.comboproduct = comboproduct;
		this.size = size;
		this.productQuantity = productQuantity;
		this.quantity = quantity;
	}





	public ProductQuantity getProductQuantity() {
		return productQuantity;
	}





	public void setProductQuantity(ProductQuantity productQuantity) {
		this.productQuantity = productQuantity;
	}





	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public comboproduct getComboproduct() {
		return comboproduct;
	}

	public void setComboproduct(comboproduct comboproduct) {
		this.comboproduct = comboproduct;
	}


	public Size getSize() {
		return size;
	}


	public void setSize(Size size) {
		this.size = size;
	}



    private int quantity; // Add quantity field

    // Constructor and other methods...

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    
    
}
