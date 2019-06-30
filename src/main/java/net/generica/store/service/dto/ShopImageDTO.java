package net.generica.store.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;
import net.generica.store.domain.enumeration.Size;

/**
 * A DTO for the {@link net.generica.store.domain.ShopImage} entity.
 */
public class ShopImageDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String herstArtNr;

    private Integer order;

    @NotNull
    private Size size;

    private String description;

    @Lob
    private byte[] image;

    private String imageContentType;

    private Long productId;

    private String productName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHerstArtNr() {
        return herstArtNr;
    }

    public void setHerstArtNr(String herstArtNr) {
        this.herstArtNr = herstArtNr;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ShopImageDTO shopImageDTO = (ShopImageDTO) o;
        if (shopImageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), shopImageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ShopImageDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", herstArtNr='" + getHerstArtNr() + "'" +
            ", order=" + getOrder() +
            ", size='" + getSize() + "'" +
            ", description='" + getDescription() + "'" +
            ", image='" + getImage() + "'" +
            ", product=" + getProductId() +
            ", product='" + getProductName() + "'" +
            "}";
    }
}
