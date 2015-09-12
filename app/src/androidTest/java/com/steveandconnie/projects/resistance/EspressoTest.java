package com.steveandconnie.projects.resistance;

import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.support.test.InstrumentationRegistry;

import org.junit.Before;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

/**
 * Created by Steve on 9/11/15.
 */
public class EspressoTest extends ActivityInstrumentationTestCase2<CreateGame> {

    private CreateGame game;

    public EspressoTest() {
        super(CreateGame.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        game = getActivity();
    }

    public void testStartGame() {
        // create a game name
        onView(withId(R.id.gameName)).perform(typeText("yoyo"), closeSoftKeyboard());

        // grab all player edit texts and enter names
//        onView(withParent(R.id.playerNamesGroup))

        // add a player
        onView(withId(R.id.addPlayerBtn)).perform(scrollTo(), click());

        // wait in order to give visual feedback
        SystemClock.sleep(2000);
    }

}
