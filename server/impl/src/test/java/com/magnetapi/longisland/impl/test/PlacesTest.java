package com.magnetapi.longisland.impl.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.magnet.longisland.api.PlacesController;
import com.magnet.longisland.api.PlacesResult;
import com.magnet.testutils.PlatformRunner;
import com.magnet.testutils.PlatformRunnerContext;

@RunWith(PlatformRunner.class)
@PlatformRunnerContext
public class PlacesTest {

  @Inject
  PlacesController placesController;

  @Test
  public void simpleTest() throws Exception {
    assertNotNull(placesController);
    PlacesResult result =
        placesController.findNearbyPlaces(
            37.365855f,       // latitude
            -122.017052f,     // longitude
            "Starbucks",      // name (optional)
            new ArrayList<String>(Arrays.asList(new String[]{"cafe"})),   // types (optiopnal)
            true,             // openNow (optional)
            null,             // minPriceLevel (optional)
            null);            // maxPriceLevel (optional)
    validateResult(result);
    while (result.getNextPageToken() != null) {
      result = placesController.findMorePlaces(result.getNextPageToken());
      validateResult(result);
    }
  }

  private void validateResult(PlacesResult result) {
    //System.out.println(result);
    assertNotNull(result);
    assertNotNull(result.getStatus());
    assertTrue(result.getStatus().toString(), PlacesResult.VALID_STATUSES.contains(result.getStatus()));
  }
	  
}
