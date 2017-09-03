package com.myevent.repositories;

import com.myevent.models.googleapi.Book;
import com.myevent.models.googleapi.Document;
import com.myevent.models.googleapi.File;
import com.myevent.models.googleapi.Slide;

import java.util.ArrayList;
import java.util.List;

public class MaterialsRepository {

    private List<Book> books;
    private List<File> files;
    private List<Slide> slides;
    private List<Document> documents;

    public MaterialsRepository() {
        books = new ArrayList<>();
        files = new ArrayList<>();
        slides = new ArrayList<>();
        documents = new ArrayList<>();
    }

    public List<Book> getBooks() {
        return books;
    }

    public List<File> getFiles() {
        return files;
    }

    public List<Slide> getSlides() {
        return slides;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void deleteBook(String isbn) {
        books.removeIf(book -> book.getIsbn().equals(isbn));
    }

}
