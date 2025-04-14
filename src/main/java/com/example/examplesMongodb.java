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
        // Cridem els mètodes per a que s'executin en ordre
        insertDocuments();
        findDocuments();
        updateDocuments();
        deleteDocuments();
    }

    // Funció per a inserir documents a una col·lecció
    public static void insertDocuments() {
        // Ruta del servidor MongoDB
        String rutaServidor = "mongodb://localhost:27017";

        // Crear el client de MongoDB
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            System.out.println("Connectat a MongoDB a " + rutaServidor);

            // Obtenir la base de dades (es crearà si no existeix)
            MongoDatabase database = mongoClient.getDatabase("testDB");
            System.out.println("Base de dades seleccionada: " + database.getName());

            // Obtenir la col·lecció (es crearà si no existeix)
            MongoCollection<Document> collection = database.getCollection("usuaris");
            System.out.println("Col·lecció seleccionada: " + collection.getNamespace().getCollectionName());

            // Inserir un sol document
            Document doc1 = new Document("nom", "Pere")
                    .append("edat", 22)
                    .append("ciutat", "València");
            collection.insertOne(doc1);
            System.out.println("Document inserit: " + doc1.toJson());

            // Inserir múltiples documents
            Document doc2 = new Document("nom", "Laura").append("edat", 27).append("ciutat", "Madrid");
            Document doc3 = new Document("nom", "Jordi").append("edat", 31).append("ciutat", "Sevilla");
            collection.insertMany(Arrays.asList(doc2, doc3));
            System.out.println("Documents inserits: " + doc2.toJson() + ", " + doc3.toJson());

            System.out.println("Documents inserits correctament!");
        }
    }

    // Funció per a trobar documents a una col·lecció
    public static void findDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("usuaris");

            // Trobar tots els documents
            System.out.println("Tots els documents:");
            for (Document doc : collection.find()) {
                System.out.println(doc.toJson());
            }

            // Trobar un document específic
            Document user = collection.find(eq("nom", "Pere")).first();
            if (user != null) {
                System.out.println("Usuari trobat: " + user.toJson());
            } else {
                System.out.println("Usuari no trobat.");
            }
        }
    }

    // Funció per a actualitzar documents a una col·lecció
    public static void updateDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("usuaris");

            // Actualitzar un sol document
            collection.updateOne(eq("nom", "Pere"), set("edat", 23));
            System.out.println("Document actualitzat: { 'nom': 'Pere', 'edat': 23 }");

            // Actualitzar múltiples documents
            collection.updateMany(eq("ciutat", "Madrid"), set("ciutat", "Barcelona"));
            System.out.println("Documents actualitzats: tots els que tenien 'ciutat': 'Madrid' ara tenen 'ciutat': 'Barcelona'");

            System.out.println("Documents actualitzats correctament!");
        }
    }

    // Funció per a eliminar documents d'una col·lecció
    public static void deleteDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("testDB");
            MongoCollection<Document> collection = database.getCollection("usuaris");

            // Eliminar un sol document (el primer que trobi amb el nom "Pere")
            collection.deleteOne(eq("nom", "Pere"));
            System.out.println("Document eliminat: { 'nom': 'Pere' }");

            // Eliminar múltiples documents (s'eliminen tots els documents que concorden amb la ciutat "Barcelona")
            collection.deleteMany(eq("ciutat", "Barcelona"));
            System.out.println("Documents eliminats: tots els que tenien 'ciutat': 'Barcelona'");

            System.out.println("Documents eliminats correctament!");
        }
    }
}