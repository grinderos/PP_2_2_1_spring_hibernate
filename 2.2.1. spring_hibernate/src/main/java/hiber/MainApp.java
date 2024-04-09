package hiber;

import hiber.config.AppConfig;
import hiber.model.Car;
import hiber.model.User;
import hiber.service.UserService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws SQLException {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);

        /*
        Создание БД "users", если она не создана.
         */
        userService.createDB();
        /*
        Создание таблиц, если их нет в БД, и очистка, если они есть.
         */
        userService.createUsersAndCarsTables();
        userService.truncateAllTables();

        /*
        Основная логика по заданию.
        --------------------------------------
         */
        User user1 = new User("James", "Bond", "agent007@mi6.com");
        user1.setCar(new Car("Aston Martin DB", 5));
        userService.add(user1);

        User user2 = new User("Ethan", "Hunt", "mission-imposible@wagner.com");
        user2.setCar(new Car("Land-Rover Defender", 110));
        userService.add(user2);

        User user3 = new User("Tony", "Stark", "T.Stark@starkindustries.st");
        user3.setCar(new Car("Audi R", 8));
        userService.add(user3);

        User user4 = new User("Meglin", "Rodion", "no data");
        user4.setCar(new Car("Mercedes-Benz E-Class W", 123));
        userService.add(user4);

        List<User> users = userService.listUsers();
        for (User user : users) {
            System.out.println(user);
            System.out.println();
        }

        System.out.println(userService.getUserWithCurrentCar("Audi R", 8));
        System.out.println("\n\n");

        /*
        --------------------------------------
         */

        /*
        Очистка таблиц "user_2_2_1" и "cars_2_2_1"
         */
//        userService.truncateAllTables();

        /*
        Удаление таблиц из БД.
         */
//        userService.dropTables();

        context.close();
    }
}
