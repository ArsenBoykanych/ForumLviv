package aboikanych.forumlviv.base.mvp;

public interface BasePresenter<T> {

    /**
     * Method that controls lifecycle of the view.
     * It gets called in view's onResume() method
     * */
    void onResume();

    /**
     * Method that controls lifecycle of the view.
     * It gets called in view's onPause() method
     * */
    void onPause();

    /**
     * Method that controls lifecycle of the view.
     * It gets called in view's onDestroy() method
     * */
    void onDestroy();

    /**
     * Method uses to attach view to presenter
     * It gets called by View layer when it's created
     * */
    void attachView(T view);
}
