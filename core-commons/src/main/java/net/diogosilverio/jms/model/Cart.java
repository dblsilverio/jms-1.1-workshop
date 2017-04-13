package net.diogosilverio.jms.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * A Simple e-commerce cart.
 * @author diogo
 *
 */
public class Cart implements Serializable {

	private static final long serialVersionUID = 8439251033322346658L;
	
	private String userId;
	private Map<Product, Integer> items = new HashMap<>();
	
	public Cart(){
		super();
	}
	
	public Cart(String userId) {
		super();
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void addProduct(Product product, Integer amount){
		this.items.put(product, amount);
	}
	
	public Boolean removeProduct(String sku){
		final Optional<Product> product = this.items.keySet().stream().filter(p -> p.getSku().equalsIgnoreCase(sku)).findFirst();
		
		if(product.isPresent()){
			return this.items.remove(product.get()).intValue() > 0;
		}
		
		return Boolean.FALSE;
	}
	
	public BigDecimal cartTotal(){
		
		final Optional<BigDecimal> totalAmount = this.items.entrySet()
											.stream()
												.map(p -> p.getKey().getPrice().multiply(new BigDecimal(p.getValue())))
												.reduce((acc, product) -> acc.add(product));
		
		return totalAmount.orElse(new BigDecimal(0.0));
	}
	

	public Map<Product, Integer> getItems() {
		return items;
	}

	public void setItems(Map<Product, Integer> items) {
		this.items = items;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Cart [userId=" + userId + ", items=" + items + "]";
	}
	
}
