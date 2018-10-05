package com.generic.retailer.app;

import static java.util.Objects.requireNonNull;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import com.generic.retailer.model.Book;
import com.generic.retailer.model.CD;
import com.generic.retailer.model.ClientProduct;
import com.generic.retailer.model.DVD;
import com.generic.retailer.model.Discount;
import com.generic.retailer.model.ProductDiscount;
import com.generic.retailer.model.Trolley;
import com.generic.retailer.model.enums.DiscountAvailability;
import com.generic.retailer.model.enums.DiscountType;
import com.generic.retailer.service.TrolleyService;
import com.generic.retailer.service.impl.TrolleyServiceImpl;

public final class Cli implements AutoCloseable {

	private static final int THURSDAY = 5;

	private static Book book;
	private static CD cd;
	private static DVD dvd;
	
	private TrolleyService trolleyService = new TrolleyServiceImpl();

	/**
	 * Initialize products needed to use
	 */
	private static void initProducts() {
		Discount thurs = new Discount("THURS", DiscountType.PERCENTAGE, 1, new BigDecimal(20.00),
				DiscountAvailability.DAY_OF_WEEK, THURSDAY);

		book = new Book("BOOK", 3, Calendar.getInstance(), 350, new BigDecimal(5.00), thurs);

		cd = new CD("CD", Calendar.getInstance(), 800, new BigDecimal(10.00), thurs);

		Discount twoForOne = new Discount("2 FOR 1", DiscountType.PERCENTAGE, 2, new BigDecimal(50.00));
		dvd = new DVD("DVD", Calendar.getInstance(), 1200, new BigDecimal(15.00), thurs, twoForOne);
	}

	public static Cli create(String prompt, BufferedReader reader, BufferedWriter writer, Calendar date) {
		requireNonNull(prompt);
		requireNonNull(reader);
		requireNonNull(writer);
		return new Cli(prompt, reader, writer, date);
	}

	public static Cli create(BufferedReader reader, BufferedWriter writer) {
		return new Cli(">", reader, writer, Calendar.getInstance());
	}

	private static final Predicate<String> WHITESPACE = Pattern.compile("^\\s{0,}$").asPredicate();

	private final String prompt;
	private final BufferedReader reader;
	private final BufferedWriter writer;
	private final Calendar date;

	private Cli(String prompt, BufferedReader reader, BufferedWriter writer, Calendar date) {
		this.prompt = prompt;
		this.reader = reader;
		this.writer = writer;
		this.date = date;
	}

	private void prompt() throws IOException {
		writeLine(prompt);
	}

	private String readLine() throws IOException {
		String line = reader.readLine();
		return line == null || WHITESPACE.test(line) ? "" : line;
	}

	private void writeLine(String line) throws IOException {
		writer.write(line);
		writer.newLine();
		writer.flush();
	}

	public void run() throws IOException {
		initProducts();
		
		String whatsYourName = "Hi! Welcome! What's your name?";
		writeLine(whatsYourName);
		prompt();
		String line = readLine();

		while (line.isEmpty()) {
			writeLine(whatsYourName);
			prompt();
			line = readLine();
		}

		Trolley trolley = new Trolley(line);
		trolley.setPurchaseDate(this.date);

		String whatBuy = "What would you like to buy? ";
		String options = "'B' for Book, 'C' for CD, 'D' for DVD, 'Q' for quit";
		

		writeLine(whatBuy + options);
		prompt();
		line = readLine();

		while (line.isEmpty() || (line.toUpperCase() != "Q")) {
			if (line.toUpperCase().equals("B")) {
				Long quantity = howMany();
				trolleyService.addProduct(trolley, book, quantity);
			} else if (line.toString().toUpperCase().equals("C")) {
				Long quantity = howMany();
				trolleyService.addProduct(trolley, cd, quantity);
			} else if (line.toString().toUpperCase().equals("D")) {
				Long quantity = howMany();
				trolleyService.addProduct(trolley, dvd, quantity);
			} else if (line.toString().toUpperCase().equals("Q")) {
				break;
			} else {
				writeLine("Sorry, product not found!");
			}

			writeLine("Would you like anything else? " + options);
			prompt();
			line = readLine();
		}
		
		writeLine("===== RECEIPT ======");
		
		NumberFormat df = NumberFormat.getCurrencyInstance(java.util.Locale.UK);
		df.setMinimumFractionDigits(2);
		
		for (ClientProduct product : trolley.getProducts()) {
			String receiptProduct = product.getProduct().getName();
			if (product.getAmount().compareTo(new BigDecimal(1)) > 0) {
				receiptProduct += " (x" + product.getAmount() + ")";
			}
			String receiptLine = String.format("%1$-10s%2$10s", receiptProduct, df.format(product.getTotalValue()));
			writeLine(receiptLine);
		}
		
		Map<Discount, BigDecimal> receiptDiscount = new HashMap<Discount, BigDecimal>();
		for (ClientProduct product : trolley.getProducts()) {
			if (product.getDiscounts() != null && !product.getDiscounts().isEmpty()) {
				for (ProductDiscount pDisc : product.getDiscounts()) {
					BigDecimal totalDisc = receiptDiscount.get(pDisc.getDiscount());
					if (totalDisc == null) {
						receiptDiscount.put(pDisc.getDiscount(), pDisc.getTotalPrice());
					} else {
						receiptDiscount.put(pDisc.getDiscount(), totalDisc.add(pDisc.getTotalPrice()));
					}
				}
			}
		}
		if (!receiptDiscount.isEmpty()) {
			for (Discount d : receiptDiscount.keySet()) {
				String totalDisc = "-" + df.format(receiptDiscount.get(d));
				String discountLine = String.format("%1$-10s%2$10s", d.getDiscountName(), totalDisc);
				writeLine(discountLine);
			}
		}
		
		writeLine("====================");
		String receiptTotal = String.format("%1$-10s%2$10s", "TOTAL", df.format(trolley.getTotal()));
		writeLine(receiptTotal);
		
		writeLine(String.format("Thank you " + trolley.getClient() + " for visiting Generic Retailer, your total is %s", trolley.getTotal()));
	}

	/**
	 * Ask and return the quantity
	 * 
	 * @return
	 * @throws IOException
	 */
	private Long howMany() throws IOException {
		String howMany = "How many?";
		
		String line;
		writeLine(howMany);
		prompt();
		line = readLine();

		Long quantity = null;
		try {
			quantity = Long.parseLong(line);
		} catch (Exception e) {
			writeLine("Sorry, quantity invalid! " + howMany);
			prompt();
			line = readLine();
		}
		
		while (line.isEmpty() || (quantity == null)) {
			try {
				quantity = Long.parseLong(line);
			} catch (Exception e) {
				writeLine("Sorry, quantity invalid! " + howMany);
				prompt();
				line = readLine();
			}
		}
		return quantity;
	}

	@Override
	public void close() throws Exception {
		reader.close();
		writer.close();
	}

}
