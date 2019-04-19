package com.etisalat.sampletask.bases.Presenters;

import com.etisalat.sampletask.bases.BasePresenter;
import com.etisalat.sampletask.bases.Controllers.IListPresenter;
import com.etisalat.sampletask.bases.Controllers.Listcontroller;

public class Food_infoPresenter extends BasePresenter<Listcontroller, IListPresenter>  {
    public Food_infoPresenter(IListPresenter listener) {
        super(listener);
    }
}
