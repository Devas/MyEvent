package com.myevent.services.books;

import com.myevent.services.AuthorizationService;
import com.myevent.services.Credentials;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class BooksService extends AuthorizationService {

    private final NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();

    public List<String> queryByIsbn(String isbn) {
        Volumes volumes = getVolumes(isbn);
        return getHtml(volumes);
    }

    private Volumes getVolumes(String isbn) {
        isbn = "isbn:" + isbn;

        // Set up Books client
        final Books books = new Books.Builder(httpTransport, jsonFactory, null)
                .setApplicationName(Credentials.APP_NAME)
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(Credentials.API_KEY))
                .build();

        // Set query string and execute the query
        Volumes volumes = null;
        try {
            volumes = books.volumes().list(isbn).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return volumes;
    }

    private List<String> getHtml(Volumes volumes) {
        List<String> html = new ArrayList<>();

        if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
            System.out.println("Books not found.");
            html.add("Books not found.");
        }

        for (Volume volume : volumes.getItems()) {
            Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
            System.out.println("==========");
            html.add("==========");

            // Title
            System.out.println("Title: " + volumeInfo.getTitle());
            html.add("Title: " + volumeInfo.getTitle());

            // Author(s)
            List<String> authors = volumeInfo.getAuthors();
            if (authors != null && !authors.isEmpty()) {
                System.out.print("Author(s): ");
                html.add("Author(s): ");
                for (int i = 0; i < authors.size(); ++i) {
                    System.out.print(authors.get(i));
                    html.add(authors.get(i));
                    if (i < authors.size() - 1) {
                        System.out.print(", ");
                        html.add(", ");
                    }
                }
                System.out.println();
                html.add("\n");
            }

            // Description
            if (volumeInfo.getDescription() != null && volumeInfo.getDescription().length() > 0) {
                System.out.println("Description: " + volumeInfo.getDescription());
                html.add("Description: " + volumeInfo.getDescription());
            }

            // Ratings
            if (volumeInfo.getRatingsCount() != null && volumeInfo.getRatingsCount() > 0) {
                int fullRating = (int) Math.round(volumeInfo.getAverageRating());
                System.out.print("User Rating: ");
                html.add("User Rating: ");
                for (int i = 0; i < fullRating; ++i) {
                    System.out.print("*");
                    html.add("*");
                }
                System.out.println(" (" + volumeInfo.getRatingsCount() + " rating(s))");
                html.add(" (" + volumeInfo.getRatingsCount() + " rating(s))");
            }

            // Price
            Volume.SaleInfo saleInfo = volume.getSaleInfo();
            if (saleInfo != null && saleInfo.getSaleability().equals("FOR_SALE")) {
                double bestPrice = Math.min(saleInfo.getListPrice().getAmount(), saleInfo.getRetailPrice().getAmount());
                System.out.println("The minimum price of the book is: " + currencyFormatter.format(bestPrice));
                html.add("The minimum price of the book is: " + currencyFormatter.format(bestPrice));
            }

            // Access status
            String accessViewStatus = volume.getAccessInfo().getAccessViewStatus();
            String info = "Additional information about the book:";
            if (accessViewStatus.equals("FULL_PUBLIC_DOMAIN")) {
                info = "Read the book for free:";
            } else if (accessViewStatus.equals("SAMPLE")) {
                info = "Read a preview of the book:";
            }
            System.out.println(info + " " + volumeInfo.getInfoLink());
            html.add(info + " " + volumeInfo.getInfoLink());
        }

        return html;
    }
}
