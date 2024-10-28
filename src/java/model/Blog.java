/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author PC
 */
public class Blog {

    private int id;
    private String title;
    private String content;
    private int staffID;
    private Date datePosted;
    private String authorName;
    private String image;
    private String description;
    private int views;
    private int commentsCount;
    private String category;
    private String authorBio;
    private String authorImage;
    // No-argument constructor

    public Blog() {
    }

    // Constructor with all fields
    public Blog(int id, String title, String content, int staffID, Date datePosted, String authorName, String image,
            String description, int views, int commentsCount, String category, String authorBio, String authorImage) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.staffID = staffID;
        this.datePosted = datePosted;
        this.authorName = authorName;
        this.image = image;
        this.description = description;
        this.views = views;
        this.commentsCount = commentsCount;
        this.category = category;
        this.authorBio = authorBio;
        this.authorImage = authorImage;
    }

    // Constructor without optional fields
    public Blog(int id, String title, String content, int staffID, Date datePosted, String authorName, String image) {
        this(id, title, content, staffID, datePosted, authorName, image, null, 0, 0, null, null, null);
    }

    // Getters and Setters for all fields
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public Date getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(Date datePosted) {
        this.datePosted = datePosted;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthorBio() {
        return authorBio;
    }

    public void setAuthorBio(String authorBio) {
        this.authorBio = authorBio;
    }

    public String getAuthorImage() {
        return authorImage;
    }

    public void setAuthorImage(String authorImage) {
        this.authorImage = authorImage;
    }

    // toString method for debugging purposes
    @Override
    public String toString() {
        return "Blog{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", content='" + content + '\''
                + ", staffID=" + staffID
                + ", datePosted=" + datePosted
                + ", authorName='" + authorName + '\''
                + ", image='" + image + '\''
                + ", description='" + description + '\''
                + ", views=" + views
                + ", commentsCount=" + commentsCount
                + ", category='" + category + '\''
                + ", authorBio='" + authorBio + '\''
                + ", authorImage='" + authorImage + '\''
                + '}';
    }
}
