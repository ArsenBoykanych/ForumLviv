package aboikanych.forumlviv.base.mvp;

public interface BaseView {

    /**
     * This is base method used to show progress during some background task
     * */
    void showProgress();

    /**
     * This is base method used to hide progress, this method is in tandem with showProgress()
     * */
    void hideProgress();

    /**
     * This is base method used to notify user about some network error
     * */
    void networkError();

    /**
     * This is base method used to notify user about some http request error
     * */
    void httpError(Exception e);

    void baseError(String errorDescription);
}
