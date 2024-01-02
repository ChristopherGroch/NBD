package Repository;

public interface Repository<T,K>{
    T select(K id);
    void insert(T o);

    void delete(T o);

    void update(T o);

}
