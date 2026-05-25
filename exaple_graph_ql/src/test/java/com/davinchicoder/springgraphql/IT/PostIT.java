package com.davinchicoder.springgraphql.IT;

import com.davinchicoder.springgraphql.entity.Post;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PostIT {

    @Autowired
    private GraphQlTester graphQlTester;

    @Test
    void shouldGetAllPosts() {
        String query = """
                query {
                    getAllMonsters {
                        id
                        name
                        monster
                        goreLevel
                    }
                }
                """;

        graphQlTester.document(query)
                .execute()
                .path("data.getAllMonsters")
                .entityList(Post.class)
                .hasSize(6);
    }

    @Test
    void shouldCreatePost() {
        String mutation = """
                mutation {
                    saveMonster(monsterDto: {
                        name: "Banshee",
                        monster: "Specter",
                        goreLevel: 6,
                        imageUrl: "https://example.com/banshee.jpg",
                    }) {
                        id
                        title
                        content
                        author
                    }
                }
                """;

        graphQlTester.document(mutation)
                .execute()
                .path("data.saveMonster")
                .entity(Post.class)
                .satisfies(post -> {
                    assertNotNull(post.getId());
                    assertEquals("Banshee", post.getName());
                    assertEquals("Specter", post.getMonster());
                    assertEquals(6, post.getGoreLevel());
                });
    }
}