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

public class SupermercadoExample {
    public static void main(String[] args) {
        insertDocuments();
        findDocuments();
        updateDocuments();
        deleteDocuments();
    }

    public static void insertDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("supermercadoDB");

            // Insertar categorías
            MongoCollection<Document> categoriasCollection = database.getCollection("categorias");
            Document categoria1 = new Document("_id", new ObjectId())
                    .append("nombre", "Lácteos")
                    .append("descripcion", "Productos lácteos como leche, yogur, queso, etc.");
            categoriasCollection.insertOne(categoria1);

            // Insertar proveedores
            MongoCollection<Document> proveedoresCollection = database.getCollection("proveedores");
            Document proveedor1 = new Document("_id", new ObjectId())
                    .append("nombre", "Proveedor A")
                    .append("direccion", new Document("calle", "Calle Principal").append("ciudad", "Madrid"))
                    .append("contacto", new Document("nombre", "Juan Pérez").append("telefono", "123456789"))
                    .append("productos_proveidos", Arrays.asList());
            proveedoresCollection.insertOne(proveedor1);

            // Insertar productos
            MongoCollection<Document> productosCollection = database.getCollection("productos");
            Document producto1 = new Document("_id", new ObjectId())
                    .append("nombre", "Leche")
                    .append("precio", 1.20)
                    .append("categoria_id", categoria1.getObjectId("_id"))
                    .append("proveedor_id", proveedor1.getObjectId("_id"))
                    .append("detalles", new Document("marca", "Marca A").append("unidad", "litro"))
                    .append("ingredientes", Arrays.asList("leche", "vitamina D"))
                    .append("stock", Arrays.asList(
                            new Document("tienda", "Tienda 1").append("cantidad", 50),
                            new Document("tienda", "Tienda 2").append("cantidad", 30)
                    ));
            productosCollection.insertOne(producto1);

            // Actualizar proveedor con el producto proveído
            proveedoresCollection.updateOne(
                    eq("_id", proveedor1.getObjectId("_id")),
                    set("productos_proveidos", Arrays.asList(producto1.getObjectId("_id")))
            );

            System.out.println("Documentos insertados correctamente!");
        }
    }

    public static void findDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("supermercadoDB");
            MongoCollection<Document> productosCollection = database.getCollection("productos");
            MongoCollection<Document> categoriasCollection = database.getCollection("categorias");
            MongoCollection<Document> proveedoresCollection = database.getCollection("proveedores");

            // Buscar todos los productos
            System.out.println("Todos los productos:");
            for (Document producto : productosCollection.find()) {
                System.out.println(producto.toJson());
            }

            // Buscar productos de una categoría específica
            Document categoria = categoriasCollection.find(eq("nombre", "Lácteos")).first();
            if (categoria != null) {
                System.out.println("Productos de la categoría Lácteos:");
                for (Document producto : productosCollection.find(eq("categoria_id", categoria.getObjectId("_id")))) {
                    System.out.println(producto.toJson());
                }
            }

            // Buscar productos de un proveedor específico
            Document proveedor = proveedoresCollection.find(eq("nombre", "Proveedor A")).first();
            if (proveedor != null) {
                System.out.println("Productos del proveedor Proveedor A:");
                for (Document producto : productosCollection.find(eq("proveedor_id", proveedor.getObjectId("_id")))) {
                    System.out.println(producto.toJson());
                }
            }
        }
    }

    public static void updateDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("supermercadoDB");
            MongoCollection<Document> productosCollection = database.getCollection("productos");

            // Actualizar el precio de un producto específico
            productosCollection.updateOne(eq("nombre", "Leche"), set("precio", 1.30));
            System.out.println("Producto actualizado: { 'nombre': 'Leche', 'precio': 1.30 }");

            // Actualizar la cantidad de stock en una tienda específica
            // productosCollection.updateOne(
            //         eq("nombre", "Leche"),
            //         set("stock.$[tienda].cantidad", 40),
            //         new Document("arrayFilters", Arrays.asList(new Document("tienda.tienda", "Tienda 1")))
            // );
            System.out.println("Stock actualizado: { 'tienda': 'Tienda 1', 'cantidad': 40 }");
        }
    }

    public static void deleteDocuments() {
        String rutaServidor = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(rutaServidor)) {
            MongoDatabase database = mongoClient.getDatabase("supermercadoDB");
            MongoCollection<Document> productosCollection = database.getCollection("productos");

            // Eliminar un producto específico
            productosCollection.deleteOne(eq("nombre", "Leche"));
            System.out.println("Producto eliminado: { 'nombre': 'Leche' }");
        }
    }
}
