package com.jballou.getshopsigns.storage;

import com.google.gson.reflect.TypeToken;
import com.jballou.getshopsigns.cache.BlockSearchCache;
import com.jballou.getshopsigns.records.BlockGroup;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BlockStore extends Store<List<BlockGroup>> {
  private static BlockStore instance;
  private final BlockSearchCache cache = new BlockSearchCache();
  private List<BlockGroup> blockEntries = new ArrayList<>();

  private BlockStore() {
    super("blocks");

    List<BlockGroup> entries = this.read();
    if (entries == null) {
      return;
    }

    this.blockEntries = entries;
    this.updateCache(entries);
  }

  static BlockStore getInstance() {
    if (instance == null) {
      instance = new BlockStore();
    }

    return instance;
  }

  private void updateCache(List<BlockGroup> data) {
    this.cache.processGroupedList(data);
  }

  @Override
  public List<BlockGroup> get() {
    return this.blockEntries;
  }

  @Override
  Type getType() {
    return new TypeToken<List<BlockGroup>>() {}.getType();
  }
}
