package com.skypro.testing;

import com.skypro.testing.shopping.Item;
import com.skypro.testing.shopping.ShoppingCart;
import com.skypro.testing.shopping.ShoppingCartDao;
import java.util.UUID;

public class Main {

  public static void main(String[] args) {
    ShoppingCartDao shoppingCartDao = new ShoppingCartDao();
    ShoppingCart shoppingCart = new ShoppingCart(shoppingCartDao);
    UUID user1 = UUID.randomUUID();
    UUID user2 = UUID.randomUUID();
    Item item1 = new Item("test1", 100);
    Item item2 = new Item("test2", 200);
    Item item3 = new Item("test3", 400);
    shoppingCart.addToCart(user1,item1);
    shoppingCart.addToCart(user1,item1);
    shoppingCart.addToCart(user1,item1);
    shoppingCart.addToCart(user2, item2);
    shoppingCart.addToCart(user2, item3);
    System.out.println(shoppingCart.calculateTotalInCart(user1));
    System.out.println(shoppingCart.calculateCartItemsCount(user1));
    System.out.println(shoppingCart.calculateTotalInCart(user2));
    System.out.println(shoppingCart.calculateCartItemsCount(user2));
    shoppingCart.removeFromCart(user1, item1);
    System.out.println(shoppingCart.calculateTotalInCart(user1));
    System.out.println(shoppingCart.calculateCartItemsCount(user1));
  }
}
