package com.linktreeclone.api.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties({"hibernateLazyInitializer"})
@Entity
@Table(name = "links")
public class Link {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "link_generator")
    private Long id;

    @Column(name = "title")
    @NotBlank(message = "Title is mandatory!")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "url")
    @NotBlank(message = "Url is mandatory!")
    private String url;

    @CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updatedAt")
    private Date updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User creator;

    public Link() {}

    public Link(
        @JsonProperty("title") String title, 
        @JsonProperty("url") String url, 
        @JsonProperty("description") String description) {
        this.title = title;
        this.url = url;
        this.description = description;
    }

    public Long getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl() {
        return this.url;
    }

    public String getDescription() {
        return this.description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }
}
