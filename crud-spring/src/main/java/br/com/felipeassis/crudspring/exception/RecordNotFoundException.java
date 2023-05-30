package br.com.felipeassis.crudspring.exception;

public class RecordNotFoundException extends RuntimeException {
    
    // private static final long serialVersionID = 1L;

    public RecordNotFoundException(Long id) {
        super(String.format("Registro não encontrado: %s", id));
    }

}
