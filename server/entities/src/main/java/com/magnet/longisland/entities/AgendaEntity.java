package com.magnet.longisland.entities;

import javax.inject.Named;

import com.magnet.common.api.Description;
import com.magnet.model.annotations.AttributeDefinition;
import com.magnet.model.annotations.EntityDefinition;
import com.magnet.model.api.ResourceWriteable;

@EntityDefinition(collection= AgendaEntity.ENTITY_COLLECTION)
@Description("An agenda entity")
@Named(AgendaEntity.ENTITY_TYPE)
public interface AgendaEntity extends ResourceWriteable {

  public static final String ENTITY_COLLECTION = "agenda-entities";
  public static final String ENTITY_TYPE = "agenda-entity";

  @AttributeDefinition(required=true, searchable=true, ranking=9)
  @Description("Returns agenda unique id")
  String  getUri();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns agenda title")
  String  getTitle();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns agenda event location")
  String  getLocation();

  @AttributeDefinition(required=true, searchable=true, ranking=9)
  @Description("Returns agenda begin")
  long  getBegin();

  @AttributeDefinition(required=true, searchable=true, ranking=9)
  @Description("Returns agenda end")
  long  getEnd();

  void setTitle(String title);

  void setLocation(String location);

  void setBegin(long begin);

  void setEnd(long end);
	  
}
