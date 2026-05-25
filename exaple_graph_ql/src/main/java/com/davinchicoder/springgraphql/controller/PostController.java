package com.davinchicoder.springgraphql.controller;

import com.davinchicoder.springgraphql.dto.PostDto;
import com.davinchicoder.springgraphql.entity.Post;
import com.davinchicoder.springgraphql.exception.PostNotFound;
import com.davinchicoder.springgraphql.mapper.PostMapper;
import com.davinchicoder.springgraphql.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {

    /** Repositorio para acceder y manipular datos de publicaciones. */
    private final PostRepository postRepository;

    /** Mapper para convertir PostDto a entidad Post. */
    private final PostMapper postMapper;

    /** Obtiene monstruos recientes con paginación (count, offset). */
    @QueryMapping
    public List<Post> getRecentMonsters(@Argument int count, @Argument int offset) {
        return postRepository.getRecentPosts(count, offset);
    }

    /** Obtiene un monstruo por su ID o lanza PostNotFound. */
    @QueryMapping
    public Post getMonsterById(@Argument String id) {
        return postRepository.getById(id).orElseThrow(PostNotFound::new);
    }

    /** Obtiene todos los monstruos. */
    @QueryMapping
    public List<Post> getAllMonsters() {
        return postRepository.getAll();
    }

    /** Elimina un monstruo por ID o lanza PostNotFound. */
    @MutationMapping
    public Post deleteMonsterById(@Argument String id) {

        return postRepository.delete(id).orElseThrow(PostNotFound::new);
    }

    /** Guarda un nuevo monstruo a partir de MonsterDto. */
    @MutationMapping
    public Post saveMonster(@Argument PostDto monsterDto) {
        Post post = postMapper.apply(monsterDto);
        return postRepository.save(post);
    }
}
