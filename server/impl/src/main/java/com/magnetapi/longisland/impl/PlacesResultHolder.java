package com.magnetapi.longisland.impl;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

public class PlacesResultHolder {
  private String[] debug_info;
  private String[] html_attributions;
  private String next_page_token;
  private Result[] results;
  private String status;
  @JsonIgnoreProperties(ignoreUnknown = true)
  public static class Result {
    private Geometry geometry;
    private String icon;
    private String id;
    private String name;
    private Opening_hours opening_hours;
//	    private Photo[] photos;
    private Integer price_level;
    private Float rating;
    private String reference;
    private String[] types;
    private String vicinity;
    private String formatted_address;
    public static class Geometry {
      private Location location;
      public static class Location {
        private float lat;
        private float lng;
        public float getLat() {
          return lat;
        }
        public void setLat(float lat) {
          this.lat = lat;
        }
        public float getLng() {
          return lng;
        }
        public void setLng(float lng) {
          this.lng = lng;
        }
      }
      public Location getLocation() {
        return location;
      }
      public void setLocation(Location location) {
        this.location = location;
      }
    }
    public static class Opening_hours {
      private boolean open_now;
      public boolean getOpen_now() {
        return open_now;
      }
      public void setOpen_now(boolean open_now) {
        this.open_now = open_now;
      }
    }
/*    public static class Photo {
      private int height;
      private String[] html_attributions;
      private String photo_reference;
      private int width;
      public int getHeight() {
        return height;
      }
      public void setHeight(int height) {
        this.height = height;
      }
      public String[] getHtml_attributions() {
        return html_attributions;
      }
      public void setHtml_attributions(String[] html_attributions) {
        this.html_attributions = html_attributions;
      }
      public String getPhoto_reference() {
        return photo_reference;
      }
      public void setPhoto_reference(String photo_reference) {
        this.photo_reference = photo_reference;
      }
      public int getWidth() {
        return width;
      }
      public void setWidth(int width) {
        this.width = width;
      }
    }*/
    public Geometry getGeometry() {
      return geometry;
    }
    public void setGeometry(Geometry geometry) {
      this.geometry = geometry;
    }
    public String getIcon() {
      return icon;
    }
    public void setIcon(String icon) {
      this.icon = icon;
    }
    public String getId() {
      return id;
    }
    public void setId(String id) {
      this.id = id;
    }
    public String getName() {
      return name;
    }
    public void setName(String name) {
      this.name = name;
    }
    public Opening_hours getOpening_hours() {
      return opening_hours;
    }
    public void setOpening_hours(Opening_hours opening_hours) {
      this.opening_hours = opening_hours;
    }
/*    public Photo[] getPhotos() {
      return photos;
    }
    public void setPhotos(Photo[] photos) {
      this.photos = photos;
    }*/
    public Integer getPrice_level() {
      return price_level;
    }
    public void setPrice_level(Integer price_level) {
      this.price_level = price_level;
    }
    public Float getRating() {
      return rating;
    }
    public void setRating(Float rating) {
      this.rating = rating;
    }
    public String getReference() {
      return reference;
    }
    public void setReference(String reference) {
      this.reference = reference;
    }
    public String[] getTypes() {
      return types;
    }
    public void setTypes(String[] types) {
      this.types = types;
    }
    public String getVicinity() {
      return vicinity;
    }
    public void setVicinity(String vicinity) {
      this.vicinity = vicinity;
    }
    public String getFormatted_address() {
      return formatted_address;
    }
    public void setFormatted_address(String formatted_address) {
      this.formatted_address = formatted_address;
    }
  }
  public String[] getDebug_info() {
    return debug_info;
  }
  public void setDebug_info(String[] debug_info) {
    this.debug_info = debug_info;
  }
  public String[] getHtml_attributions() {
    return html_attributions;
  }
  public void setHtml_attributions(String[] html_attributions) {
    this.html_attributions = html_attributions;
  }
  public String getNext_page_token() {
    return next_page_token;
  }
  public void setNext_page_token(String next_page_token) {
    this.next_page_token = next_page_token;
  }
  public Result[] getResults() {
    return results;
  }
  public void setResults(Result[] results) {
    this.results = results;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
}
