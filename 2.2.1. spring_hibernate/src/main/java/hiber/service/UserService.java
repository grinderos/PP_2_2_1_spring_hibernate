package hiber.service;

import hiber.model.User;

import java.util.List;

public interface UserService {
    void add(User user);
    List<User> listUsers();
    User getUserWithCurrentCar(String model, int series);

    /*
    Далее идут вспомогательные методы, чтоб не лазить каждый раз в Workbench
    */
    void truncateAllTables();
    void createUsersAndCarsTables();

    void createDB();

    void dropTables();

}
