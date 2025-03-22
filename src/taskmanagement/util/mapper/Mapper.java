package taskmanagement.util.mapper;

public interface Mapper<X,Y> {
    Y toDto(X entity);
}
