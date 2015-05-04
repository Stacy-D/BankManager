package bankManegerTest;

import static org.junit.Assert.*;

import org.junit.Test;

import bankManager.BankService;

public class BankServiceTest {

	@Test
	public void test() {
		BankService.createDatastore();
		System.out.println(BankService.findClient("Anny L"));
	//	BankService.setNewLimit("Ann Ja");
	}

}
