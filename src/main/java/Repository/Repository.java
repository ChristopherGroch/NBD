package Repository;

import java.util.List;

public interface Repository<T,K> {
    T getByKey(K id);
    void save(T o);
    void delete(T o);
    List<T> getAllRecords();
}
