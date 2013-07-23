package com.magnet.longisland.api;

import java.util.List;

import com.magnet.model.api.ResourceNodeBuilder;

public interface PlacesResultBuilder<U extends PlacesResult, T extends PlacesResultBuilder<U, T>> extends ResourceNodeBuilder<U, T> {

  T nextPageToken(String nextPageToken);

  T places(List<Place> places);

  T status(PlacesResult.Status status);
}
