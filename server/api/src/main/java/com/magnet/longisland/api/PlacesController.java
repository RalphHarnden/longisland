package com.magnet.longisland.api;

import java.util.List;

import javax.inject.Named;

import com.magnet.common.api.RemoteAccessible;
import com.magnet.common.api.SupportedTargets;
import com.magnet.controller.spi.Controller;
import com.magnet.inject.Contract;
import com.magnet.inject.Optional;

@RemoteAccessible
@Contract
public interface PlacesController extends Controller {

  /**
   * Find nearby places from Google Places API.
   * <br>
   * If the nextPageToken value is not null in the returned PlacesResult instance,
   * additional results can be fetched using findMorePlaces().
   * @param query string
   * @param latitude of current location (optional)
   * @param longitude of current location
   * @param radius distance in meters (optional)
   * @param types of places (optional)
   * @param openNow true if results restricted to those that are open now (optional)
   * @param minPriceLevel minimum price level (optional)
   * @param maxPriceLevel maximum price level (optional)
   * @return result set
   * @see #findMorePlaces(String)
   */
  public PlacesResult findPlaces(
      @Named("query")@Optional String query,
      @Named("latitude")@Optional Float latitude,
      @Named("longitude")@Optional Float longitude,
      @Named("radius")@Optional Integer radius,
      @Named("types")@Optional List<String> types,
      @Named("opennow")@Optional Boolean openNow,
      @Named("minpricelevel")@Optional Place.PriceLevel minPriceLevel,
      @Named("maxpricelevel")@Optional Place.PriceLevel maxPriceLevel);

  /**
   * Find nearby places from Google Places API.
   * <br>
   * If the nextPageToken value is not null in the returned PlacesResult instance,
   * additional results can be fetched using findMorePlaces().
   * @param latitude of current location
   * @param longitude of current location
   * @param name of places (optional)
   * @param types of places (optional)
   * @param openNow true if results restricted to those that are open now (optional)
   * @param minPriceLevel minimum price level (optional)
   * @param maxPriceLevel maximum price level (optional)
   * @return result set
   * @see #findMorePlaces(String)
   */
  public PlacesResult findNearbyPlaces(
      @Named("latitude")float latitude,
      @Named("longitude")float longitude,
      @Named("name")@Optional String name,
      @Named("types")@Optional List<String> types,
      @Named("opennow")@Optional Boolean openNow,
      @Named("minpricelevel")@Optional Place.PriceLevel minPriceLevel,
      @Named("maxpricelevel")@Optional Place.PriceLevel maxPriceLevel);

  /**
   * Get more places from prior query.
   * <br>
   * If the nextPageToken value is not null in the returned PlacesResult instance,
   * additional results can be fetched using this method.
   * @param nextPageToken token for next page; taken from prior PlacesResult.
   * @return next places.
   */
  public PlacesResult findMorePlaces(
      @Named("nextpagetoken")String nextPageToken);

}
