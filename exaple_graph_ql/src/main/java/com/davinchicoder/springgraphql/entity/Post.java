package com.davinchicoder.springgraphql.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/** Entity: representa el modelo interno/persistente (BD) con identidad y estado;
 * DTO: objeto ligero para transferir datos entre capas sin exponer la entidad ni su lógica. */
@Data
@Builder
public class Post {

    private String id;
    private String name;
    private String monster;
    private Integer goreLevel;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

}
