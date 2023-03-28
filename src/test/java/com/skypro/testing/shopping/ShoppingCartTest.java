package com.skypro.testing.shopping;

import com.skypro.testing.shopping.exception.CartValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ShoppingCartTest {
    @Mock
    private ShoppingCartDao dao;
    @InjectMocks
    private ShoppingCart shoppingCart;
    private UUID userId;
    private Map<UUID, List<Item>> users;
//    private Item item;
    private  Item item1 = new Item("Евгений", 30);
    private Item item2 = new Item("Марина", 60);
    private Item item3 = new Item("Алина", 90);
    private Item item4 = new Item("Ветерок", 100);
    private Item item5 = new Item("Ежик", 0);
    private Item item6 = new Item("", 10);
    @Spy
    List<Item> items = new ArrayList<>();


    @BeforeEach
    public void setUp() {
        //создаем тестовые данные
        userId = UUID.randomUUID();
        users = new HashMap<>();
        users.put(userId, new ArrayList<>());

    }
    @Test
    public void testAddToCartItem() {
        doNothing().when(dao).addItem(userId,item1);
        shoppingCart.addToCart(userId, item1);
        verify(dao, only()).addItem(userId,item1);
    }
    @Test
    public void testAddToCartInOrder() {
        InOrder inOrder = inOrder(dao);
        shoppingCart.addToCart(userId,item1);
        inOrder.verify(dao, times(1)).addItem(userId,item1);
    }

    @Test
    public void testRemoveFromCartWhenItemsHasEmptyField() {
        Assertions.assertThrows(CartValidationException.class, () -> shoppingCart.removeFromCart(userId, new Item("", 100)));
    }
    @Test

    public void testRemoveFromCartWhenItemsHasNullUserId() {
        Assertions.assertThrows(CartValidationException.class, () -> shoppingCart.addToCart(null, item1));
    }
    @Test
    public void removeFromCart2() {
        when(dao.removeItem(userId, item2)).thenReturn(true);
        shoppingCart.removeFromCart(userId, item2);
        verify(dao, only()).removeItem(userId, item2);
    }
    @Test
    public void testCalculateCartItemsCountForUsersFromList() {
        items.add(item1);
        items.add(item3);
        items.add(item2);
        items.add(item4);
        Mockito.when(dao.getItems(userId)).thenReturn(items);
        shoppingCart.calculateCartItemsCount(userId);
        Assertions.assertEquals(4, shoppingCart.calculateCartItemsCount(userId));

    }
    @Test
    public void testCalculateCartItemsCountForUsersFromList2() {
        items.add(item4);
        when(dao.getItems(userId)).thenReturn(items);
        int size = shoppingCart.calculateCartItemsCount(userId);
        verify(dao, only()).getItems(userId);
    }
    @Test
    void testWhenCalculateCartItemsWhenItemsHasNullUserId(){
        Mockito.when(dao.getItems(null)).thenReturn(items);
        shoppingCart.calculateCartItemsCount(null);
        Assertions.assertEquals(0, shoppingCart.calculateCartItemsCount(null));
    }
    @Test
    public void calculateTotalInCartForUsersFromList() {
        items.add(item1);
        items. add(item2);
        Mockito.when(dao.getItems(userId)).thenReturn(items);
        shoppingCart.calculateTotalInCart(userId);
        Assertions.assertEquals(90, shoppingCart.calculateTotalInCart(userId));    }
    @Test
    public void calculateTotalInCartWhenItemsHasNullUserId() {
        Mockito.when(dao.getItems(null)).thenReturn(items);
        shoppingCart.calculateTotalInCart(null);
        Assertions.assertEquals(0, shoppingCart.calculateTotalInCart(null));
    }
}
