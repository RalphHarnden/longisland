package com.magnet.longisland.api;

import com.magnet.model.api.ResourceNodeBuilder;

public interface LocationToAddressBuilder<U extends LocationToAddress, T extends LocationToAddressBuilder<U, T>> extends ResourceNodeBuilder<U,T> {

  T location(String location);

  T address(Address address);
	  
}
