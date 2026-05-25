package com.davinchicoder.springgraphql.repository;

import com.davinchicoder.springgraphql.entity.Post;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Repositorio en memoria que simula operaciones CRUD sobre Post sin usar base de datos. */
@Repository
public class PostRepository {

    private final List<Post> POSTS = new ArrayList<>(
            List.of(
                    Post.builder().id(1L)
                            .name("Magma Drake")
                            .monster("Lava")
                            .goreLevel(9)
                            .imageUrl("https://example.com/magma-drake.jpg")
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build(),
                    Post.builder()
                            .id(2L)
                            .name("Cinder Worm")
                            .monster("Lava")
                            .goreLevel(7)
                            .imageUrl("https://example.com/cinder-worm.jpg")
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build(),
                    Post.builder()
                            .id(3L)
                            .name("Bloom Wraith")
                            .monster("Floral")
                            .goreLevel(4)
                            .imageUrl("https://example.com/bloom-wraith.jpg")
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build(),
                    Post.builder()
                            .id(4L)
                            .name("Thorn Stalker")
                            .monster("Floral")
                            .goreLevel(6)
                            .imageUrl("https://example.com/thorn-stalker.jpg")
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build(),
                    Post.builder()
                            .id(5L)
                            .name("Tide Lurker")
                            .monster("Water")
                            .goreLevel(7)
                            .imageUrl("https://example.com/tide-lurker.jpg")
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build(),
                    Post.builder()
                            .id(6L)
                            .name("Deep Leviathan")
                            .monster("Water")
                            .goreLevel(10)
                            .imageUrl("https://example.com/deep-leviathan.jpg")
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build()
            )
    );


    public List<Post> getRecentPosts(int count, int offset) {
        return POSTS.stream()
                .filter(post -> post.getDeletedAt() == null)
                .toList()
                .subList(offset, Math.min(offset + count, POSTS.size()));
    }

    public Post save(Post post) {
        post.setId(this.getNextId());
        post.setCreatedAt(LocalDateTime.now());

        POSTS.add(post);
        return post;
    }

    public Optional<Post> delete(Long id) {
        Optional<Post> postToDelete = POSTS.stream()
                .filter(post -> post.getId().equals(id))
                .findFirst();

        postToDelete.ifPresent(post -> post.setDeletedAt(LocalDateTime.now()));

        return postToDelete;
    }

    public Optional<Post> getById(Long id) {
        return POSTS.stream().filter(post -> post.getId().equals(id)).findFirst();
    }

    public List<Post> getAll() {
        return POSTS.stream().filter(post -> post.getDeletedAt() == null).toList();
    }

    private Long getNextId() {
        return POSTS.stream().mapToLong(Post::getId).max().orElse(0L) + 1L;
    }

}
