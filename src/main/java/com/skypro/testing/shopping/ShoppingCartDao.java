package com.skypro.testing.shopping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ShoppingCartDao {

  private final Map<UUID, List<Item>> usersCarts = new HashMap<>();

  public List<Item> getItems(UUID userId) {
    return usersCarts.getOrDefault(userId, List.of());
  }

  public void addItem(UUID userId, Item item) {
    usersCarts.computeIfAbsent(userId, uuid -> new ArrayList<>()).add(item);
  }

  public boolean removeItem(UUID userId, Item item) {
    List<Item> userItems = usersCarts.getOrDefault(userId, List.of());
    return userItems.remove(item);
  }
}
