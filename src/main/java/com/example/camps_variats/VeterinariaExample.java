package com.example.camps_variats;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.types.ObjectId;
import java.util.Arrays;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.set;

public class VeterinariaExample {
    public static void main(String[] args) {
        insertDocuments();
        findDocuments();
        updateDocuments();
        deleteDocuments();
    }

    public static void insertDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("veterinariaDB");

            // Insertar dueños
            MongoCollection<Document> dueñosCollection = database.getCollection("dueños");
            Document dueño1 = new Document("_id", new ObjectId())
                    .append("nombre", "Juan Pérez")
                    .append("direccion", new Document("calle", "Calle Mayor").append("ciudad", "Valencia"))
                    .append("telefono", "987654321")
                    .append("mascotas", Arrays.asList());
            dueñosCollection.insertOne(dueño1);

            // Insertar veterinarios
            MongoCollection<Document> veterinariosCollection = database.getCollection("veterinarios");
            Document veterinario1 = new Document("_id", new ObjectId())
                    .append("nombre", "Dr. García")
                    .append("especialidad", "Cirugía")
                    .append("consultorio", "Consultorio A")
                    .append("mascotas_atendidas", Arrays.asList());
            veterinariosCollection.insertOne(veterinario1);

            // Insertar mascotas
            MongoCollection<Document> mascotasCollection = database.getCollection("mascotas");
            Document mascota1 = new Document("_id", new ObjectId())
                    .append("nombre", "Fido")
                    .append("especie", "Perro")
                    .append("raza", "Labrador")
                    .append("edad", 5)
                    .append("dueño_id", dueño1.getObjectId("_id"))
                    .append("veterinario_id", veterinario1.getObjectId("_id"))
                    .append("vacunas", Arrays.asList(
                            new Document("nombre", "Rabia").append("fecha", "2023-01-01"),
                            new Document("nombre", "Parvovirus").append("fecha", "2023-02-01")
                    ))
                    .append("historial_medico", Arrays.asList(
                            new Document("fecha", "2023-03-01").append("diagnostico", "Otitis").append("tratamiento", "Antibióticos")
                    ));
            mascotasCollection.insertOne(mascota1);

            // Actualizar dueño con la mascota
            dueñosCollection.updateOne(
                    eq("_id", dueño1.getObjectId("_id")),
                    set("mascotas", Arrays.asList(mascota1.getObjectId("_id")))
            );

            // Actualizar veterinario con la mascota atendida
            veterinariosCollection.updateOne(
                    eq("_id", veterinario1.getObjectId("_id")),
                    set("mascotas_atendidas", Arrays.asList(mascota1.getObjectId("_id")))
            );

            System.out.println("Documentos insertados correctamente!");
        }
    }

    public static void findDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("veterinariaDB");
            MongoCollection<Document> mascotasCollection = database.getCollection("mascotas");
            MongoCollection<Document> dueñosCollection = database.getCollection("dueños");
            MongoCollection<Document> veterinariosCollection = database.getCollection("veterinarios");

            // Buscar todas las mascotas
            System.out.println("Todas las mascotas:");
            for (Document mascota : mascotasCollection.find()) {
                System.out.println(mascota.toJson());
            }

            // Buscar mascotas de un dueño específico
            Document dueño = dueñosCollection.find(eq("nombre", "Juan Pérez")).first();
            if (dueño != null) {
                System.out.println("Mascotas de Juan Pérez:");
                for (Document mascota : mascotasCollection.find(eq("dueño_id", dueño.getObjectId("_id")))) {
                    System.out.println(mascota.toJson());
                }
            }

            // Buscar mascotas atendidas por un veterinario específico
            Document veterinario = veterinariosCollection.find(eq("nombre", "Dr. García")).first();
            if (veterinario != null) {
                System.out.println("Mascotas atendidas por Dr. García:");
                for (Document mascota : mascotasCollection.find(eq("veterinario_id", veterinario.getObjectId("_id")))) {
                    System.out.println(mascota.toJson());
                }
            }
        }
    }

    public static void updateDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("veterinariaDB");
            MongoCollection<Document> mascotasCollection = database.getCollection("mascotas");

            // Actualizar la edad de una mascota específica
            mascotasCollection.updateOne(eq("nombre", "Fido"), set("edad", 6));
            System.out.println("Mascota actualizada: { 'nombre': 'Fido', 'edad': 6 }");

            // Añadir una nueva vacuna a una mascota específica
            mascotasCollection.updateOne(
                    eq("nombre", "Fido"),
                    set("vacunas", Arrays.asList(
                            new Document("nombre", "Rabia").append("fecha", "2023-01-01"),
                            new Document("nombre", "Parvovirus").append("fecha", "2023-02-01"),
                            new Document("nombre", "Hepatitis").append("fecha", "2023-04-01")
                    ))
            );
            System.out.println("Vacuna añadida: { 'nombre': 'Hepatitis', 'fecha': '2023-04-01' }");
        }
    }

    public static void deleteDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("veterinariaDB");
            MongoCollection<Document> mascotasCollection = database.getCollection("mascotas");

            // Eliminar una mascota específica
            mascotasCollection.deleteOne(eq("nombre", "Fido"));
            System.out.println("Mascota eliminada: { 'nombre': 'Fido' }");
        }
    }
}
