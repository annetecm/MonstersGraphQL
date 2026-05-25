package com.davinchicoder.springgraphql.repository;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Repository;

import com.davinchicoder.springgraphql.entity.Post;
import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;

@Repository
public class PostRepository {

    private static final String COLLECTION = "monsters";

    private final Firestore firestore;

    public PostRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public List<Post> getRecentPosts(int count, int offset) {
        if (count < 0 || offset < 0) {
            throw new IllegalArgumentException("count and offset must not be negative");
        }

        List<Post> posts = activePosts();
        if (offset >= posts.size()) {
            return List.of();
        }

        return posts.subList(offset, Math.min(offset + count, posts.size()));
    }

    public Post save(Post post) {
        DocumentReference reference = firestore.collection(COLLECTION).document();
        LocalDateTime now = LocalDateTime.now();

        post.setId(reference.getId());
        post.setCreatedAt(now);
        post.setUpdatedAt(now);

        esperar(reference.set(toDocument(post)));
        return post;
    }

    public Optional<Post> delete(String id) {
        Optional<Post> postToDelete = getById(id);
        postToDelete.ifPresent(post -> {
            LocalDateTime now = LocalDateTime.now();
            post.setDeletedAt(now);
            post.setUpdatedAt(now);
            esperar(firestore.collection(COLLECTION).document(id).update(Map.of(
                    "deletedAt", FieldValue.serverTimestamp(),
                    "updatedAt", FieldValue.serverTimestamp())));
        });
        return postToDelete;
    }

    public Optional<Post> getById(String id) {
        DocumentSnapshot document = esperar(firestore.collection(COLLECTION).document(id).get());
        if (!document.exists()) {
            return Optional.empty();
        }

        Post post = fromDocument(document);
        return post.getDeletedAt() == null ? Optional.of(post) : Optional.empty();
    }

    public List<Post> getAll() {
        return activePosts();
    }

    private List<Post> activePosts() {
        return esperar(firestore.collection(COLLECTION).get()).getDocuments().stream()
                .map(this::fromDocument)
                .filter(post -> post.getDeletedAt() == null)
                .sorted(Comparator.comparing(Post::getCreatedAt).reversed())
                .toList();
    }

    private Map<String, Object> toDocument(Post post) {
        Map<String, Object> document = new HashMap<>();
        document.put("name", post.getName());
        document.put("monster", post.getMonster());
        document.put("goreLevel", post.getGoreLevel());
        document.put("imageUrl", post.getImageUrl());
        document.put("createdAt", FieldValue.serverTimestamp());
        document.put("updatedAt", FieldValue.serverTimestamp());
        return document;
    }

    private Post fromDocument(DocumentSnapshot document) {
        return Post.builder()
                .id(document.getId())
                .name(document.getString("name"))
                .monster(document.getString("monster"))
                .goreLevel(toInteger(document.getLong("goreLevel")))
                .imageUrl(document.getString("imageUrl"))
                .createdAt(toLocalDateTime(document.getTimestamp("createdAt")))
                .updatedAt(toLocalDateTime(document.getTimestamp("updatedAt")))
                .deletedAt(toLocalDateTime(document.getTimestamp("deletedAt")))
                .build();
    }

    private Integer toInteger(Long value) {
        return value == null ? null : value.intValue();
    }

    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp == null
                ? null
                : LocalDateTime.ofInstant(timestamp.toDate().toInstant(), ZoneId.systemDefault());
    }

    private <T> T esperar(ApiFuture<T> resultado) {
        try {
            return resultado.get();
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Operacion con Firestore interrumpida", exception);
        } catch (ExecutionException exception) {
            throw new IllegalStateException("No fue posible acceder a Firestore", exception.getCause());
        }
    }
}
