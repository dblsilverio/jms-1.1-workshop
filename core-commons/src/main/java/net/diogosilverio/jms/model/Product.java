package net.diogosilverio.jms.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A product!
 * 
 * @author diogo
 *
 */
public class Product implements Serializable {

	private static final long serialVersionUID = -5430937102509972735L;
	
	private String sku;
	private String name;
	private BigDecimal price;
	
	public Product(String sku, String name, BigDecimal price) {
		super();
		this.sku = sku;
		this.name = name;
		this.price = price;
	}

	public String getSku() {
		return sku;
	}

	public String getName() {
		return name;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.equals(other.sku))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [sku=" + sku + ", name=" + name + ", price=" + price + "]";
	}
	
}
