/**
 * 
 */
package bankManegerTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import bankManager.Client;

/**
 * @author Natalia
 *
 */
public class ClientTest {
	
	private static final String NAME = "Natalia";
	private static final String ID = "12345678";
	private static final String PASSWORD = "32456nla";
	private static final int MONEY_ON_ACCOUNT = 1856372; 
	
	
	private Client client;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		client = new Client(NAME, ID, PASSWORD, MONEY_ON_ACCOUNT);
	}

	@Test
	public void test() {
		assertEquals("Test done successful", NAME, client.getName());
		assertEquals(ID, client.getId());
		assertEquals("Test done successful", PASSWORD, client.getPassword());
		assertEquals(MONEY_ON_ACCOUNT, client.getMoneyOnBanlAccount());
		assertEquals(1856373, client.addMoney(1));
	}

}
