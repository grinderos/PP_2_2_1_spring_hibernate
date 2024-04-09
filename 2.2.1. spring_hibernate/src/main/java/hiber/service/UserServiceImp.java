package hiber.service;

import hiber.dao.UserDao;
import hiber.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public void add(User user) {
        userDao.add(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> listUsers() {
        return userDao.listUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserWithCurrentCar(String model, int series) {
        return userDao.getUserWithCurrentCar(model, series);
    }


    /*
    Далее идут вспомогательные методы, чтоб не лазить каждый раз в Workbench
    */

    @Override
    @Transactional
    public void truncateAllTables() {
        userDao.truncateAllTables();
    }

    @Override
    @Transactional
    public void createUsersAndCarsTables() {
        userDao.createUsersAndCarsTables();
    }

    @Override
    @Transactional
    public void createDB() {
        userDao.createDB();
    }

    @Override
    @Transactional
    public void dropTables() {
        userDao.dropTables();
    }

}
