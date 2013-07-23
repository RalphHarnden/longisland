package com.magnetapi.longisland.impl.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.magnet.longisland.api.Address;
import com.magnet.longisland.api.AddressBuilder;
import com.magnet.longisland.api.Agenda;
import com.magnet.longisland.api.AgendaBuilder;
import com.magnet.longisland.api.AgendaController;
import com.magnet.longisland.api.Category;
import com.magnet.longisland.api.CategoryBuilder;
import com.magnet.longisland.api.LocationToAddress;
import com.magnet.longisland.api.LocationToAddressBuilder;
import com.magnet.longisland.api.Place;
import com.magnet.longisland.api.PlacesController;
import com.magnet.longisland.api.PlacesResult;
import com.magnet.model.api.common.EntityFactory;
import com.magnet.testutils.PlatformRunner;
import com.magnet.testutils.PlatformRunnerContext;

@RunWith(PlatformRunner.class)
@PlatformRunnerContext
public class AgendaTest {

  private static long ONE_HOUR = 60l*60l*1000l;
  private static long ONE_DAY = ONE_HOUR*24;

  @Inject
  AgendaController agendaController;

  @Inject
  PlacesController placesController;

  private AgendaBuilder<Agenda, ?> agendaBuilder =
      (AgendaBuilder<Agenda, ?>) EntityFactory.createNodeBuilder(Agenda.class);

  private AddressBuilder<Address, ?> addressBuilder =
      (AddressBuilder<Address, ?>) EntityFactory.createNodeBuilder(Address.class);

  private LocationToAddressBuilder<LocationToAddress, ?> locationToddressBuilder =
      (LocationToAddressBuilder<LocationToAddress, ?>) EntityFactory.createNodeBuilder(LocationToAddress.class);

  private CategoryBuilder<Category, ?> categoryBuilder =
      (CategoryBuilder<Category, ?>) EntityFactory.createNodeBuilder(Category.class);

  @Test
  public void simpleTest() {
    assertNotNull(agendaController);
    assertNotNull(placesController);
    List<Agenda> agendas = agendaController.getAgenda();
    assertTrue("initial agendas is empty", agendas.isEmpty());
    long currentTime = System.currentTimeMillis();
    List<Agenda> newAgendas = new ArrayList<Agenda>();
    newAgendas.add(
        agendaBuilder.uri("agenda::1")
            .title("agenda 1")
            .location("Magnet Systems, Inc.")
            .begin(currentTime + ONE_HOUR)
            .end(currentTime + 2 * ONE_HOUR)
            .build());
    newAgendas.add(
        agendaBuilder.uri("agenda::2")
            .title("agenda 2")
            .location("GM Advanced Technology Silicon Valley Office")
            .begin(currentTime + ONE_DAY)
            .end(currentTime + ONE_DAY + ONE_HOUR)
            .build());
    agendaController.setAgenda(newAgendas);
    agendas = agendaController.getAgenda();
    assertEquals("new agendas size", newAgendas.size(), agendas.size());
    for (Agenda agenda: agendas) {
      if ("agenda::1".equals(agenda.getUri())) {
        assertEquals("agenda 1", agenda.getTitle());
        assertEquals("Magnet Systems, Inc.", agenda.getLocation());
        assertNull("address", agenda.getAddress());
        assertEquals("begin", currentTime+ONE_HOUR, agenda.getBegin());
        assertEquals("end", currentTime+2*ONE_HOUR, agenda.getEnd());
        agendaController.setLocationAddress(buildLocationToAddress(agenda));
      } else  if ("agenda::2".equals(agenda.getUri())) {
        assertEquals("agenda 2", agenda.getTitle());
        assertEquals("GM Advanced Technology Silicon Valley Office", agenda.getLocation());
        assertNull("address", agenda.getAddress());
        assertEquals("begin", currentTime+ONE_DAY, agenda.getBegin());
        assertEquals("end", currentTime+ONE_DAY+ONE_HOUR, agenda.getEnd());
        agendaController.setLocationAddress(buildLocationToAddress(agenda));
      } else {
        fail("unknown agenda uri: " + agenda.getUri());
      }
    }
    agendas = agendaController.getAgenda();
    assertEquals("updated agendas size", newAgendas.size(), agendas.size());
    for (Agenda agenda: agendas) {
      if ("agenda::1".equals(agenda.getUri())) {
        assertEquals("agenda 1", agenda.getTitle());
        assertEquals("Magnet Systems, Inc.", agenda.getLocation());
        assertNotNull("address", agenda.getAddress());
        assertEquals("begin", currentTime + ONE_HOUR, agenda.getBegin());
        assertEquals("end", currentTime+2*ONE_HOUR, agenda.getEnd());
        assertNotNull(agenda.getAddress());
      } else  if ("agenda::2".equals(agenda.getUri())) {
        assertEquals("agenda 2", agenda.getTitle());
        assertEquals("GM Advanced Technology Silicon Valley Office", agenda.getLocation());
        assertNotNull("address", agenda.getAddress());
        assertEquals("begin", currentTime+ONE_DAY, agenda.getBegin());
        assertEquals("end", currentTime + ONE_DAY + ONE_HOUR, agenda.getEnd());
        assertNotNull(agenda.getAddress());
      } else {
        fail("unknown agenda uri: " + agenda.getUri());
      }
    }
  }

