package com.example.maxim_exemple;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Arrays;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class BibliotecaExample {
    public static void main(String[] args) {
        insertDocuments();
        findDocuments();
        updateDocuments();
        deleteDocuments();
    }

    public static void insertDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("bibliotecaDB");

            // Insertar autores
            MongoCollection<Document> autoresCollection = database.getCollection("autores");
            Document autor1 = new Document("_id", "autor-1")
                    .append("nombre", "Miguel de Cervantes")
                    .append("nacionalidad", "Española")
                    .append("libros", Arrays.asList());
            autoresCollection.insertOne(autor1);

            // Insertar usuarios
            MongoCollection<Document> usuariosCollection = database.getCollection("usuarios");
            Document usuario1 = new Document("_id", "usuario-1")
                    .append("nombre", "Ana García")
                    .append("direccion", new Document("calle", "Calle Libros").append("ciudad", "Madrid"))
                    .append("libros_prestados", Arrays.asList());
            usuariosCollection.insertOne(usuario1);

            // Insertar libros
            MongoCollection<Document> librosCollection = database.getCollection("libros");
            Document libro1 = new Document("_id", "ISBN-123456789")
                    .append("titulo", "El Quijote")
                    .append("autor_id", "autor-1")
                    .append("año_publicacion", 1605)
                    .append("genero", "Novela")
                    .append("disponible", true)
                    .append("reservas", Arrays.asList(
                            new Document("usuario_id", "usuario-1").append("fecha_reserva", "2023-10-01"),
                            new Document("usuario_id", "usuario-2").append("fecha_reserva", "2023-10-02")
                    ));
            librosCollection.insertOne(libro1);

            // Actualizar autor con el libro
            autoresCollection.updateOne(
                    eq("_id", "autor-1"),
                    set("libros", Arrays.asList("ISBN-123456789"))
            );

            // Actualizar usuario con el libro prestado
            usuariosCollection.updateOne(
                    eq("_id", "usuario-1"),
                    set("libros_prestados", Arrays.asList("ISBN-123456789"))
            );

            System.out.println("Documentos insertados correctamente!");
        }
    }

    public static void findDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("bibliotecaDB");
            MongoCollection<Document> librosCollection = database.getCollection("libros");
            MongoCollection<Document> autoresCollection = database.getCollection("autores");
            MongoCollection<Document> usuariosCollection = database.getCollection("usuarios");

            // Buscar todos los libros
            System.out.println("Todos los libros:");
            for (Document libro : librosCollection.find()) {
                System.out.println(libro.toJson());
            }

            // Buscar libros de un autor específico
            Document autor = autoresCollection.find(eq("nombre", "Miguel de Cervantes")).first();
            if (autor != null) {
                System.out.println("Libros de Miguel de Cervantes:");
                for (Document libro : librosCollection.find(eq("autor_id", autor.getString("_id")))) {
                    System.out.println(libro.toJson());
                }
            }

            // Buscar libros prestados por un usuario específico
            Document usuario = usuariosCollection.find(eq("nombre", "Ana García")).first();
            if (usuario != null) {
                System.out.println("Libros prestados a Ana García:");
                for (Document libro : librosCollection.find(in("_id", usuario.getList("libros_prestados", String.class)))) {
                    System.out.println(libro.toJson());
                }
            }

            // Buscar libros disponibles
            System.out.println("Libros disponibles:");
            for (Document libro : librosCollection.find(eq("disponible", true))) {
                System.out.println(libro.toJson());
            }

            // Buscar libros con reservas
            System.out.println("Libros con reservas:");
            for (Document libro : librosCollection.find(exists("reservas", true))) {
                System.out.println(libro.toJson());
            }
        }
    }

    public static void updateDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("bibliotecaDB");
            MongoCollection<Document> librosCollection = database.getCollection("libros");

            // Actualizar la disponibilidad de un libro específico
            librosCollection.updateOne(eq("_id", "ISBN-123456789"), set("disponible", false));
            System.out.println("Libro actualizado: { '_id': 'ISBN-123456789', 'disponible': false }");

            // Añadir una nueva reserva a un libro específico
            librosCollection.updateOne(
                    eq("_id", "ISBN-123456789"),
                    addToSet("reservas", new Document("usuario_id", "usuario-3").append("fecha_reserva", "2023-10-03"))
            );
            System.out.println("Reserva añadida: { 'usuario_id': 'usuario-3', 'fecha_reserva': '2023-10-03' }");

            // Actualizar el año de publicación de un libro específico
            librosCollection.updateOne(eq("_id", "ISBN-123456789"), set("año_publicacion", 1615));
            System.out.println("Libro actualizado: { '_id': 'ISBN-123456789', 'año_publicacion': 1615 }");
        }
    }

    public static void deleteDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("bibliotecaDB");
            MongoCollection<Document> librosCollection = database.getCollection("libros");

            // Eliminar un libro específico
            librosCollection.deleteOne(eq("_id", "ISBN-123456789"));
            System.out.println("Libro eliminado: { '_id': 'ISBN-123456789' }");

            // Eliminar libros de un autor específico
            librosCollection.deleteMany(eq("autor_id", "autor-1"));
            System.out.println("Libros del autor 'autor-1' eliminados.");

            // Eliminar libros que no están disponibles
            librosCollection.deleteMany(eq("disponible", false));
            System.out.println("Libros no disponibles eliminados.");
        }
    }
}
