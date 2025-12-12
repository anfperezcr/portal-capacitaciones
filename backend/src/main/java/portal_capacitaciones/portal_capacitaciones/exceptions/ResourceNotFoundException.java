package portal_capacitaciones.portal_capacitaciones.exceptions;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND) // Mapea esta excepción al código de estado HTTP 404
public class ResourceNotFoundException extends RuntimeException {

    // Constructor que recibe el mensaje de error
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
