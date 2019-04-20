package com.etisalat.sampletask.bases.Utility;

import android.content.SharedPreferences;

import com.etisalat.sampletask.bases.model.item;
import com.etisalat.sampletask.bases.model.menu;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author ahmed aniss
 * this is an helper function class which contains function could be used in any other class
 */
public class HelperFN {
    private static HelperFN opj;

    private HelperFN() {}
    public static HelperFN getInstance(){
        return opj == null ? new HelperFN():opj;
    }

    /**
     *
     * @param items
     * @return sorted list of items
     * this function is to sort list of items alphabetically
     */
    public List<item> SortItemsbyalphabeticallyOrder(List<item> items) {
        if (items.size() > 0) {
            Collections.sort(items, new Comparator<item>() {
                @Override
                public int compare(final item object1, final item object2) {
                    return object1.getName().compareTo(object2.getName());
                }
            });
            return items;
        }
        else throw new IllegalArgumentException("invalide length to sort");
    }

    /**
     *
     * @param items
     * @param lastUpdate
     * @param mPrefs
     *
     * this function is to save data in shared pref
     */
    public void Save_Data(List<item> items ,String lastUpdate,SharedPreferences mPrefs) {


        SharedPreferences.Editor prefsEditor = mPrefs.edit();
            Gson gson = new Gson();
            menu menu = new menu(items);
            String json = gson.toJson(menu);
            prefsEditor.putString("MyObject", json);
            prefsEditor.putString("UpdateTime", lastUpdate);
            prefsEditor.commit();

    }

    /**
     *
     * @param mPrefs
     * @return paire of list of items and last update time
     * this fucnction is to get data from shared pref
     */
    public CustomPair<List<item>,String> Get_Data(SharedPreferences mPrefs) {

            Gson gson = new Gson();
            String json = mPrefs.getString("MyObject", "");
            String lastUpdate = mPrefs.getString("UpdateTime", "");
            menu obj = gson.fromJson(json, menu.class);
            if (obj!=null){
                return new CustomPair<>(obj.getItem(),lastUpdate);
            }else return null;


    }
}
