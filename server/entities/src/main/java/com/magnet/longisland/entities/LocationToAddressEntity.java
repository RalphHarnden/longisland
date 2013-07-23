package com.magnet.longisland.entities;

import javax.inject.Named;

import com.magnet.common.api.Description;
import com.magnet.model.annotations.AttributeDefinition;
import com.magnet.model.annotations.EntityDefinition;
import com.magnet.model.annotations.RelationshipDefinition;
import com.magnet.model.api.ResourceWriteable;

@EntityDefinition(collection= LocationToAddressEntity.ENTITY_COLLECTION)
@Description("A location-to-address entity")
@Named(LocationToAddressEntity.ENTITY_TYPE)
public interface LocationToAddressEntity extends ResourceWriteable {

  public static final String ENTITY_COLLECTION = "location-to-address-entities";
  public static final String ENTITY_TYPE = "location-to-address-entity";

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns the location")
  String getLocation();

  //TODO: Should we do a many-to-many relationship here?
  @RelationshipDefinition(ownership=true)
  @Description("Returns the address.")
  AddressEntity getAddress();

  void setAddress(AddressEntity address);
	  
}
