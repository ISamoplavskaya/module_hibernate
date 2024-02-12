package dao;

import entity.Account;
import entity.Operation;

import java.time.LocalDateTime;
import java.util.List;

public interface AccountDao extends GenericDao<Account> {
    void createOperationForAccount(long accountID, Operation operation);

    void deleteOperationByID(long accountID, long operation);

    List<Operation> getOperationsByAccount(long accountID);

    List<Operation> getOperationInPeriodByAccount(long accountID, LocalDateTime startDate, LocalDateTime endDate);

}
