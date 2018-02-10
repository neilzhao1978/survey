package com.neil.survey.module;

public class Products {

   private String id;
   private String thumb_url;
   private Style_location style_location;
   public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

   public void setThumb_url(String thumb_url) {
        this.thumb_url = thumb_url;
    }
    public String getThumb_url() {
        return thumb_url;
    }

   public void setStyle_location(Style_location style_location) {
        this.style_location = style_location;
    }
    public Style_location getStyle_location() {
        return style_location;
    }

}