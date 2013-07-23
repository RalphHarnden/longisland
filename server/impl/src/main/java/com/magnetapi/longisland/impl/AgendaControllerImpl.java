package com.magnetapi.longisland.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.magnet.attributes.api.ComparisonOperator;
import com.magnet.common.api.IterableResource;
import com.magnet.longisland.api.Address;
import com.magnet.longisland.api.AddressBuilder;
import com.magnet.longisland.api.Agenda;
import com.magnet.longisland.api.AgendaBuilder;
import com.magnet.longisland.api.AgendaController;
import com.magnet.longisland.api.Category;
import com.magnet.longisland.api.CategoryBuilder;
import com.magnet.longisland.api.LocationToAddress;
import com.magnet.longisland.entities.AddressEntity;
import com.magnet.longisland.entities.AddressEntityBuilder;
import com.magnet.longisland.entities.AgendaEntity;
import com.magnet.longisland.entities.AgendaEntityBuilder;
import com.magnet.longisland.entities.CategoryEntity;
import com.magnet.longisland.entities.CategoryEntityBuilder;
import com.magnet.longisland.entities.LocationToAddressEntity;
import com.magnet.longisland.entities.LocationToAddressEntityBuilder;
import com.magnet.model.api.ResourceAlreadyExistsException;
import com.magnet.model.api.ResourceDoesNotExistException;
import com.magnet.model.api.ResourceManager;
import com.magnet.model.api.ResourceQuery;
import com.magnet.model.api.common.EntityFactory;

@Singleton
public class AgendaControllerImpl implements AgendaController {

  @Inject
  ResourceManager entityManager;

  @Override
  public void setAgenda(@Named("agendaList") List<Agenda> list) {
    // Delete all AgendaEntities
    IterableResource<AgendaEntity> entities = entityManager.query(AgendaEntity.class, null);
    for (AgendaEntity entity: entities) {
      try {
        entityManager.delete(entity);
      } catch (ResourceDoesNotExistException e) {
        LogMessagesSingleton.get().swallowing(e);
      }
    }
    // Persist the new AgendaEntities
    for (Agenda agenda: list) {
      try {
        entityManager.persist(agendaToAgendaEntity(agenda));
      } catch (ResourceAlreadyExistsException e) {
        LogMessagesSingleton.get().warning(e);  //TODO: What to do here?
      }
    }
  }

  @Override
  public List<Agenda> getAgenda() {
    IterableResource<AgendaEntity> entities = entityManager.query(AgendaEntity.class, null);
    List<Agenda> retval = new ArrayList<Agenda>(entities.getSize());
    for (AgendaEntity entity: entities) {
      retval.add(agendaEntityToAgenda(entity));
    }
    return retval;
  }

  @Override
  public void setLocationAddress(@Named("locationToAddress") LocationToAddress loc2Addr) {
    if (loc2Addr.getLocation() == null) {
      throw new IllegalArgumentException(
          LogMessagesSingleton.getResources().locationNotSpecified());
    }
    if (loc2Addr.getAddress() == null) {
      throw new IllegalArgumentException(
          LogMessagesSingleton.getResources().addressNotSpecified());
    }
    // Find the entity with the same location, if any
    ResourceQuery query =
        entityManager.getQueryBuilder(LocationToAddressEntity.class).
            addExpression("location", ComparisonOperator.EQUAL, loc2Addr.getLocation()).build();
    LocationToAddressEntity entity =
        LocationToAddressEntity.class.cast(
            entityManager.query(LocationToAddressEntity.class, query).firstResult(false));
    // If the entity was found, delete it
    if (entity != null) {
      AddressEntity addressEntity = entity.getAddress();
      try {
        entityManager.delete(entity);
      } catch (ResourceDoesNotExistException e) {
        LogMessagesSingleton.get().swallowing(e);
      }
      //TODO is this necessary?
      if (addressEntity != null) {
        try {
          entityManager.delete(addressEntity);
        } catch (ResourceDoesNotExistException e) {
          LogMessagesSingleton.get().swallowing(e);
        }
      }
    }
    // Persist the new entity
    try {
      //TODO is this correct?
      AddressEntity addressEntity = addressToAddressEntity(loc2Addr.getAddress());
      entityManager.persist(addressEntity);
      entity = locationToAddressEntity(loc2Addr);
      entity.setAddress(addressEntity);
      entityManager.persist(entity);
    } catch (ResourceAlreadyExistsException e) {
      LogMessagesSingleton.get().warning(e);    //TODO what to do here?
    }
  }

