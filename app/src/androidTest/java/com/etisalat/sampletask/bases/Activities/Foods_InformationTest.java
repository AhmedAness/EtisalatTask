package com.etisalat.sampletask.bases.Activities;

import android.support.test.espresso.action.ViewActions;
import android.support.v7.widget.RecyclerView;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.rule.ActivityTestRule;

import com.etisalat.sampletask.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.util.regex.Pattern.matches;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.junit.Assert.*;

public class Foods_InformationTest {

    String time="";
    @Rule
    public ActivityTestRule<Foods_Information> informationTestRule = new ActivityTestRule<Foods_Information>(Foods_Information.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testCach(){
        // when click refresh
//        Espresso.onView(withId(R.id.Refresh)).perform(click());

        // check if last update time isn't null which give it from the shared pref
        if (getRVcount() > 0){
            onView(withId(R.id.Menu_Recycler)).perform(ViewActions.click());
        }
    }
    private int getRVcount(){
        RecyclerView recyclerView = (RecyclerView) informationTestRule.getActivity().findViewById(R.id.Menu_Recycler);
        return recyclerView.getAdapter().getItemCount();
    }

    @After
    public void tearDown() throws Exception {
    }
}