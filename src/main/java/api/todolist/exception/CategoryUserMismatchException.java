package api.todolist.exception;

public class CategoryUserMismatchException extends RuntimeException {
    public CategoryUserMismatchException(String message) {
        super(message);
    }
}