  @Override
  public void setCategory(@Named("index") int index, @Named("category") Category category) {
    if ((index < 0) || (index >= CATEGORY_COUNT)) {
      throw new IllegalArgumentException(
          LogMessagesSingleton.getResources().invalidCategoryIndex(index));
    }
    // Find the entity with the same index, if any
    ResourceQuery query =
        entityManager.getQueryBuilder(CategoryEntity.class).
            addExpression("index", ComparisonOperator.EQUAL, index).build();
    CategoryEntity entity =
        CategoryEntity.class.cast(
            entityManager.query(CategoryEntity.class, query).firstResult(false));
    // If the entity was found, delete it
    if (entity != null) {
      try {
        entityManager.delete(entity);
      } catch (ResourceDoesNotExistException e) {
        LogMessagesSingleton.get().swallowing(e);
      }
    }
    // If the category was specified, persist the new CategoryEntity
    if (category != null) {
      try {
        entity = categoryToCategoryEntity(index, category);
        entityManager.persist(entity);
      } catch (ResourceAlreadyExistsException e) {
        LogMessagesSingleton.get().warning(e);    //TODO what to do here?
      }
    }
  }

  @Override
  public List<Category> getCategories() {
    IterableResource<CategoryEntity> entities = entityManager.query(CategoryEntity.class, null);
    Category[] retval = new Category[CATEGORY_COUNT];
    for (CategoryEntity entity: entities) {
      int index = entity.getIndex();
      if ((index < 0) || (index >= CATEGORY_COUNT)) {
        throw new IllegalStateException(
            LogMessagesSingleton.getResources().invalidCategoryIndex(index));
      }
      retval[index] = categoryEntityToCategory(entity);
    }
    for (int i = 0; i < CATEGORY_COUNT; i++) {
      if (retval[i] == null) {
        retval[i] = categoryEntityToCategory(null);
      }
    }
    return Arrays.asList(retval);
  }

  @Override
  public String getVersion() {
    return MAGNET_VERSION_1_0_0;
  }


  private AgendaEntity agendaToAgendaEntity(Agenda agenda) {
    AgendaEntity retval = null;
    if (agenda != null) {
      @SuppressWarnings({ "rawtypes", "unchecked" })
      AgendaEntityBuilder builder =
          EntityFactory.createEntityBuilder(AgendaEntity.class);
      builder.uri(agenda.getUri())
          .title(agenda.getTitle())
          .location(agenda.getLocation())
          .begin(agenda.getBegin())
          .end(agenda.getEnd());
      retval = AgendaEntity.class.cast(builder.build());
    }
    return retval;
  }

  private Agenda agendaEntityToAgenda(AgendaEntity entity) {
    Agenda retval = null;
    if (entity != null) {
      AgendaBuilder<Agenda, ?> builder =
          (AgendaBuilder<Agenda, ?>)EntityFactory.createNodeBuilder(Agenda.class);
      builder.uri(entity.getUri())
          .title(entity.getTitle())
          .begin(entity.getBegin())
          .end(entity.getEnd());
      if (entity.getLocation() != null) {
        builder.location(entity.getLocation());
        builder.address(findAddress(entity.getLocation()));
      }
      retval = builder.build();
    }
    return retval;
  }

  private Address findAddress(String location) {
    return addressEntityToAddress(findAddressEntity(location));
  }

