package com.magnet.longisland.entities;

import com.magnet.model.api.ResourceBuilder;

public interface LocationToAddressEntityBuilder <U extends LocationToAddressEntity, T extends LocationToAddressEntityBuilder<U, T>> extends ResourceBuilder<U,T> {

  T location(String location);

  T address(AddressEntity address);
}
