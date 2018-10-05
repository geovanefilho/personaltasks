package com.generic.retailer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Calendar;

import org.junit.Test;

import com.generic.retailer.app.Cli;

public class CliTest {

	private static BufferedReader reader(String... lines) {
		StringBuilder builder = new StringBuilder();
		Arrays.stream(lines).forEach(line -> builder.append(line).append(lineSep()));
		return new BufferedReader(new StringReader(builder.toString()));
	}

	private static String lineSep() {
		return System.lineSeparator();
	}

	/*
	 * The receipt format should be as per below:
	 *
	 * "===== RECEIPT ======",
	 * "DVD           £15.00",
	 * "CD            £10.00",
	 * "BOOK           £5.00",
	 * "THURS         -£6.00",
	 * "====================",
	 * "TOTAL         £24.00"
	 */
	private static void assertReceipt(StringWriter writer, String... expected) {
		String[] obtained = writer.toString().split(System.lineSeparator());
		int expectedNumItems = expected.length - 3;
		String[] items = new String[expectedNumItems];
		int numItems = 0;
		boolean receiptHeader = false;
		boolean receiptEnd = false;
		String total = "";
		for (int i = 0; i < obtained.length; i++) {
			if (!receiptHeader) {
				if ("===== RECEIPT ======".equals(obtained[i])) {
					receiptHeader = true;
				}
				// Everything before receipt header is ignored
			} else if (!receiptEnd) {
				if ("====================".equals(obtained[i])) {
					receiptEnd = true;
				} else {
					if (numItems == expectedNumItems) {
						fail("Too many items");
					}
					items[numItems] = obtained[i];
					numItems++;
				}
			} else {
				total = obtained[i];
				break;
			}
		}
		assertThat(receiptHeader).isTrue();
		assertThat(receiptEnd).isTrue();
		assertThat(items).containsExactlyInAnyOrder(Arrays.copyOfRange(expected, 1, expectedNumItems + 1));
		assertThat(total).isEqualTo(expected[expected.length - 1]);
	}

	@Test
	public void testReceipt() throws IOException {
		BufferedReader reader = reader(
				"Teste",
				"c", "1",
				"d", "1",
				"b", "1",
				"q");

		StringWriter writer = new StringWriter();
		Calendar notThursday = Calendar.getInstance();
		if (notThursday.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			notThursday.add(Calendar.DAY_OF_MONTH, 1);
		}

		Cli cli = Cli.create(">", reader, new BufferedWriter(writer), notThursday);
		cli.run();
		assertReceipt(writer,
				"===== RECEIPT ======",
				"CD            £10.00",
				"DVD           £15.00",
				"BOOK           £5.00",
				"====================",
				"TOTAL         £30.00");
	}

	@Test
	public void testAggregatedReceipt() throws IOException {
		BufferedReader reader = reader(
				"Teste",
				"c", "2",
				"b", "1",
				"q");

		StringWriter writer = new StringWriter();
		Calendar notThursday = Calendar.getInstance();
		if (notThursday.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			notThursday.add(Calendar.DAY_OF_MONTH, 1);
		}
		Cli cli = Cli.create(">", reader, new BufferedWriter(writer), notThursday);
		cli.run();
		assertReceipt(writer,
				"===== RECEIPT ======",
				"CD (x2)       £20.00",
				"BOOK           £5.00",
				"====================",
				"TOTAL         £25.00");
	}

	@Test
	public void testDiscountTwoForOne() throws IOException {
		BufferedReader reader = reader(
				"Teste",
				"d", "2",
				"b", "1",
				"q");

		StringWriter writer = new StringWriter();
		Calendar notThursday = Calendar.getInstance();
		if (notThursday.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
			notThursday.add(Calendar.DAY_OF_MONTH, 1);
		}
		Cli cli = Cli.create(">", reader, new BufferedWriter(writer), notThursday);
		cli.run();
		assertReceipt(writer,
				"===== RECEIPT ======",
				"DVD (x2)      £30.00",
				"BOOK           £5.00",
				"2 FOR 1      -£15.00",
				"====================",
				"TOTAL         £20.00");
	}

	@Test
	public void testDiscountThursdays() throws IOException {
		BufferedReader reader = reader(
				"Teste",
				"d", "1",
				"c", "1",
				"b", "1",
				"q");

		StringWriter writer = new StringWriter();
		Calendar thursday = Calendar.getInstance();
		while (!(thursday.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)) {
			thursday.add(Calendar.DAY_OF_MONTH, 1);
		}
		Cli cli = Cli.create(">", reader, new BufferedWriter(writer), thursday);
		cli.run();
		assertReceipt(writer,
				"===== RECEIPT ======",
				"DVD           £15.00",
				"CD            £10.00",
				"BOOK           £5.00",
				"THURS         -£6.00",
				"====================",
				"TOTAL         £24.00");
	}

	@Test
	public void testDiscount2For1OnThursdays() throws IOException {
		BufferedReader reader = reader(
				"Teste",
				"d", "2",
				"b", "1",
				"q");

		StringWriter writer = new StringWriter();
		Calendar thursday = Calendar.getInstance();
		while (!(thursday.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY)) {
			thursday.add(Calendar.DAY_OF_MONTH, 1);
		}
		Cli cli = Cli.create(">", reader, new BufferedWriter(writer), thursday);
		cli.run();
		assertReceipt(writer,
				"===== RECEIPT ======",
				"DVD (x2)      £30.00",
				"BOOK           £5.00",
				"2 FOR 1      -£15.00",
				"THURS         -£1.00",
				"====================",
				"TOTAL         £19.00");
	}
}