  private AddressEntity findAddressEntity(String location) {
    ResourceQuery query =
        entityManager.getQueryBuilder(LocationToAddressEntity.class).
            addExpression("location", ComparisonOperator.EQUAL, location).build();
    LocationToAddressEntity entity =
        LocationToAddressEntity.class.cast(
            entityManager.query(LocationToAddressEntity.class, query).firstResult(false));
    return (entity == null) ? null : entity.getAddress();
  }

  private Address addressEntityToAddress(AddressEntity entity) {
    Address retval = null;
    if (entity != null) {
      AddressBuilder<Address, ?> builder =
          (AddressBuilder<Address, ?>)EntityFactory.createNodeBuilder(Address.class);
      builder.addressLine1(entity.getAddressLine1())
          .addressLine2(entity.getAddressLine2())
          .addressLine3(entity.getAddressLine3())
          .adminArea(entity.getAdminArea())
          .countryCode(entity.getCountryCode())
          .countryName(entity.getCountryName())
          .featureName(entity.getFeatureName())
          .latitude(entity.getLatitude())
          .locality(entity.getLocality())
          .longitude(entity.getLongitude())
          .phone(entity.getPhone())
          .postalCode(entity.getPostalCode())
          .premises(entity.getPremises())
          .subAdminArea(entity.getSubAdminArea())
          .subLocality(entity.getSubLocality())
          .subThoroughfare(entity.getSubThoroughfare())
          .thoroughfare(entity.getThoroughfare())
          .url(entity.getUrl());
      retval = builder.build();
    }
    return retval;
  }

  private AddressEntity addressToAddressEntity(Address address) {
    AddressEntity retval = null;
    if (address != null) {
      @SuppressWarnings({ "unchecked", "rawtypes" })
      AddressEntityBuilder builder =
          EntityFactory.createEntityBuilder(AddressEntity.class);
      builder.addressLine1(address.getAddressLine1())
          .addressLine2(address.getAddressLine2())
          .addressLine3(address.getAddressLine3())
          .adminArea(address.getAdminArea())
          .countryCode(address.getCountryCode())
          .countryName(address.getCountryName())
          .featureName(address.getFeatureName())
          .latitude(address.getLatitude())
          .locality(address.getLocality())
          .longitude(address.getLongitude())
          .phone(address.getPhone())
          .postalCode(address.getPostalCode())
          .premises(address.getPremises())
          .subAdminArea(address.getSubAdminArea())
          .subLocality(address.getSubLocality())
          .subThoroughfare(address.getSubThoroughfare())
          .thoroughfare(address.getThoroughfare());
      builder.url(address.getUrl());
      retval = AddressEntity.class.cast(builder.build());
    }
    return retval;
  }

  private LocationToAddressEntity locationToAddressEntity(LocationToAddress loc2Addr) {
    LocationToAddressEntity retval = null;
    if (loc2Addr != null) {
      @SuppressWarnings({ "rawtypes", "unchecked" })
      LocationToAddressEntityBuilder builder =
          EntityFactory.createEntityBuilder(LocationToAddressEntity.class);
      builder.location(loc2Addr.getLocation());
      retval = LocationToAddressEntity.class.cast(builder.build());
    }
    return retval;
  }

  private CategoryEntity categoryToCategoryEntity(int index, Category category) {
    CategoryEntity retval = null;
    if (category != null) {
      @SuppressWarnings({ "rawtypes", "unchecked" })
      CategoryEntityBuilder builder =
          EntityFactory.createEntityBuilder(CategoryEntity.class);
      builder.index(index).name(category.getName()).type(category.getType());
      retval = CategoryEntity.class.cast(builder.build());
    }
    return retval;
  }

  private Category categoryEntityToCategory(CategoryEntity entity) {
    CategoryBuilder<Category, ?> builder =
        (CategoryBuilder<Category, ?>)EntityFactory.createNodeBuilder(Category.class);
    if (entity != null) {
      builder.type(entity.getType()).name(entity.getName());
    }
    return builder.build();
  }
	  
}
