package com.davinchicoder.springgraphql.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;

@Service
public class FirebaseService {

    private static final String TEST_COLLECTION = "firebase_connection_tests";

    private final Firestore firestore;

    public FirebaseService(Firestore firestore) {
        this.firestore = firestore;
    }

    public Map<String, Object> guardarDato(Map<String, Object> valores) {
        Map<String, Object> documento = new HashMap<>(valores);
        documento.put("createdAt", FieldValue.serverTimestamp());

        DocumentReference referencia = firestore.collection(TEST_COLLECTION).document();
        esperar(referencia.set(documento));

        return obtenerDato(referencia.getId());
    }

    public Map<String, Object> obtenerDato(String id) {
        DocumentSnapshot documento = esperar(firestore.collection(TEST_COLLECTION).document(id).get());

        if (!documento.exists()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Documento no encontrado");
        }

        Map<String, Object> valores = new HashMap<>(documento.getData());
        valores.put("id", documento.getId());
        return valores;
    }

    private <T> T esperar(ApiFuture<T> resultado) {
        try {
            return resultado.get();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Operacion con Firebase interrumpida", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("No fue posible acceder a Firebase", exception.getCause());
        }
    }
}
