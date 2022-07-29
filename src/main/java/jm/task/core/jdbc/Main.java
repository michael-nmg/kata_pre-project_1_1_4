package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();

        userService.saveUser("Ivan", "Petrov", (byte) 23);
        userService.saveUser("Andrey", "Ivanov", (byte) 25);
        userService.saveUser("Sergey", "Tarasov", (byte) 27);
        userService.saveUser("Anton", "Sidorov", (byte) 29);

        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();

        Util.connectionClose();
    }
}