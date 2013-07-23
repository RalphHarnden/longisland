package com.magnet.longisland.entities;

import javax.inject.Named;

import com.magnet.common.api.Description;
import com.magnet.model.annotations.AttributeDefinition;
import com.magnet.model.annotations.EntityDefinition;
import com.magnet.model.api.ResourceWriteable;

@EntityDefinition(collection= CategoryEntity.ENTITY_COLLECTION)
@Description("An category entity")
@Named(CategoryEntity.ENTITY_TYPE)
public interface CategoryEntity extends ResourceWriteable {

  public static final String ENTITY_COLLECTION = "category-entities";
  public static final String ENTITY_TYPE = "category-entity";

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns the index name")
  int getIndex();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns the category name")
  String getName();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns the category type")
  String getType();
	  
}
