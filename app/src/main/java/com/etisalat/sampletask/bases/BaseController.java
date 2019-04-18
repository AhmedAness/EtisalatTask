package com.etisalat.sampletask.bases;

import com.etisalat.sampletask.bases.Interface.BaseControllerListener;

public abstract class BaseController<T extends BaseControllerListener> {

    protected T listener;

    public BaseController(T listener) {
        this.listener = listener;
    }
}
