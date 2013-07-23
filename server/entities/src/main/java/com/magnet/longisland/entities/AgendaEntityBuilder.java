package com.magnet.longisland.entities;

import com.magnet.model.api.ResourceBuilder;

public interface AgendaEntityBuilder <U extends AgendaEntity, T extends AgendaEntityBuilder<U, T>> extends ResourceBuilder<U,T> {

  T uri(String uri);

  T title(String title);

  T location(String location);

  T begin(long begin);

  T end(long end);
	  
}
