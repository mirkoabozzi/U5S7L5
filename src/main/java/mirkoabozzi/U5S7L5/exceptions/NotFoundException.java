package mirkoabozzi.U5S7L5.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("Element " + id + " not found");
    }

    public NotFoundException(String msg) {
        super(msg);
    }
}
