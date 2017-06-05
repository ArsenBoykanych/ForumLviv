package aboikanych.forumlviv.ui.home.mvp;

import java.util.List;

import aboikanych.forumlviv.base.mvp.BaseView;
import aboikanych.forumlviv.net.model.NPEModel;

/**
 * Created by Boykanych on 05.06.2017.
 */

public interface NPEView extends BaseView {

    void onNPELoaded(List<NPEModel> npeList);
}
