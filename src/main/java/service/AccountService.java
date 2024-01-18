package service;

import dao.AccountDao;
import dao.impl.AccountDaoImpl;
import entity.Account;
import entity.Category;
import entity.Operation;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AccountService {
    private final AccountDao accountDao = new AccountDaoImpl(Account.class);

    public AccountService() {
    }

    public Account findAccountByID(long id) {
        return accountDao.findByID(id)
                .orElseThrow(() -> new RuntimeException("Account with " + id + " not found"));
    }

    public List<Account> findAllAccount() {
        return accountDao.findAll();
    }

    public void saveAccount(Account account) {
        accountDao.save(account);
    }

    public void updateAccount(Account account) {
        accountDao.update(account);
    }

    public void deleteAccount(Account account) {
        accountDao.delete(account);
    }

    public void addOperationToAccount(long accountID, Operation operation) {
        accountDao.createOperationForAccount(accountID, operation);
    }

    public List<Operation> getAllOperationsByAccount(long accountID) {
        return accountDao.getOperationsByAccount(accountID);
    }

    public List<Operation> sortOperationsByAmount(long accountID) {
        List<Operation> operations = getAllOperationsByAccount(accountID);
        return operations.stream()
                .sorted(Comparator.comparing(Operation::getAmount))
                .collect(Collectors.toList());
    }

    public List<Operation> sortOperationsByCategory(long accountID, Category typeCategory) {
        List<Operation> operations = getAllOperationsByAccount(accountID);
        return operations.stream()
                .filter(operation -> operation.getCategory().compareTo(typeCategory) == 0)
                .sorted(Comparator.comparing(Operation::getAmount))
                .collect(Collectors.toList());
    }

    public Optional<Operation> getLargestOperationByAccount(long accountID) {
        List<Operation> operations = getAllOperationsByAccount(accountID);
        return operations.stream()
                .max(Comparator.comparing(Operation::getAmount));
    }

    public List<Operation> findOperationsInPeriod(long accountID, LocalDateTime startDate, LocalDateTime endDate) {
        return accountDao.getOperationInPeriodByAccount(accountID, startDate, endDate);
    }
}
