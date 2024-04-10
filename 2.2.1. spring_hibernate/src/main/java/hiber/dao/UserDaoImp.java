package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {
    @Autowired
    private Environment env;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public User getUserWithCurrentCar(String model, int series) {
        TypedQuery<User> query = sessionFactory.getCurrentSession()
                .createQuery("from User where car.model = :model and car.series = :series")
                .setParameter("model", model)
                .setParameter("series", series);

        if (query.getResultList().size() > 0) {
            return query.getResultList().get(0);
        } else {
            return null;
        }
    }

    /*
    Далее идут вспомогательные методы, чтоб не лазить каждый раз в Workbench
    */

    @Override
    public void truncateAllTables() {
        existsTables("truncate");
    }

    @Override
    public void createUsersAndCarsTables() {
        Session session = sessionFactory.getCurrentSession();

        session.createSQLQuery(
                        "create table if not exists " +
                                env.getProperty("db.tableCars") +
                                "( id bigint primary key auto_increment, " +
                                "model varchar(40) not null, " +
                                "series int not null);")
                .executeUpdate();

        session.createSQLQuery(
                        "create table if not exists " +
                                env.getProperty("db.tableUsers") +
                                " (id bigint primary key auto_increment, " +
                                "firstName varchar(40) not null, " +
                                "lastName varchar(40) not null, " +
                                "email varchar(40) not null, " +
                                "car_id bigint, " +
                                "foreign key (car_id) references " +
                                env.getProperty("db.tableCars") + "(id));")
                .executeUpdate();
    }

    @Override
    public void dropTables() {
        existsTables("drop");
    }

    private void existsTables(String action) {
        Session session = sessionFactory.getCurrentSession();

        session.createSQLQuery("set foreign_key_checks = 0;").executeUpdate();

        session.createSQLQuery(action + " table " + env.getProperty("db.tableCars"))
                .executeUpdate();
        session.createSQLQuery(action + " table " + env.getProperty("db.tableUsers"))
                .executeUpdate();

        session.createSQLQuery("set foreign_key_checks = 1;").executeUpdate();
    }
}
