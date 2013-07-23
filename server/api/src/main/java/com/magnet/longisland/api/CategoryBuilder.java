package com.magnet.longisland.api;

import com.magnet.model.api.ResourceNodeBuilder;

public interface CategoryBuilder<U extends Category, T extends CategoryBuilder<U, T>> extends ResourceNodeBuilder<U,T> {

  T name(String name);

  T type(String type);
	  
}
