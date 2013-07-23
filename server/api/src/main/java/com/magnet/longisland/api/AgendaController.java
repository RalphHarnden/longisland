package com.magnet.longisland.api;

import java.util.List;

import javax.inject.Named;

import com.magnet.common.api.RemoteAccessible;
import com.magnet.common.api.SupportedTargets;
import com.magnet.controller.spi.Controller;
import com.magnet.inject.Contract;
import com.magnet.inject.Optional;

@RemoteAccessible
@Contract
public interface AgendaController extends Controller {

  public static final int CATEGORY_COUNT = 8;

  /**
   * Set the agendas.
   * @param list of agendas
   */
  void setAgenda(@Named("agendaList")List<Agenda> list);

  /**
   * Get the agendas.
   * @return agendas
   */
  List<Agenda> getAgenda();

  /**
   * Set location to address mapping.
   * @param loc2Addr location to address mapping.
   */
  void setLocationAddress(@Named("locationToAddress")LocationToAddress loc2Addr);

  /**
   * Set the category for specified  index.
   * @param index of category; must be between 0 and CATEGORY_COUNT-1, inclusive.
   * @param category to be set; null to delete category at specified index.
   * @see #CATEGORY_COUNT
   */
  void setCategory(@Named("index")int index, @Named("category")@Optional Category category);

  /**
   * Get categories.
   * @return categories.
   * There will be CATEGORY_COUNT+1 instances, in index order.
   * If a category was not set for an index,
   * the Category instance at that index will have a null name and type.
   * @see #CATEGORY_COUNT
   */
  List<Category> getCategories();
	  
}
