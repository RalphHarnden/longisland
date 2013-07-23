package com.magnetapi.longisland.impl;

//@JsonIgnoreProperties(ignoreUnknown = true)
@SuppressWarnings("unused")
public class PlaceDetailsHolder {
  public static class Result {
	    public static class AddressComponent {
	      private String long_name;
	      private String short_name;
	      private String[] types;
	      public String getLong_name() {
	        return long_name;
	      }
	      public void setLong_name(String long_name) {
	        this.long_name = long_name;
	      }
	    }
/*    public static class Event {
      private String event_id;
      private long start_time;
      private String summary;
      private String url;
      public String getEvent_id() {
        return event_id;
      }
      public void setEvent_id(String event_id) {
        this.event_id = event_id;
      }
      public long getStart_time() {
        return start_time;
      }
      public void setStart_time(long start_time) {
        this.start_time = start_time;
      }
      public String getSummary() {
        return summary;
      }
      public void setSummary(String summary) {
        this.summary = summary;
      }
      public String getUrl() {
        return url;
      }
      public void setUrl(String url) {
        this.url = url;
      }
    }*/
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
/*    public static class Review {
      private Aspect[] aspects;
      private String author_name;
      private String author_url;
      private String text;
      private long time;
      public static class Aspect {
        private int rating;
        private String type;
        public int getRating() {
          return rating;
        }
        public void setRating(int rating) {
          this.rating = rating;
        }
        public String getType() {
          return type;
        }
        public void setType(String type) {
          this.type = type;
        }
      }
      public Aspect[] getAspects() {
        return aspects;
      }
      public void setAspects(Aspect[] aspects) {
        this.aspects = aspects;
      }
      public String getAuthor_name() {
        return author_name;
      }
      public void setAuthor_name(String author_name) {
        this.author_name = author_name;
      }
      public String getAuthor_url() {
        return author_url;
      }
      public void setAuthor_url(String author_url) {
        this.author_url = author_url;
      }
      public String getText() {
        return text;
      }
      public void setText(String text) {
        this.text = text;
      }
      public long getTime() {
        return time;
      }
      public void setTime(long time) {
        this.time = time;
      }
    }*/
    public static class Opening_hours {
      private boolean open_now;
      public boolean getOpen_now() {
        return open_now;
      }
      public void setOpen_now(boolean open_now) {
        this.open_now = open_now;
      }
    }
    public static class Photo {
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
    }
    private AddressComponent[] address_components;
//		    private Event[] events;
    private String formatted_address;
    private String formatted_phone_number;
    private Geometry geometry;
    private String icon;
    private String id;
    private String international_phone_number;
    private String name;
    private Opening_hours opening_hours;
    private Photo[] photos;
    private Integer price_level;
    private Float rating;
    private String reference;
//		    private Review[] reviews;
    private String[] types;
    private String url;
    private String vicinity;
    private Integer utc_offset;
    private String website;
    public AddressComponent[] getAddress_components() {
      return address_components;
    }
    public void setAddress_components(AddressComponent[] address_components) {
      this.address_components = address_components;
    }
/*    public Event[] getEvents() {
      return events;
    }
    public void setEvents(Event[] events) {
      this.events = events;
    }*/
    public String getFormatted_address() {
      return formatted_address;
    }
    public void setFormatted_address(String formatted_address) {
      this.formatted_address = formatted_address;
    }
    public String getFormatted_phone_number() {
      return formatted_phone_number;
    }
    public void setFormatted_phone_number(String formatted_phone_number) {
      this.formatted_phone_number = formatted_phone_number;
    }
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
    public String getInternational_phone_number() {
      return international_phone_number;
    }
    public void setInternational_phone_number(String international_phone_number) {
      this.international_phone_number = international_phone_number;
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
    public Photo[] getPhotos() {
      return photos;
    }
    public void setPhotos(Photo[] photos) {
      this.photos = photos;
    }
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
/*    public Review[] getReviews() {
      return reviews;
    }
    public void setReviews(Review[] reviews) {
      this.reviews = reviews;
    }*/
    public String[] getTypes() {
      return types;
    }
    public void setTypes(String[] types) {
      this.types = types;
    }
    public String getUrl() {
      return url;
    }
    public void setUrl(String url) {
      this.url = url;
    }
    public Integer getUtc_offset() {
      return utc_offset;
    }
    public void setUtc_offset(Integer utc_offset) {
      this.utc_offset = utc_offset;
    }
    public String getVicinity() {
      return vicinity;
    }
    public void setVicinity(String vicinity) {
      this.vicinity = vicinity;
    }
    public String getWebsite() {
      return website;
    }
    public void setWebsite(String website) {
      this.website = website;
    }
  }
  private String[] html_attributions;
  private Result[] results;
  private String status;
  public String[] getHtml_attributions() {
    return this.html_attributions;
  }
  public void setHtml_attributions(String[] html_attributions) {
    this.html_attributions = html_attributions;
  }
  public Result[] getResults() {
    return this.results;
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
