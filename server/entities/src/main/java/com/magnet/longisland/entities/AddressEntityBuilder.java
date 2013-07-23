package com.magnet.longisland.entities;

import com.magnet.model.api.ResourceBuilder;

public interface AddressEntityBuilder <U extends AddressEntity, T extends AddressEntityBuilder<U, T>> extends ResourceBuilder<U,T> {

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

//  T    maxAddressLineIndex(int maxAddressLineIndex);

  T    locality(String locality);

  T    longitude(double longitude);

  T    phone(String phone);

  T    postalCode(String postalCode);

  T    premises(String premises);

  T    subAdminArea(String subAdminArea);

  T    subLocality(String sublocality);

  T    subThoroughfare(String subthoroughfare);

  T    thoroughfare(String thoroughfare);

  T    url(String Url);

}
