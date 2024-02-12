package dao;

import entity.Account;
import entity.Operation;
import entity.User;

import java.util.List;

public interface UserDao extends GenericDao<User> {
    void createAccountForUser(long userID, Account account);

    List<Account> getAccountsByUser(long userID);

    List<Operation> getOperationsByUser(long userID);


}
