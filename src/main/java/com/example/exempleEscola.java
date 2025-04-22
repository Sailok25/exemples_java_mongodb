package com.example;

import com.mongodb.client.*;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.*;

import static com.mongodb.client.model.Filters.eq;

public class exempleEscola {

    static MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    static MongoDatabase db = mongoClient.getDatabase("escuelaDB");

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Crear escuela");
            System.out.println("2. Crear aula");
            System.out.println("3. Crear persona");
            System.out.println("4. Ver personas");
            System.out.println("5. Salir");
            System.out.print("Selecciona una opción: ");
            int opcion = Integer.parseInt(sc.nextLine());

            switch (opcion) {
                case 1 -> crearEscuela();
                case 2 -> crearAula();
                case 3 -> crearPersona();
                case 4 -> verPersonas();
                case 5 -> {
                    mongoClient.close();
                    return;
                }
                default -> System.out.println("Opción no válida.");
            }
        }
    }

    public static void crearEscuela() {
        MongoCollection<Document> escuelas = db.getCollection("escuelas");
        System.out.print("Nombre de la escuela: ");
        String nombre = sc.nextLine();
        System.out.print("Dirección: ");
        String direccion = sc.nextLine();

        Document doc = new Document("nombre", nombre).append("direccion", direccion);
        escuelas.insertOne(doc);
        System.out.println("Escuela creada con ID: " + doc.getObjectId("_id"));
    }

    public static void crearAula() {
        MongoCollection<Document> escuelas = db.getCollection("escuelas");
        MongoCollection<Document> aulas = db.getCollection("aulas");

        System.out.println("Lista de escuelas:");
        for (Document esc : escuelas.find()) {
            System.out.println(esc.getObjectId("_id") + " - " + esc.getString("nombre"));
        }

        System.out.print("ID de la escuela a la que pertenece el aula: ");
        String escuelaId = sc.nextLine();

        System.out.print("Nombre del aula: ");
        String nombre = sc.nextLine();
        System.out.print("Capacidad: ");
        int capacidad = Integer.parseInt(sc.nextLine());

        Document aula = new Document("nombre", nombre)
                .append("capacidad", capacidad)
                .append("escuela_id", new ObjectId(escuelaId));

        aulas.insertOne(aula);
        System.out.println("Aula creada correctamente.");
    }

    public static void crearPersona() {
        MongoCollection<Document> escuelas = db.getCollection("escuelas");
        MongoCollection<Document> aulas = db.getCollection("aulas");
        MongoCollection<Document> personas = db.getCollection("personas");

        // Elegir escuela
        System.out.println("Escuelas disponibles:");
        for (Document esc : escuelas.find()) {
            System.out.println(esc.getObjectId("_id") + " - " + esc.getString("nombre"));
        }
        System.out.print("ID de la escuela: ");
        ObjectId escuelaId = new ObjectId(sc.nextLine());

        // Elegir aula
        System.out.println("Aulas disponibles en esa escuela:");
        for (Document aula : aulas.find(eq("escuela_id", escuelaId))) {
            System.out.println(aula.getObjectId("_id") + " - " + aula.getString("nombre"));
        }
        System.out.print("ID del aula: ");
        ObjectId aulaId = new ObjectId(sc.nextLine());

        System.out.print("Nombre de la persona: ");
        String nombre = sc.nextLine();
        System.out.print("Rol (alumno/profesor): ");
        String rol = sc.nextLine();

        // Teléfonos
        List<Document> telefonos = new ArrayList<>();
        System.out.print("¿Cuántos teléfonos quieres añadir? ");
        int cantidad = Integer.parseInt(sc.nextLine());

        for (int i = 1; i <= cantidad; i++) {
            System.out.print("Teléfono " + i + ": ");
            String tel = sc.nextLine();
            telefonos.add(new Document("telefono", tel));
        }

        Document persona = new Document("nombre", nombre)
                .append("rol", rol)
                .append("telefonos", telefonos)
                .append("escuela_id", escuelaId)
                .append("aula_id", aulaId);

        personas.insertOne(persona);
        System.out.println("Persona registrada con éxito.");
    }

    public static void verPersonas() {
        MongoCollection<Document> personas = db.getCollection("personas");
        System.out.println("Lista de personas:");
        for (Document p : personas.find()) {
            System.out.println(p.toJson());
        }
    }
}
