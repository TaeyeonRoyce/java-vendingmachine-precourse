package vendingmachine.exceptions;

import static constant.StringConstant.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;

import vendingmachine.model.VendingMachine;

public class PriceTest {

	@Test
	void isPriceNumberTest() {
		//given
		String userInput = "백원";

		//when
		VendingMachine vendingMachine = new VendingMachine();

		//then
		assertThatThrownBy(() -> {
			vendingMachine.setBalance(userInput);})
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(NOT_NUMBER);
	}

	@Test
	void isPricePositiveTest() {
		//given
		String userInput = "-1";

		//when
		VendingMachine vendingMachine = new VendingMachine();

		//then
		assertThatThrownBy(() -> {
			vendingMachine.setBalance(userInput);})
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(NOT_POSITIVE);
	}

	@Test
	void isPriceCoinValueTest() {
		//given
		String userInput = "111";

		//when
		VendingMachine vendingMachine = new VendingMachine();

		//then
		assertThatThrownBy(() -> {
			vendingMachine.setBalance(userInput);})
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining(NOT_COIN_VALUE);
	}

}