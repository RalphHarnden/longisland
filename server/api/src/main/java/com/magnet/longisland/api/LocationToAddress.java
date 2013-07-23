package com.magnet.longisland.api;

import javax.inject.Named;

import com.magnet.common.api.Description;
import com.magnet.model.annotations.AttributeDefinition;
import com.magnet.model.annotations.EntityDefinition;
import com.magnet.model.api.ResourceNodeWriteable;

@EntityDefinition(collection=LocationToAddress.ENTITY_COLLECTION)
@Named(LocationToAddress.ENTITY_TYPE)
public interface LocationToAddress extends ResourceNodeWriteable  {

  public static final String ENTITY_COLLECTION = "location-to-address-nodes";
  public static final String ENTITY_TYPE = "location-to-address-node";

  @AttributeDefinition
  @Description("Returns the location")
  String getLocation();

  @AttributeDefinition
  @Description("Returns the address.")
  Address getAddress();
	  
}
