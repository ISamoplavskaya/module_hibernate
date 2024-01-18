package service;

import dao.UserDao;
import dao.impl.UserDaoImpl;
import entity.Account;
import entity.Operation;
import entity.User;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    private final UserDao userDao = new UserDaoImpl(User.class);

    public UserService() {
    }

    public User findUserByID(long id) {
        return userDao.findByID(id)
                .orElseThrow(() -> new RuntimeException("User with " + id + " not found"));
    }

    public List<User> findAllUser() {
        return userDao.findAll();
    }

    public void saveUser(User user) {
        userDao.save(user);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public void deleteUser(User user) {
        userDao.delete((user));
    }

    public void addAccountToUser(long userID, Account account) {
        userDao.createAccountForUser(userID, account);
    }

    public List<Account> getAllAccountsByUser(long userID) {
        return userDao.getAccountsByUser(userID);
    }

    public List<Operation> getAllOperationsByUser(long userID) {
        return userDao.getOperationsByUser(userID);
    }

    public Optional<Operation> getLargestOperationByUser(long userID) {
        List<Operation> userOperations = getAllOperationsByUser(userID);
        return userOperations.stream()
                .max(Comparator.comparing(Operation::getAmount));
    }

    public List<Account> getAccountsSortedByBalance(long userID) {
        List<Account> accounts = getAllAccountsByUser(userID);
        return accounts.stream()
                    .sorted(Comparator.comparing(Account::getBalance))
                    .collect(Collectors.toList());
    }
}


