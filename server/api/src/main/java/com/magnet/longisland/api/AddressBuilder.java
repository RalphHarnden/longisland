package com.magnet.longisland.api;

import com.magnet.model.api.ResourceNodeBuilder;

public interface AddressBuilder<U extends Address, T extends AddressBuilder<U, T>> extends ResourceNodeBuilder<U,T> {

  //  T    addressLine(int index, String line);

  T    addressLine1(String line);

  T    addressLine2(String line);

  T    addressLine3(String line);

  T    adminArea(String adminArea);

  T    countryCode(String countryCode);

  T    countryName(String countryName);

//  T    extras(Bundle extras);

  T    featureName(String featureName);

  T    latitude(double latitude);

  T    locality(String locality);

  T    longitude(double longitude);

//  T    maxAddressLineIndex(int maxAddressLineIndex);

  T    phone(String phone);

  T    postalCode(String postalCode);

  T    premises(String premises);

  T    subAdminArea(String subAdminArea);

  T    subLocality(String sublocality);

  T    subThoroughfare(String subthoroughfare);

  T    thoroughfare(String thoroughfare);

  T    url(String Url);

}
