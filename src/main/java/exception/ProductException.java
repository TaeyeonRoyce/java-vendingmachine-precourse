package exception;

import static constant.CharacterConstant.*;
import static constant.NumberConstant.*;
import static constant.StringConstant.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import vendingmachine.model.Product;

public class ProductException {
	public static void isValidProduct(String userProducts) {
		isEmpty(userProducts);
		List<String> products = Arrays.asList(userProducts.split(PRODUCT_DIVIDER));
		for (String product : products) {
			isWrapped(product);
		}
	}

	private static void isEmpty(String userProducts) {
		if (userProducts.trim().length() == ZERO) {
			throw new IllegalArgumentException(PRODUCT_NAME_NULL);
		}
	}

	//[]로 감싸여 있는지 검증
	private static void isWrapped(String userProduct) {
		if (!userProduct.startsWith(PRODUCT_WRAPPER_LEFT)
				|| !userProduct.endsWith(PRODUCT_WRAPPER_RIGHT)) {
			throw new IllegalArgumentException(PRODUCT_WRAPPER_NULL);
		}
		userProduct = userProduct.substring(PRODUCT_WRAPPER_SIZE, userProduct.length() - PRODUCT_WRAPPER_SIZE);
		isDivided(userProduct);
	}

	//두개 이상의 상품이 ;으로 나뉘어 있는지 검증
	private static void isDivided(String userProduct) {
		if (userProduct.contains(PRODUCT_WRAPPER_LEFT)
			&& userProduct.contains(PRODUCT_WRAPPER_RIGHT)) {
			throw new IllegalArgumentException(PRODUCT_NOT_DIVIDED);
		}
		isProduct(userProduct);
	}

	private static void isProduct(String userProduct) {
		List<String> productDetail = Arrays.asList(userProduct.split(PRODUCT_DETAIL_DIVIDER));
		if (productDetail.size() != PRODUCT_DETAIL_AMOUNT) {
			throw new IllegalArgumentException(PRODUCT_DETAIL_UNMATCHED);
		}
		isValidPrice(productDetail.get(PRODUCT_PRICE_INDEX));
		isValidQuantity(productDetail.get(PRODUCT_QUANTITY_INDEX));
	}

	private static int isValidPrice(String productPrice) {
		try {
			PriceException.isValidPrice(productPrice);
			int price = Integer.parseInt(productPrice);
			PriceException.isProductPrice(price);
			return price;
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(PRODUCT_PRICE_PREFIX + e.getMessage());
		}
	}

	private static int isValidQuantity(String productQuantity) {
		try {
			QuantityException.isValidQuantity(productQuantity);
			int quantity = Integer.parseInt(productQuantity);
			return quantity;
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(PRODUCT_QUANTITY_PREFIX + e.getMessage());
		}
	}

	public static void isDuplicated(List<Product> productList) {
		List<String> productNames = new ArrayList<>();
		for (Product product : productList) {
			productNames.add(product.getName());
		}
		int distinctSize =
			productNames.stream()
			.map(productName -> productName.trim())
			.distinct()
			.collect(Collectors.toList()).size();
		if (productNames.size() != distinctSize) {
			productList.clear(); //중복 유무와 상관없이 생성된 ProductList를 초기화 해야함
			throw new IllegalArgumentException(PRODUCT_NAME_DUPLICATED);
		}
	}
}
