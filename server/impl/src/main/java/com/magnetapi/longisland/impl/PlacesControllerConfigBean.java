package com.magnetapi.longisland.impl;

import javax.inject.Named;

import com.magnet.common.api.Description;
import com.magnet.config.api.ConfigBean;
import com.magnet.inject.DefaultValue;

@ConfigBean
@Named("PlacesController")
public interface PlacesControllerConfigBean {

  public static final String PLACES_URL = "https://maps.googleapis.com/maps/api/place";
  public static final String NEARBYSEARCH_URL = PLACES_URL + "/nearbysearch/json";
  public static final String TEXTSEARCH_URL = PLACES_URL + "/textsearch/json";
  public static final String MORERESULTS_URL = PLACES_URL + "/search/json";
  public static final String DETAILS_URL = PLACES_URL + "/details/json";

  public static final String DEFAULT_RETRY_COUNT = "5";
  public static final String DEFAULT_RETRY_PAUSE = "1000";

  @Description("URL of Google Places Nearby Search API")
  @DefaultValue(NEARBYSEARCH_URL)
  public String getNearbySearchUrl();

  @Description("URL of Google Places Text Search API")
  @DefaultValue(TEXTSEARCH_URL)
  public String getTextSearchUrl();

  @Description("URL of Google Places Search API to get more results")
  @DefaultValue(MORERESULTS_URL)
  public String getMoreResultsUrl();

  @Description("URL of Google Places Details API")
  @DefaultValue(DETAILS_URL)
  public String getDetailsUrl();

  @Description("Google API key")
  public String getKey();

  @Description("Retry count")
  @DefaultValue(DEFAULT_RETRY_COUNT)
  public int getRetryCount();

  @Description("Retry pause")
  @DefaultValue(DEFAULT_RETRY_PAUSE)
  public int getRetryPause();

  public static class DefaultConfigBean implements PlacesControllerConfigBean {
    @Override
    public String getNearbySearchUrl() {
      return NEARBYSEARCH_URL;
    }

    @Override
    public String getTextSearchUrl() {
      return TEXTSEARCH_URL;
    }

    @Override
    public String getMoreResultsUrl() {
      return MORERESULTS_URL;
    }

    @Override
    public String getDetailsUrl() {
      return DETAILS_URL;
    }

    @Override
    public String getKey() {
      return "AIzaSyBHKLZl8JdTFyhUD-FeoYb1hFb6KjvqQSA";		//TODO: REMOVE THIS!!!
    }

    @Override
    public int getRetryCount() {
      return Integer.parseInt(DEFAULT_RETRY_COUNT);
    }

    @Override
    public int getRetryPause() {
      return Integer.parseInt(DEFAULT_RETRY_PAUSE);
    }
  }

}
