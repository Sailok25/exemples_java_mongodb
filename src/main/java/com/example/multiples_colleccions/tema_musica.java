package com.example.multiples_colleccions;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Arrays;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.set;

public class tema_musica {
    public static void main(String[] args) {
        insertDocuments();
        findDocuments();
        updateDocuments();
        deleteDocuments();
    }

    public static void insertDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("musicaDB");

            // Insertar artistas
            MongoCollection<Document> artistasCollection = database.getCollection("artistas");
            Document artista1 = new Document("_id", "1")
                    .append("nombre", "Coldplay")
                    .append("genero", "Rock")
                    .append("pais", "Reino Unido");
            artistasCollection.insertOne(artista1);

            // Insertar álbumes
            MongoCollection<Document> albumsCollection = database.getCollection("albumes");
            Document album1 = new Document("_id", "1")
                    .append("titulo", "A Rush of Blood to the Head")
                    .append("artista_id", "1")
                    .append("año", 2002)
                    .append("canciones", Arrays.asList(
                            new Document("titulo", "Politik").append("duracion", 3.18),
                            new Document("titulo", "In My Place").append("duracion", 3.48)
                    ));
            albumsCollection.insertOne(album1);

            System.out.println("Documentos insertados correctamente!");
        }
    }

    public static void findDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("musicaDB");
            MongoCollection<Document> artistasCollection = database.getCollection("artistas");
            MongoCollection<Document> albumsCollection = database.getCollection("albumes");

            // Buscar todos los artistas
            System.out.println("Todos los artistas:");
            for (Document artista : artistasCollection.find()) {
                System.out.println(artista.toJson());
            }

            // Buscar álbumes de un artista específico
            System.out.println("Álbumes de Coldplay:");
            for (Document album : albumsCollection.find(eq("artista_id", "1"))) {
                System.out.println(album.toJson());
            }

            // Buscar álbumes con canciones que tengan una duración mayor a 3.30
            System.out.println("Álbumes con canciones de duración mayor a 3.30:");
            for (Document album : albumsCollection.find(elemMatch("canciones", gte("duracion", 3.30)))) {
                System.out.println(album.toJson());
            }
        }
    }

    public static void updateDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("musicaDB");
            MongoCollection<Document> albumsCollection = database.getCollection("albumes");

            // Actualizar el año de un álbum específico
            albumsCollection.updateOne(eq("_id", "1"), set("año", 2003));
            System.out.println("Álbum actualizado: { '_id': '1', 'año': 2003 }");

            // // Actualizar la duración de una canción específica
            // albumsCollection.updateOne(
            //         eq("_id", "1"),
            //         set("canciones.$[cancion].duracion", 3.50),
            //         new Document("arrayFilters", Arrays.asList(new Document("cancion.titulo", "In My Place")))
            // );
            // System.out.println("Canción actualizada: { 'titulo': 'In My Place', 'duracion': 3.50 }");
        }
    }

    public static void deleteDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("musicaDB");
            MongoCollection<Document> albumsCollection = database.getCollection("albumes");

            // Eliminar un álbum específico
            albumsCollection.deleteOne(eq("_id", "1"));
            System.out.println("Álbum eliminado: { '_id': '1' }");
        }
    }
}
