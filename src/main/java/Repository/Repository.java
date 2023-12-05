package Repository;

import java.util.List;

public interface Repository<T,K,J,O> {
    T getByKey(K id);
    void save(T o);
    void delete(T o);
    void create(J o) throws Exception;
    List<T> getAllRecords();
    List<T> getAllArchiveRecords(O o);
}
