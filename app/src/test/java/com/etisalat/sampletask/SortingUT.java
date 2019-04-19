package com.etisalat.sampletask;

import com.etisalat.sampletask.bases.Utility.HelperFN;
import com.etisalat.sampletask.bases.model.item;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SortingUT {


    List<item> data ;
    item itm1 ;
    item itm2 ;
    item itm3 ;
    item itm4 ;
    item itm5 ;


    @Before
    public void InitTheListOfItems (){
        data = new ArrayList<>();

        itm1 =new item(4,"Margherita","155","Single cheese topping");
        itm2 =new item(2,"Double Cheese Margherita","155","Single cheese topping");
        itm3 =new item(3,"Fresh Veggie","155","Single cheese topping");
        itm4 =new item(5,"Peppy Paneer","155","Single cheese topping");
        itm5 =new item(1,"AAAAA","155","Single cheese topping");

        data.add(itm1);
        data.add(itm2);
        data.add(itm3);
        data.add(itm4);
        data.add(itm5);


    }
    /**
     *
    * test if item sorted alphabetically
    *
    * */
    @Test
    public void Sortalphabetically(){

        /**
         * sort data and retuen sorted list in same list acc to passing by reference in java
         * */
        HelperFN.getInstance().SortItemsbyalphabeticallyOrder(data);
        /**
        * validate on sorting
        * */
        assertEquals (data.get(0).toString(),itm5.toString());
        assertEquals (data.get(1).toString(),itm2.toString());
        assertEquals (data.get(2).toString(),itm3.toString());
        assertEquals (data.get(3).toString(),itm1.toString());
        assertEquals (data.get(4).toString(),itm4.toString());
    }


}
