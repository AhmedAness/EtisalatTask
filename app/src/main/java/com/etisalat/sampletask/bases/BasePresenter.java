package com.etisalat.sampletask.bases;

import com.etisalat.sampletask.bases.Interface.BaseControllerListener;
import com.etisalat.sampletask.bases.Interface.BasePresenterListener;

public abstract class BasePresenter<T extends BaseController,
        E extends BasePresenterListener>
        implements BaseControllerListener {

    protected E listener;
    protected T controller;

    public BasePresenter(E listener) {
        this.listener = listener;
    }
}