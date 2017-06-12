package aboikanych.forumlviv.ui.calbacks;

import java.util.List;

import aboikanych.forumlviv.net.model.ShopService;

public interface ForumActivityCallbacks {

    void setSearchVisibility(boolean visible);

    void listOfShopsToSearch(List<ShopService> searchServiceList);
}
