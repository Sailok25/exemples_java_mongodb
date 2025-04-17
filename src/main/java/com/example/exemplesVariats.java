package com.example;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Arrays;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class exemplesVariats {
    private static final String rutaServidor = "mongodb://localhost:27017";
    public static void main(String[] args) {
        // Ejecutar ejemplos de música
        insertMusicDocuments();
        findMusicDocuments();
        updateMusicDocuments();
        deleteMusicDocuments();

        // Ejecutar ejemplos de parking
        insertParkingDocuments();
        findParkingDocuments();
        updateParkingDocuments();
        deleteParkingDocuments();

        // Ejecutar ejemplos de supermercado
        insertSupermarketDocuments();
        findSupermarketDocuments();
        updateSupermarketDocuments();
        deleteSupermarketDocuments();

        // Ejecutar ejemplos de películas
        insertMovieDocuments();
        findMovieDocuments();
        updateMovieDocuments();
        deleteMovieDocuments();

        // Ejecutar ejemplos de veterinaria
        insertVetDocuments();
        findVetDocuments();
        updateVetDocuments();
        deleteVetDocuments();
    }

    // Métodos para la temática de música
    public static void insertMusicDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("musica");

            Document doc1 = new Document("titulo", "Canción 1")
                    .append("artista", "Artista 1")
                    .append("genero", "Rock");
            collection.insertOne(doc1);

            Document doc2 = new Document("titulo", "Canción 2").append("artista", "Artista 2").append("genero", "Pop");
            Document doc3 = new Document("titulo", "Canción 3").append("artista", "Artista 3").append("genero", "Jazz");
            collection.insertMany(Arrays.asList(doc2, doc3));

            System.out.println("Documentos de música insertados correctamente!");
        }
    }

    public static void findMusicDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("musica");

            System.out.println("Todos los documentos de música:");
            for (Document doc : collection.find()) {
                System.out.println(doc.toJson());
            }

            Document song = collection.find(eq("titulo", "Canción 1")).first();
            if (song != null) {
                System.out.println("Canción encontrada: " + song.toJson());
            } else {
                System.out.println("Canción no encontrada.");
            }
        }
    }

    public static void updateMusicDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("musica");

            collection.updateOne(eq("titulo", "Canción 1"), set("genero", "Pop Rock"));
            collection.updateMany(eq("genero", "Pop"), set("genero", "Pop Latino"));

            System.out.println("Documentos de música actualizados correctamente!");
        }
    }

    public static void deleteMusicDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("musica");

            collection.deleteOne(eq("titulo", "Canción 1"));
            collection.deleteMany(eq("genero", "Pop Latino"));

            System.out.println("Documentos de música eliminados correctamente!");
        }
    }

    // Métodos para la temática de parking
    public static void insertParkingDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("parking");

            Document doc1 = new Document("plaza", "A1")
                    .append("estado", "ocupado")
                    .append("vehiculo", "Coche");
            collection.insertOne(doc1);

            Document doc2 = new Document("plaza", "B2").append("estado", "libre").append("vehiculo", "Moto");
            Document doc3 = new Document("plaza", "C3").append("estado", "ocupado").append("vehiculo", "Coche");
            collection.insertMany(Arrays.asList(doc2, doc3));

            System.out.println("Documentos de parking insertados correctamente!");
        }
    }

    public static void findParkingDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("parking");

            System.out.println("Todos los documentos de parking:");
            for (Document doc : collection.find()) {
                System.out.println(doc.toJson());
            }

            Document plaza = collection.find(eq("plaza", "A1")).first();
            if (plaza != null) {
                System.out.println("Plaza encontrada: " + plaza.toJson());
            } else {
                System.out.println("Plaza no encontrada.");
            }
        }
    }

    public static void updateParkingDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("parking");

            collection.updateOne(eq("plaza", "A1"), set("estado", "libre"));
            collection.updateMany(eq("vehiculo", "Coche"), set("estado", "ocupado"));

            System.out.println("Documentos de parking actualizados correctamente!");
        }
    }

    public static void deleteParkingDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("parking");

            collection.deleteOne(eq("plaza", "A1"));
            collection.deleteMany(eq("estado", "libre"));

            System.out.println("Documentos de parking eliminados correctamente!");
        }
    }

    // Métodos para la temática de supermercado
    public static void insertSupermarketDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("supermercado");

            Document doc1 = new Document("producto", "Leche")
                    .append("precio", 1.2)
                    .append("cantidad", 100);
            collection.insertOne(doc1);

            Document doc2 = new Document("producto", "Pan").append("precio", 0.8).append("cantidad", 50);
            Document doc3 = new Document("producto", "Huevos").append("precio", 1.5).append("cantidad", 200);
            collection.insertMany(Arrays.asList(doc2, doc3));

            System.out.println("Documentos de supermercado insertados correctamente!");
        }
    }

    public static void findSupermarketDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("supermercado");

            System.out.println("Todos los documentos de supermercado:");
            for (Document doc : collection.find()) {
                System.out.println(doc.toJson());
            }

            Document producto = collection.find(eq("producto", "Leche")).first();
            if (producto != null) {
                System.out.println("Producto encontrado: " + producto.toJson());
            } else {
                System.out.println("Producto no encontrado.");
            }
        }
    }

    public static void updateSupermarketDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("supermercado");

            collection.updateOne(eq("producto", "Leche"), set("precio", 1.3));
            collection.updateMany(eq("cantidad", 50), set("cantidad", 60));

            System.out.println("Documentos de supermercado actualizados correctamente!");
        }
    }

    public static void deleteSupermarketDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("supermercado");

            collection.deleteOne(eq("producto", "Leche"));
            collection.deleteMany(eq("cantidad", 60));

            System.out.println("Documentos de supermercado eliminados correctamente!");
        }
    }

    // Métodos para la temática de películas
    public static void insertMovieDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("peliculas");

            Document doc1 = new Document("titulo", "Película 1")
                    .append("director", "Director 1")
                    .append("genero", "Acción");
            collection.insertOne(doc1);

            Document doc2 = new Document("titulo", "Película 2").append("director", "Director 2").append("genero", "Comedia");
            Document doc3 = new Document("titulo", "Película 3").append("director", "Director 3").append("genero", "Drama");
            collection.insertMany(Arrays.asList(doc2, doc3));

            System.out.println("Documentos de películas insertados correctamente!");
        }
    }

    public static void findMovieDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("peliculas");

            System.out.println("Todos los documentos de películas:");
            for (Document doc : collection.find()) {
                System.out.println(doc.toJson());
            }

            Document pelicula = collection.find(eq("titulo", "Película 1")).first();
            if (pelicula != null) {
                System.out.println("Película encontrada: " + pelicula.toJson());
            } else {
                System.out.println("Película no encontrada.");
            }
        }
    }

    public static void updateMovieDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("peliculas");

            collection.updateOne(eq("titulo", "Película 1"), set("genero", "Ciencia Ficción"));
            collection.updateMany(eq("genero", "Comedia"), set("genero", "Comedia Romántica"));

            System.out.println("Documentos de películas actualizados correctamente!");
        }
    }

    public static void deleteMovieDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("peliculas");

            collection.deleteOne(eq("titulo", "Película 1"));
            collection.deleteMany(eq("genero", "Comedia Romántica"));

            System.out.println("Documentos de películas eliminados correctamente!");
        }
    }

    // Métodos para la temática de veterinaria
    public static void insertVetDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("veterinaria");

            Document doc1 = new Document("mascota", "Perro 1")
                    .append("dueño", "Dueño 1")
                    .append("tratamiento", "Vacunas");
            collection.insertOne(doc1);

            Document doc2 = new Document("mascota", "Gato 1").append("dueño", "Dueño 2").append("tratamiento", "Desparasitación");
            Document doc3 = new Document("mascota", "Perro 2").append("dueño", "Dueño 3").append("tratamiento", "Revisión");
            collection.insertMany(Arrays.asList(doc2, doc3));

            System.out.println("Documentos de veterinaria insertados correctamente!");
        }
    }

    public static void findVetDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("veterinaria");

            System.out.println("Todos los documentos de veterinaria:");
            for (Document doc : collection.find()) {
                System.out.println(doc.toJson());
            }

            Document mascota = collection.find(eq("mascota", "Perro 1")).first();
            if (mascota != null) {
                System.out.println("Mascota encontrada: " + mascota.toJson());
            } else {
                System.out.println("Mascota no encontrada.");
            }
        }
    }

    public static void updateVetDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("veterinaria");

            collection.updateOne(eq("mascota", "Perro 1"), set("tratamiento", "Revisión Anual"));
            collection.updateMany(eq("tratamiento", "Desparasitación"), set("tratamiento", "Desparasitación y Vacunas"));

            System.out.println("Documentos de veterinaria actualizados correctamente!");
        }
    }

    public static void deleteVetDocuments() {
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("veterinaria");

            collection.deleteOne(eq("mascota", "Perro 1"));
            collection.deleteMany(eq("tratamiento", "Desparasitación y Vacunas"));

            System.out.println("Documentos de veterinaria eliminados correctamente!");
        }
    }
}
