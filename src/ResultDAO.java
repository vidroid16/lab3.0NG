import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class ResultDAO {
    private EntityManager entityManager;

    public ResultDAO(){
        entityManager = Persistence.createEntityManagerFactory("jpa-persistence").createEntityManager();
    }

    public Result insertPoint(Result point) {
        entityManager.getTransaction().begin();
        entityManager.persist(point);
        entityManager.getTransaction().commit();
        return point;
    }

    public Result findResultPoint(String id) {
        return entityManager.find(Result.class, id);
    }

    public List<Result> findAllPoints() {
        TypedQuery<Result> query = entityManager.createQuery("Select e from Result e", Result.class);
        return query.getResultList();
    }

    public void removePoint(String id) {
        Result point = findResultPoint(id);
        if (point != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(point);
            entityManager.getTransaction().commit();
        }
    }

}
