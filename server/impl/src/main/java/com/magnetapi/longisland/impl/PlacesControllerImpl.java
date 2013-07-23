package com.magnetapi.longisland.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.codehaus.jackson.map.ObjectMapper;

import com.magnet.common.PlatformRuntimeException;
import com.magnet.config.api.ConfiguredBy;
import com.magnet.longisland.api.Place;
import com.magnet.longisland.api.PlaceBuilder;
import com.magnet.longisland.api.PlacesController;
import com.magnet.longisland.api.PlacesResult;
import com.magnet.longisland.api.PlacesResultBuilder;
import com.magnet.model.api.common.EntityFactory;

@SuppressWarnings("unused")
@Singleton
@ConfiguredBy(value=PlacesControllerConfigBean.class, defaultConfig=PlacesControllerConfigBean.DefaultConfigBean.class)
public class PlacesControllerImpl implements PlacesController {

  private PlacesControllerConfigBean config;

  @Inject
  public PlacesControllerImpl(PlacesControllerConfigBean config) {
    this.config = config;
  }

  @Override
  public PlacesResult findPlaces(
      String search,
      Float latitude,
      Float longitude,
      Integer radius,
      List<String> types,
      Boolean openNow,
      Place.PriceLevel minPriceLevel,
      Place.PriceLevel maxPriceLevel) {
    PlacesResultHolder resultHolder;
    BufferedReader in = null;
    try {
      if (search == null) {
        throw new IllegalArgumentException(
            LogMessagesSingleton.getResources().searchQueryNotSpecified());
      }
      StringBuilder query = new StringBuilder(config.getTextSearchUrl());
      query.append("?key=").append(config.getKey());
      query.append("&query=").append(URLEncoder.encode(search, Charset.defaultCharset().name()));
      query.append("&sensor=true");
      if ((latitude != null) && (longitude != null) && (radius != null)) {
        query.append("&location=").append(latitude).append(",").append(longitude);
        query.append("&radius=").append(radius);
      }
      if ((openNow != null) && openNow) {
        query.append("&opennow");

      }
      if ((types != null) && !types.isEmpty()) {
        query.append("&types=");
        boolean first = true;
        for (String type: types) {
          if (first) {
            first = false;
          } else {
            query.append("|");
          }
          query.append(URLEncoder.encode(type, Charset.defaultCharset().name()));
        }
      }
      if (minPriceLevel != null) {
        query.append("&minprice=").append(Place.PriceLevel.toValue(minPriceLevel));
      }
      if (maxPriceLevel != null) {
        query.append("&maxprice=").append(Place.PriceLevel.toValue(maxPriceLevel));
      }
      resultHolder = doQuery(query.toString());
    } catch(IOException ex) {
      throw new PlatformRuntimeException(String.valueOf(ex), ex);
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException ignore) {
          LogMessagesSingleton.get().swallowing(ignore);
        }
      }
    }
    return buildResult(resultHolder);
  }

  @Override
  public PlacesResult findNearbyPlaces(
      float latitude,
      float longitude,
       String name,
       List<String> types,
       Boolean openNow,
       Place.PriceLevel minPriceLevel,
       Place.PriceLevel maxPriceLevel) {
    PlacesResultHolder resultHolder;
    BufferedReader in = null;
    try {
      StringBuilder query = new StringBuilder(config.getNearbySearchUrl());
      query.append("?key=").append(config.getKey());
      query.append("&location=").append(latitude).append(",").append(longitude).append("&sensor=true");
      query.append("&rankby=distance");
      if (name != null) {
        query.append("&name=").append(URLEncoder.encode(name, Charset.defaultCharset().name()));
      }
      if ((openNow != null) && openNow) {
        query.append("&opennow");

      }
      if ((types != null) && !types.isEmpty()) {
        query.append("&types=");
        boolean first = true;
        for (String type: types) {
          if (first) {
            first = false;
          } else {
            query.append("|");
          }
          query.append(URLEncoder.encode(type, Charset.defaultCharset().name()));
        }
      }
      if (minPriceLevel != null) {
        query.append("&minprice=").append(Place.PriceLevel.toValue(minPriceLevel));
      }
      if (maxPriceLevel != null) {
        query.append("&maxprice=").append(Place.PriceLevel.toValue(maxPriceLevel));
      }
      resultHolder = doQuery(query.toString());
    } catch(IOException ex) {
      throw new PlatformRuntimeException(String.valueOf(ex), ex);
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException ignore) {
          LogMessagesSingleton.get().swallowing(ignore);
        }
      }
    }
    return buildResult(resultHolder);
 }

  @Override
  public PlacesResult findMorePlaces(String nextPageToken) {
    PlacesResult result = null;
    PlacesResultHolder resultHolder;
    try {
      StringBuilder query = new StringBuilder(config.getMoreResultsUrl());
      query.append("?key=").append(config.getKey());
      query.append("&sensor=true");
      query.append("&pagetoken=").append(nextPageToken);
      for (int i = 0; i <= config.getRetryCount(); i++) {
        resultHolder = doQuery(query.toString());
        result = buildResult(resultHolder);
        if (result.getStatus() != PlacesResult.Status.INVALID_REQUEST) {
          break;
        }
        try {
          Thread.sleep(config.getRetryPause());
        } catch (InterruptedException ignore) {
          LogMessagesSingleton.get().swallowing(ignore);
        }
      }
    } catch(IOException ex) {
      throw new PlatformRuntimeException(String.valueOf(ex), ex);
    }
    return result;
  }

  private PlacesResultHolder doQuery(String queryString) throws IOException {
    URL url = new URL(queryString);
    URLConnection conn = url.openConnection();
    StringBuilder sb;
    BufferedReader in = null;
    try {
      in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
      sb = new StringBuilder();
      for (String inputLine = in.readLine(); inputLine != null; inputLine = in.readLine())
        sb.append(inputLine).append("\n");
    } finally {
      if (in != null) {
        in.close();
      }
    }
    return parseResult(sb.toString());
  }

  private PlacesResultHolder parseResult(String content) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    // mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    return mapper.readValue(content, PlacesResultHolder.class);
  }

  @SuppressWarnings("unchecked")
  private PlacesResult buildResult(PlacesResultHolder resultHolder) {
    PlacesResultBuilder<PlacesResult, ?> resultBuilder =
        (PlacesResultBuilder<PlacesResult, ?>)EntityFactory.createNodeBuilder(PlacesResult.class);
    resultBuilder.nextPageToken(resultHolder.getNext_page_token()).status(PlacesResult.Status.valueOf(resultHolder.getStatus()));
    PlacesResultHolder.Result[] places = resultHolder.getResults();
    if ((places != null) && (places.length > 0)) {
      List<Place> placeList = new ArrayList<Place>(places.length);
      for (PlacesResultHolder.Result result: resultHolder.getResults()) {
        PlaceBuilder<Place, ?> placeBuilder =
            (PlaceBuilder<Place, ?>)EntityFactory.createNodeBuilder(Place.class);
        if (result.getGeometry() != null) {
          placeBuilder.latitude(result.getGeometry().getLocation().getLat());
          placeBuilder.longitude(result.getGeometry().getLocation().getLng());
        }
        placeBuilder.icon(result.getIcon());
        placeBuilder.name(result.getName());
        if (result.getOpening_hours() != null) {
          placeBuilder.open(result.getOpening_hours().getOpen_now());
        }
        placeBuilder.priceLevel((result.getPrice_level() == null) ? null : Place.PriceLevel.fromValue(result.getPrice_level()));
        placeBuilder.rating(result.getRating());
        placeBuilder.vicinity(result.getVicinity());
        placeBuilder.formattedAddress(result.getFormatted_address());
        placeBuilder.reference(result.getReference());
        String[] resultTypes = result.getTypes();
        if ((resultTypes != null) && (resultTypes.length > 0)) {
          placeBuilder.types(new ArrayList<String>(Arrays.asList(resultTypes)));
        }
        placeList.add(placeBuilder.build());
      }
      resultBuilder.places(placeList);
    }
    return resultBuilder.build();

  }

  @Override
  public String getVersion() {
    return MAGNET_VERSION_1_0_0;
  }
	  
}
