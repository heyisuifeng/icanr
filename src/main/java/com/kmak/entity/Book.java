package com.kmak.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created by Leaf.Ye on 2017/6/8.
 */
@XmlRootElement
public class Book implements Serializable {
    private Integer id;
    private String name;
    private String author;
    public Book() {
        super();
        // TODO Auto-generated constructor stub
    }
    public Book(Integer id, String name, String author) {
        super();
        this.id = id;
        this.name = name;
        this.author = author;
    }
    public Integer getId() {
        return id;
    }
    // 该属性作为xml的element
    @XmlElement
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }
    @XmlElement
    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Book [id=" + id + ", name=" + name + ", author=" + author + "]";
    }
}
