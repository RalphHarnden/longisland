package com.magnet.longisland.api;

import java.util.List;

import com.magnet.model.api.ResourceNodeBuilder;

public interface PlaceBuilder <U extends Place, T extends PlaceBuilder<U, T>> extends ResourceNodeBuilder<U, T> {

  T latitude(Float latitude);

  T longitude(Float longitude);

  T icon(String icon);

  T name(String name);

  T open(Boolean open);

  T priceLevel(Place.PriceLevel priceLevel);

  T rating(Float rating);

  T types(List<String> types);

  T vicinity(String vicinity);

  T formattedAddress(String formattedAddress);

  T reference(String reference);
	  
}
