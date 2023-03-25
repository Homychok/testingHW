package com.skypro.testing.shopping;

import com.skypro.testing.shopping.exception.CartValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartDaoTest {
    private UUID userId;
    private Item item1;
    private Item item2;
    private Item item3;
    private Item item4;
    private Item item5;
    private Item item6;
    private Map<UUID, List<Item>> users;
    private ShoppingCartDao dao;
    @BeforeEach
    public void setUp() {
        //создаем тестовые данные
        dao = new ShoppingCartDao();
        userId = UUID.randomUUID();
        Item item1 = new Item("Евгений", 30);
        Item item2 = new Item("Марина", 60);
        Item item3 = new Item("Алина", 90);
        Item item4 = new Item("Ветерок", 100);
        Item item5 = new Item("Ежик", 0);
        Item item6 = new Item("", 10);
        users = new HashMap<>();
    }
    @Test
    public void testGetItemsReturnListItemsIfCartIsNotEmpty() {
        dao.addItem(userId, item1);
        dao.addItem(userId, item2);
        dao.addItem(userId, item3);
        //создаем список items и заполняем его данными нашего метода
        List<Item> items = dao.getItems(userId);
        Assertions.assertEquals(3, items.size());
        Assertions.assertTrue(items.contains(item1));
    }
    @Test
    public void testGetItemsReturnListItemsIfCartIsNullOrIsEmpty() {
        List<Item> items = dao.getItems(userId);
        Assertions.assertNotNull(items);
        Assertions.assertEquals(0, items.size());
    }
    @Test
    public void testAddItemAddAndReturnListItemsWhichUsersContained() {
        if (users.containsKey(userId)) {
            dao.addItem(userId, item1);
            dao.addItem(userId, item2);
            dao.addItem(userId, item3);
            List<Item> items = dao.getItems(userId);
            Assertions.assertEquals(3, items.size());
            Assertions.assertTrue(items.contains(item1));
        }
    }
    @Test
    public void testAddItemAddAndReturnListItemsForNewItemWithoutUserId() {
        UUID newUser = UUID.randomUUID();
        if (!users.containsKey(userId)) {
            dao.addItem(newUser, item4);
            List<Item> items = dao.getItems(newUser);
            Assertions.assertEquals(1, items.size());
            Assertions.assertTrue(items.contains(item4));
        }
    }
    @Test
    public void testAddItemReturnFailForEmptyItemPrice() {
        UUID newUser = UUID.randomUUID();
        dao.addItem(newUser, item5);
            for (Item item : dao.getItems(newUser)) {
                Assertions.assertTrue(item.getPrice() <= 0, "Price item введено некорректно");
            }
        }
    @Test
    public void testAddItemReturnFailForEmptyItemName() {
        UUID newUser = UUID.randomUUID();
        dao.addItem(newUser, item6);
        for (Item item : dao.getItems(newUser)) {
                Assertions.assertTrue(item.getName().isBlank(), "Имя item введено некорректно");
        }
    }
    @Test
    public void testRemoveItemReturnListItemsIfRemoveItem() {
        if (users.containsKey(userId)) {
            dao.addItem(userId, item1);
            dao.addItem(userId, item2);
            dao.addItem(userId, item3);
            List<Item> items = dao.getItems(userId);
            dao.removeItem(userId, item2);
            List<Item> newItem = dao.getItems(userId);
            Assertions.assertEquals(3, items.size());
            Assertions.assertEquals(2, newItem.size());
            Assertions.assertTrue(items.contains(item2));
            Assertions.assertTrue(newItem.contains(item1));
        }
    }
    @Test
    public void testRemoveItemForEmptyList() {
        List<Item> items = dao.getItems(userId);
        Assertions.assertNotNull(items);
        Assertions.assertTrue(items.size() == 0, "Удалить объект невозможно, список пуст");
    }
}