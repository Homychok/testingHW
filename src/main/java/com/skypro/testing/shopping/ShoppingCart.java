package com.skypro.testing.shopping;

import com.skypro.testing.shopping.exception.CartValidationException;
import java.util.UUID;

public class ShoppingCart {

  private final ShoppingCartDao dao;

  public ShoppingCart(ShoppingCartDao dao) {
    this.dao = dao;
  }

  public void addToCart(UUID userId, Item item) {
    if (item == null || userId == null) {
      throw new CartValidationException("One of parameters is null");
    }
    dao.addItem(userId, item);
  }

  public void removeFromCart(UUID userId, Item item) {
    if (item == null || userId == null) {
      throw new CartValidationException("One of parameters is null");
    }
    if (!dao.removeItem(userId, item)) {
      throw new CartValidationException("Item not found in cart");
    }
  }

  public int calculateCartItemsCount(UUID userId) {
    return dao.getItems(userId).size();
  }

  public int calculateTotalInCart(UUID userId) {
    return dao.getItems(userId).stream().mapToInt(Item::getPrice).sum();
  }
}
