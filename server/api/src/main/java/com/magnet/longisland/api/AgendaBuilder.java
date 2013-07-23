package com.magnet.longisland.api;

import com.magnet.model.api.ResourceNodeBuilder;

public interface AgendaBuilder<U extends Agenda, T extends AgendaBuilder<U, T>> extends ResourceNodeBuilder<U,T> {

  T uri(String uri);

  T title(String title);

  T location(String location);

  T address(Address address);

  T begin(long begin);

  T end(long end);
	  
}
