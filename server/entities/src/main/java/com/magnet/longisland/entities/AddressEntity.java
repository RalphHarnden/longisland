package com.magnet.longisland.entities;

import javax.inject.Named;

import com.magnet.common.api.Description;
import com.magnet.model.annotations.AttributeDefinition;
import com.magnet.model.annotations.EntityDefinition;
import com.magnet.model.api.ResourceWriteable;

@EntityDefinition(collection= AddressEntity.ENTITY_COLLECTION)
@Description("An address entity")
@Named(AddressEntity.ENTITY_TYPE)
public interface AddressEntity extends ResourceWriteable {

  public static final String ENTITY_COLLECTION = "address-entities";
  public static final String ENTITY_TYPE = "address-entity";

//  @AttributeDefinition(required=false, searchable=true, ranking=9)
//  @Description("Returns a line of the address numbered by the given index (starting at 0), or null if no such line is present.")
//  String  getAddressLine(int index);

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns address line 1")
  String  getAddressLine1();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns address line 2")
  String  getAddressLine2();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns address line 3")
  String  getAddressLine3();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns the administrative area name of the address, for example, \"CA\", or null if it is unknown")
  String  getAdminArea();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns the country code of the address, for example \"US\", or null if it is unknown.")
  String  getCountryCode();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns the localized country name of the address, for example \"Iceland\", or null if it is unknown.")
  String  getCountryName();

//  @AttributeDefinition(required=false, searchable=true, ranking=9)
//  @Description("Returns additional provider-specific information about the address as a Bundle.")
//  Bundle  getExtras();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns the feature name of the address, for example, \"Golden Gate Bridge\", or null if it is unknown")
  String  getFeatureName();

  @AttributeDefinition(required=true, searchable=true, ranking=9)
  @Description("Returns the latitude of the address if known.")
  double  getLatitude();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns the locality of the address, for example \"Mountain View\", or null if it is unknown.")
  String  getLocality();

  @AttributeDefinition(required=true, searchable=true, ranking=9)
  @Description("Returns the longitude of the address if known.")
  double  getLongitude();

//  @AttributeDefinition(required=true, searchable=true, ranking=9)
//  @Description("Returns the largest index currently in use to specify an address line.")
//  int     getMaxAddressLineIndex();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns the phone number of the address if known, or null if it is unknown.")
  String  getPhone();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns the postal code of the address, for example \"94110\", or null if it is unknown.")
  String  getPostalCode();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns the premises of the address, or null if it is unknown.")
  String  getPremises();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns the sub-administrative area name of the address, for example, \"Santa Clara County\", or null if it is unknown")
  String  getSubAdminArea();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns the sub-locality of the address, or null if it is unknown.")
  String  getSubLocality();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns the sub-thoroughfare name of the address, which may be null.")
  String  getSubThoroughfare();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns the thoroughfare name of the address, for example, \"1600 Ampitheater Parkway\", which may be null")
  String  getThoroughfare();

  @AttributeDefinition(required=false, searchable=true, ranking=9)
  @Description("Returns the public URL for the address if known, or null if it is unknown.")
  String  getUrl();

//  void    setAddressLine(int index, String line);

  void    setAddressLine1(String line);

  void    setAddressLine2(String line);

  void    setAddressLine3(String line);

  void    setAdminArea(String adminArea);

  void    setCountryCode(String countryCode);

  void    setCountryName(String countryName);

//  void    setExtras(Bundle extras);

  void    setFeatureName(String featureName);

  void    setLatitude(double latitude);

  void    setLocality(String locality);

  void    setLongitude(double longitude);

  void    setPhone(String phone);

  void    setPostalCode(String postalCode);

  void    setPremises(String premises);

  void    setSubAdminArea(String subAdminArea);

  void    setSubLocality(String sublocality);

  void    setSubThoroughfare(String subthoroughfare);

  void    setThoroughfare(String thoroughfare);

  void    setUrl(String Url);
	  
}
