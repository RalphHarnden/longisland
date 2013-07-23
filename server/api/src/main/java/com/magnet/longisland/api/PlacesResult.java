package com.magnet.longisland.api;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Named;

import com.magnet.common.api.Description;
import com.magnet.model.annotations.AttributeDefinition;
import com.magnet.model.annotations.ComponentDefinition;
import com.magnet.model.annotations.EntityDefinition;
import com.magnet.model.api.ResourceNode;

@EntityDefinition(collection=PlacesResult.ENTITY_COLLECTION)
@Named(PlacesResult.ENTITY_TYPE)
public interface PlacesResult extends ResourceNode {

  public static final String ENTITY_COLLECTION = "place-result-nodes";
  public static final String ENTITY_TYPE = "place-result-node";

  public enum Status {
    OK,
    ZERO_RESULTS,
    OVER_QUERY_LIMIT,
    REQUEST_DENIED,
    INVALID_REQUEST
  }

  @SuppressWarnings("serial")
  public static final Set<Status> VALID_STATUSES =
      new HashSet<Status>() {{
        add(Status.OK);
        add(Status.ZERO_RESULTS);
      }};

  @AttributeDefinition
  @Description("Next page token")
  public String getNextPageToken();

  @AttributeDefinition
  @ComponentDefinition(clazz = Place.class, name = "place")
  @Description("Places")
  public List<Place> getPlaces();

  @AttributeDefinition
  @Description("Status")
  public Status getStatus();
	  
}
