package com.magnet.longisland.api;

import javax.inject.Named;

import com.magnet.common.api.Description;
import com.magnet.model.annotations.AttributeDefinition;
import com.magnet.model.annotations.EntityDefinition;
import com.magnet.model.api.ResourceNodeWriteable;

@EntityDefinition(collection=Category.ENTITY_COLLECTION)
@Named(Category.ENTITY_TYPE)
public interface Category extends ResourceNodeWriteable {

  public static final String ENTITY_COLLECTION = "category-nodes";
  public static final String ENTITY_TYPE = "category-node";

  @AttributeDefinition
  @Description("Returns the category name")
  String getName();

  @AttributeDefinition
  @Description("Returns the category type")
  String getType();

  void setName(String name);

  void setType(String type);
	  
}
