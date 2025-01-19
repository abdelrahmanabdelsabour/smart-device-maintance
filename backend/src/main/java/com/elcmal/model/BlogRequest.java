package com.elcmal.model;


import lombok.Data;

@Data
public class BlogRequest {

    private String title;
    private String content;
    private String author;
    private String imageUrl;  // Add this if you're passing the image URL directly when creating
}
