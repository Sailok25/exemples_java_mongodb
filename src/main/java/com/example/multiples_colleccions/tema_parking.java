package com.example.multiples_colleccions;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import java.util.Arrays;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.set;

public class tema_parking {
    public static void main(String[] args) {
        insertDocuments();
        findDocuments();
        updateDocuments();
        deleteDocuments();
    }

    public static void insertDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("parkingDB");

            // Insertar vehículos
            MongoCollection<Document> vehiculosCollection = database.getCollection("vehiculos");
            Document vehiculo1 = new Document("_id", "1")
                    .append("matricula", "1234ABC")
                    .append("marca", "Toyota")
                    .append("modelo", "Corolla")
                    .append("color", "Rojo");
            vehiculosCollection.insertOne(vehiculo1);

            // Insertar estacionamientos
            MongoCollection<Document> estacionamientosCollection = database.getCollection("estacionamientos");
            Document estacionamiento1 = new Document("_id", "1")
                    .append("vehiculo_id", "1")
                    .append("fecha_entrada", "2023-10-01T08:00:00Z")
                    .append("fecha_salida", "2023-10-01T10:00:00Z")
                    .append("plaza", "A12");
            estacionamientosCollection.insertOne(estacionamiento1);

            System.out.println("Documentos insertados correctamente!");
        }
    }

    public static void findDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("parkingDB");
            MongoCollection<Document> vehiculosCollection = database.getCollection("vehiculos");
            MongoCollection<Document> estacionamientosCollection = database.getCollection("estacionamientos");

            // Buscar todos los vehículos
            System.out.println("Todos los vehículos:");
            for (Document vehiculo : vehiculosCollection.find()) {
                System.out.println(vehiculo.toJson());
            }

            // Buscar estacionamientos de un vehículo específico
            System.out.println("Estacionamientos del vehículo con matrícula 1234ABC:");
            for (Document estacionamiento : estacionamientosCollection.find(eq("vehiculo_id", "1"))) {
                System.out.println(estacionamiento.toJson());
            }

            // Buscar estacionamientos en una plaza específica
            System.out.println("Estacionamientos en la plaza A12:");
            for (Document estacionamiento : estacionamientosCollection.find(eq("plaza", "A12"))) {
                System.out.println(estacionamiento.toJson());
            }
        }
    }

    public static void updateDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("parkingDB");
            MongoCollection<Document> estacionamientosCollection = database.getCollection("estacionamientos");

            // Actualizar la fecha de salida de un estacionamiento específico
            estacionamientosCollection.updateOne(eq("_id", "1"), set("fecha_salida", "2023-10-01T11:00:00Z"));
            System.out.println("Estacionamiento actualizado: { '_id': '1', 'fecha_salida': '2023-10-01T11:00:00Z' }");
        }
    }

    public static void deleteDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("parkingDB");
            MongoCollection<Document> estacionamientosCollection = database.getCollection("estacionamientos");

            // Eliminar un estacionamiento específico
            estacionamientosCollection.deleteOne(eq("_id", "1"));
            System.out.println("Estacionamiento eliminado: { '_id': '1' }");
        }
    }
}
