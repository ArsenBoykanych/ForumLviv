package aboikanych.forumlviv.ui.shops_services.mvp;

import java.util.List;

import aboikanych.forumlviv.base.mvp.BaseView;
import aboikanych.forumlviv.net.model.ShopService;

/**
 * Created by Boykanych on 05.06.2017.
 */

public interface ShopServiceView extends BaseView {

    void onShopsServicesLoaded(List<ShopService> shopsServices);
}
