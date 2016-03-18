package com.ahmedjazzar.rosetta;

import android.content.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.*;

/**
 * Created by ahmedjazzar on /29/216.
 */

@RunWith(MockitoJUnitRunner.class)
public class LocalesDetectorTest {

    @Mock
    Context mMockContext;

    @Before
    public void setUp() throws Exception {
        assertNotNull(mMockContext.getResources());
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testFetchAvailableLocales() throws Exception {

    }

    @Test
    public void testGetCurrentLocale() throws Exception {

    }

    @Test
    public void testDetectMostClosestLocale() throws Exception {

    }

    @Test
    public void testValidateLocales() throws Exception {

    }
}