  @Test
  public void simpleCategoryTest() {
    assertNotNull(agendaController);
    List<Category> categories = agendaController.getCategories();
    assertEquals("categories size", AgendaController.CATEGORY_COUNT, categories.size());
    int i = 0;
    for (Category category: categories) {
      assertNotNull("category " + i + " is not null", category);
      assertNull("category " + i + " name is null", category.getName());
      assertNull("category " + i + " type is null", category.getType());
      ++i;
    }
    Category newCategory =
        categoryBuilder.name("category 4").type("CATEGORY").build();
    agendaController.setCategory(4, newCategory);
    categories = agendaController.getCategories();
    i = 0;
    for (Category category: categories) {
      assertNotNull("category " + i + " is not null", category);
      if (i == 4) {
        assertEquals("category " + i, category.getName());
        assertEquals("CATEGORY", category.getType());
      } else {
        assertNull("category " + i + " name is null", category.getName());
        assertNull("category " + i + " type is null", category.getType());
      }
      ++i;
    }
    newCategory.setName("category x");
    agendaController.setCategory(4, newCategory);
    categories = agendaController.getCategories();
    i = 0;
    for (Category category: categories) {
      assertNotNull("category " + i + " is not null", category);
      if (i == 4) {
        assertEquals("category x", category.getName());
        assertEquals("CATEGORY", category.getType());
      } else {
        assertNull("category " + i + " name is null", category.getName());
        assertNull("category " + i + " type is null", category.getType());
      }
      ++i;
    }
    agendaController.setCategory(4, null);
    categories = agendaController.getCategories();
    i = 0;
    for (Category category: categories) {
      assertNotNull("category " + i + " is not null", category);
      assertNull("category " + i + " name is null", category.getName());
      assertNull("category " + i + " type is null", category.getType());
      ++i;
    }
    for (i = 0; i < AgendaController.CATEGORY_COUNT; i++) {
      newCategory.setName("category " + i);
      agendaController.setCategory(i, newCategory);
    }
    categories = agendaController.getCategories();
    i = 0;
    for (Category category: categories) {
      assertNotNull("category " + i + " is not null", category);
      assertEquals("category " + i, category.getName());
      assertEquals("CATEGORY", category.getType());
      ++i;
    }
    try {
      agendaController.setCategory(-1, null);
      fail("Expected exception not thrown with index -1");
    } catch (IllegalArgumentException ignore) {
    }
    try {
      agendaController.setCategory(AgendaController.CATEGORY_COUNT, null);
      fail("Expected exception not thrown with index " + AgendaController.CATEGORY_COUNT);
    } catch (IllegalArgumentException ignore) {
    }
  }

  private Place getPlace(String location) {
    PlacesResult result =
        placesController.findPlaces(
            location,         // location
            null,             // latitude (optional)
            null,             // longitude (optional)
            null,             // radius (optional)
            null,             // types (optiopnal)
            null,             // openNow (optional)
            null,             // minPriceLevel (optional)
            null);            // maxPriceLevel (optional)
    assertEquals("places result", PlacesResult.Status.OK, result.getStatus());
    assertTrue("places result size", result.getPlaces().size() > 0);
    return result.getPlaces().get(0);
  }

  private LocationToAddress buildLocationToAddress(Agenda agenda) {
    Place place = getPlace(agenda.getLocation());
    Address address =
        addressBuilder.latitude(place.getLatitude()).longitude(place.getLongitude()).build();
    return  locationToddressBuilder.location(agenda.getLocation()).address(address).build();
  }
	  
}
