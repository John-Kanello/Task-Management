package taskmanagement.util;

public interface Mapper<X,Y> {
    Y toDto(X entity);
}
