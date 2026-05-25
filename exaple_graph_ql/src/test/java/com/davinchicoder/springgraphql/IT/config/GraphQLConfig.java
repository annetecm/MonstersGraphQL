package com.davinchicoder.springgraphql.IT.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;

@Configuration
public class GraphQLConfig {

    @Bean
    public GraphQlTester graphQlTester() {

        WebTestClient client =
                WebTestClient.bindToServer()
                        .baseUrl("http://localhost:8080/graphql")
                        .build();

        return HttpGraphQlTester.create(client);
    }

}
