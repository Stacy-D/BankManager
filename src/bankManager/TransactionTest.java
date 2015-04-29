package BankMeneger;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TransactionTest {
	
	private Transaction transaction;

	@Before
	public void setUp() throws Exception {
		transaction = new Transaction(321);
	}

	@Test
	public void test() {
		assertEquals(321, transaction.getRemovedMoney());
	}

}
