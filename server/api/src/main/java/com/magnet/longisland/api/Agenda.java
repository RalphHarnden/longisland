package com.magnet.longisland.api;

import javax.inject.Named;

import com.magnet.common.api.Description;
import com.magnet.model.annotations.AttributeDefinition;
import com.magnet.model.annotations.EntityDefinition;
import com.magnet.model.api.ResourceNodeWriteable;

@EntityDefinition(collection=Agenda.ENTITY_COLLECTION)
@Named(Agenda.ENTITY_TYPE)
public interface Agenda extends ResourceNodeWriteable {

  public static final String ENTITY_COLLECTION = "agenda-nodes";
  public static final String ENTITY_TYPE = "agenda-node";

  @AttributeDefinition
  @Description("Returns agenda unique id")
  String  getUri();

  @AttributeDefinition
  @Description("Returns agenda title")
  String  getTitle();

  @AttributeDefinition
  @Description("Returns agenda event location")
  String  getLocation();

  @AttributeDefinition
  @Description("Returns agenda event address")
  Address  getAddress();

  @AttributeDefinition
  @Description("Returns agenda begin")
  long  getBegin();

  @AttributeDefinition
  @Description("Returns agenda end")
  long  getEnd();

  void setTitle(String title);

  void setLocation(String location);

  void setAddress(Address address);

  void setBegin(long begin);

  void setEnd(long end);

}
