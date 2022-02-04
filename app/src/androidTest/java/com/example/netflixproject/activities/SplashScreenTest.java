package com.example.netflixproject.activities;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

import android.app.Activity;
import android.app.Instrumentation;
import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.example.netflixproject.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class SplashScreenTest
{
    @Rule
    public ActivityTestRule<SplashScreen> activityTestRule=new ActivityTestRule<>(SplashScreen.class);
    private SplashScreen screen=null;
    Instrumentation.ActivityMonitor monitor=getInstrumentation().addMonitor(SignInActivity.class.getName(),null,false);
    @Before
    public void setUp() throws Exception {
        screen=activityTestRule.getActivity();
    }
    @Test
    public void test()
    {
        View v=screen.findViewById(R.id.forgotpasswordtextview);
        Activity sigin=getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(sigin);

    }

    @After
    public void tearDown() throws Exception {
        screen=null;
    }
}