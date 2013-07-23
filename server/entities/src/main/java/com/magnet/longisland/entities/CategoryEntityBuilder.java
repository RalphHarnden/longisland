package com.magnet.longisland.entities;

import com.magnet.model.api.ResourceBuilder;

public interface CategoryEntityBuilder <U extends CategoryEntity, T extends CategoryEntityBuilder<U, T>> extends ResourceBuilder<U,T> {

  T index(int index);

  T name(String name);

  T type(String type);
	  
}
