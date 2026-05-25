package com.davinchicoder.springgraphql.exception;

/** Excepción personalizada que indica que un Post no fue encontrado,
 * extendiendo RuntimeException para manejar este caso específico
 * en la aplicación. */
public class PostNotFound extends RuntimeException {
    public PostNotFound() {
        super("Post not found");
    }
}
