package com.davinchicoder.springgraphql.dto;

import lombok.Builder;
import lombok.Data;

/** DTO (Data Transfer Object): objeto simple usado para transportar datos entre capas
 *  (ej. cliente ↔ servidor) sin exponer la entidad interna. */
@Data
@Builder
public class PostDto {

    private String name;
    private String monster;
    private Integer goreLevel;
    private String imageUrl;
}
