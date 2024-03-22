package coms.model.product;

import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class ProductImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long imgId;
    
    private String name;
    
    private String type;

    @Column(name = "image_url")
    private String imageUrl;
    
   

    // Constructors
    public ProductImage() {
        super();
    }


	public ProductImage(Long imgId, String name, String type, String imageUrl) {
		this.imgId = imgId;
		this.name = name;
		this.type = type;
		this.imageUrl = imageUrl;
	}

	public Long getImgId() {
		return imgId;
	}



	public void setImgId(Long imgId) {
		this.imgId = imgId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}


	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
