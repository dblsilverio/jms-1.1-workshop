package net.diogosilverio.jms.client.standalone.utils;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

import net.diogosilverio.jms.model.Cart;
import net.diogosilverio.jms.model.Product;

/**
 * Dirty hand guy.
 * 
 * @author diogo
 *
 */
public class Generator {

	private static final Product[] PRODUCTS = new Product[] {
			new Product("58702", "Yamaha YHT-3920UBL 5.1-Channel Home Theater in a Box System with Bluetooth",
					new BigDecimal(429)),
			new Product("64823", "Monoprice 10565 Premium 5.1 Channel Home Theater System with Subwoofer",
					new BigDecimal(230)),
			new Product("66697", "Enclave Audio CineHome HD 5.1 Wireless Audio Home Theater System",
					new BigDecimal(1199)),
			new Product("19704",
					"MEGACRA Sound Bar 38-Inch 80Watt 2.0 Channel Bluetooth Home Theater Soundbar (Remote Control, 3D Audio Mode, 6 Drivers, Wirless and Wired)",
					new BigDecimal(159.99)),
			new Product("65746", "Sony BDVN5200W 1000W 5.1 Channel Full HD Blu-ray Disc Home Theater System",
					new BigDecimal(399)),
			new Product("6705", "Samsung UN65KS8000 65-Inch 4K Ultra HD Smart LED TV (2016 Model)",
					new BigDecimal(2799.99)),
			new Product("4184", "Samsung UN65KS8500 Curved 65-Inch 4K Ultra HD Smart LED TV (2016 Model)",
					new BigDecimal(1997.99)),
			new Product("31786", "Samsung UN55KU6600 Curved 55-Inch 4K Ultra HD Smart LED TV (2016 Model)",
					new BigDecimal(1047.99)),
			new Product("42", "LG Electronics 55UH7700 55-Inch 4K Ultra HD Smart LED TV (2016 Model)",
					new BigDecimal(1799.99)),
			new Product("2893", "Sony XBR43X800D 43-Inch 4K Ultra HD TV (2016 Model)", new BigDecimal(1299.99)),
			new Product("98723", "Sony XBR65X850D 65-Inch 4K Ultra HD Smart TV (2016 model)", new BigDecimal(1598)),
			new Product("29980", "LG Electronics 43UH6100 43-Inch 4K Ultra HD Smart LED TV (2016 Model)",
					new BigDecimal(649.99)),
			new Product("46151", "LG Electronics OLED65E6P Flat 65-Inch 4K Ultra HD Smart OLED TV (2016 Model)",
					new BigDecimal(4999.99)),
			new Product("13516", "Sony XBR55X700D 55-Inch HDR 4K Ultra HD TV (2016 Model)", new BigDecimal(798)),
			new Product("16154", "Samsung UN75JU7100 75-Inch 4K Ultra HD 3D Smart LED TV (2015 Model)",
					new BigDecimal(4999.99)),
			new Product("10159", "Samsung UN40KU6300 40-Inch 4K Ultra HD Smart LED TV (2016 Model)",
					new BigDecimal(417.97)),
			new Product("56995", "Samsung UN49KU6500 Curved 49-Inch 4K Ultra HD Smart LED TV (2016 Model)",
					new BigDecimal(899.99)),
			new Product("39781", "Sceptre U508CV-UMK 49-Inch 4K Ultra HD LED TV (2015 Model)", new BigDecimal(368.88)),
			new Product("91072", "Samsung UN43KU7000 43-Inch 4K Ultra HD Smart LED TV (2016 Model)",
					new BigDecimal(879.99)), };

	private static String userId() {
		return UUID.randomUUID().toString();
	}

	public static Cart generateCart() {

		int items = new Random().nextInt(5) + 1;

		final Cart cart = new Cart(userId());

		IntStream.range(0, items)
			.forEach(p -> cart.addProduct(PRODUCTS[new Random().nextInt(PRODUCTS.length)], new Random().nextInt(3)));

		return cart;
	}

}