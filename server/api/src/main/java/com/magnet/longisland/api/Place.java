package com.magnet.longisland.api;

import java.util.List;

import javax.inject.Named;

import com.magnet.common.api.Description;
import com.magnet.model.annotations.AttributeDefinition;
import com.magnet.model.annotations.ComponentDefinition;
import com.magnet.model.annotations.EntityDefinition;
import com.magnet.model.api.ResourceNode;

@EntityDefinition(collection=Place.ENTITY_COLLECTION)
@Named(Place.ENTITY_TYPE)
public interface Place extends ResourceNode {

  public static final String ENTITY_COLLECTION = "place-nodes";
  public static final String ENTITY_TYPE = "place-node";

  public enum PriceLevel {
    Free,
    Inexpensive,
    Moderate,
    Expensive,
    VeryExpensive;
    public static PriceLevel fromValue(int value) {
      PriceLevel[] priceLevels = PriceLevel.values();
      if ((value < 0) || (value > priceLevels.length)) {
        throw new IllegalArgumentException("Invalid PriceRange value: " + value);
      }
      return priceLevels[value];
    }
    public static int toValue(PriceLevel value) {
      PriceLevel[] priceLevels = PriceLevel.values();
      for (int i = 0; i < priceLevels.length; i++) {
        if (priceLevels[i] == value) {
          return i;
        }
      }
      throw new IllegalArgumentException("Unable to find value of: " + value);
    }
  }

  @AttributeDefinition
  @Description("Latitude")
  public Float getLatitude();

  @AttributeDefinition
  @Description("Longitude")
  public Float getLongitude();

  @AttributeDefinition
  @Description("Icon")
  public String getIcon();

  @AttributeDefinition
  @Description("Name")
  public String getName();

  @AttributeDefinition
  @Description("Open")
  public Boolean isOpen();

  @AttributeDefinition
  @Description("Price level")
  public PriceLevel getPriceLevel();

  @AttributeDefinition
  @Description("Rating")
  public Float getRating();

  @AttributeDefinition
  @ComponentDefinition(clazz = String.class, name = "type")
  @Description("Types")
  public List<String> getTypes();

  @AttributeDefinition
  @Description("Vicinity")
  public String getVicinity();

  @AttributeDefinition
  @Description("Formatted address")
  public String getFormattedAddress();

  @AttributeDefinition
  @Description("Detail reference")
  public String getReference();

}
