package com.example;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Arrays;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class examplesMongodb {
    public static void main(String[] args) {
        // Llamamos a los métodos para que se ejecuten en orden
        insertDocuments();
        findDocuments();
        updateDocuments();
        deleteDocuments();
    }

    // Función para insertar documentos en una colección
    public static void insertDocuments() {
        // Ruta del servidor MongoDB
        String rutaServidor = "mongodb://localhost:27017";

        // Crear el cliente de MongoDB
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            System.out.println("Conectado a MongoDB en " + rutaServidor);

            // Obtener la base de datos (se creará si no existe)
            MongoDatabase database = mongoClient.getDatabase("testDB");
            System.out.println("Base de datos seleccionada: " + database.getName());

            // Obtener la colección (se creará si no existe)
            MongoCollection<Document> collection = database.getCollection("usuaris");
            System.out.println("Colección seleccionada: " + collection.getNamespace().getCollectionName());

            // Insertar un solo documento con _id personalizado
            Document doc1 = new Document("_id", "A1")
                    .append("nom", "Pere")
                    .append("edat", 22)
                    .append("ciutat", "València");
            collection.insertOne(doc1);
            System.out.println("Documento insertado: " + doc1.toJson());

            // Insertar múltiples documentos con _id personalizado
            Document doc2 = new Document("_id", "B2").append("nom", "Laura").append("edat", 27).append("ciutat", "Madrid");
            Document doc3 = new Document("_id", "Costafreda").append("nom", "Jordi").append("edat", 31).append("ciutat", "Sevilla");
            collection.insertMany(Arrays.asList(doc2, doc3));
            System.out.println("Documentos insertados: " + doc2.toJson() + ", " + doc3.toJson());

            System.out.println("Documentos insertados correctamente!");
        }
    }

    // Función para encontrar documentos en una colección
    public static void findDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("usuaris");

            // Encontrar todos los documentos
            System.out.println("Todos los documentos:");
            for (Document doc : collection.find()) {
                System.out.println(doc.toJson());
            }

            // Encontrar un documento específico
            Document user = collection.find(eq("nom", "Pere")).first();
            if (user != null) {
                System.out.println("Usuario encontrado: " + user.toJson());
            } else {
                System.out.println("Usuario no encontrado.");
            }
        }
    }

    // Función para actualizar documentos en una colección
    public static void updateDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("usuaris");

            // Actualizar un solo documento
            collection.updateOne(eq("nom", "Pere"), set("edat", 23));
            System.out.println("Documento actualizado: { 'nom': 'Pere', 'edat': 23 }");

            // Actualizar múltiples documentos
            collection.updateMany(eq("ciutat", "Madrid"), set("ciutat", "Barcelona"));
            System.out.println("Documentos actualizados: todos los que tenían 'ciutat': 'Madrid' ahora tienen 'ciutat': 'Barcelona'");

            System.out.println("Documentos actualizados correctamente!");
        }
    }

    // Función para eliminar documentos de una colección
    public static void deleteDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("usuaris");

            // Eliminar un solo documento (el primero que encuentre con el nombre "Pere")
            collection.deleteOne(eq("nom", "Pere"));
            System.out.println("Documento eliminado: { 'nom': 'Pere' }");

            // Eliminar múltiples documentos (se eliminan todos los documentos que concuerdan con la ciudad "Barcelona")
            collection.deleteMany(eq("ciutat", "Barcelona"));
            System.out.println("Documentos eliminados: todos los que tenían 'ciutat': 'Barcelona'");

            System.out.println("Documentos eliminados correctamente!");
        }
    }
}
