
import entity.Account;
import entity.Category;
import entity.Operation;
import entity.User;
import report.CsvExportAccountStatementService;
import report.CsvExportOperationStatementService;
import report.CsvExportReport;
import service.AccountService;
import service.OperationService;
import service.UserService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        OperationService operationService = new OperationService();
        AccountService accountService = new AccountService();
        CsvExportReport<Account> csvExportAccountStatement = new CsvExportAccountStatementService();
        CsvExportReport<Operation> csvExportOperationStatement = new CsvExportOperationStatementService();

        User user = User.builder()
                .firstname("111")
                .lastname("123")
                .email("111@gmail.com")
                .build();
     userService.saveUser(user);
        User user1 = userService.findUserByID(1L);
        System.out.println("User with ID=1: " + user1);
        user1.setFirstname("Alex");
//        userService.updateUser(user1);
//        userService.deleteUser(userService.findUserByID(4L));

        Account account = Account.builder()
                .accountName("Privat002")
                .cardNumber("5432145612481245")
                .balance(BigDecimal.valueOf(3000.00))
                .build();
//        accountService.saveAccount(account);
//        userService.addAccountToUser(1L,account);
        Account account1 = accountService.findAccountByID(4L);
        System.out.println("Account with ID=4: " + account1);
        account1.setBalance(BigDecimal.valueOf(1000.00));
        //  accountService.updateAccount(account1);
        // accountService.deleteAccount(accountService.findAccountByID(8L));
        accountService.findAllAccount().forEach(System.out::println);
        // accountService.deleteOperation(4L,9L);

        Operation operation1 = Operation.builder()
                .name("Rent")
                .operationDate(LocalDateTime.of(2023, 11, 10, 15, 44))
                .amount(BigDecimal.valueOf(100.00))
                .category(Category.EXPENSE)
                .build();
        operationService.saveOperation(operation1);
        // accountService.addOperationToAccount(3L, operation1);
        Operation operationID20 = operationService.findOperationByID(20L);
        System.out.println("Operation with ID=20 " + operationID20);
        operationID20.setName("Cat");
        //  operationService.updateOperation(operationID20);
        operationService.findAllOperation().forEach(System.out::println);
        // accountService.deleteOperation(3L,23);

        System.out.println("Largest Operation by user " + userService.getLargestOperationByUser(1L));
        System.out.println("Largest Operation by account " + accountService.getLargestOperationByAccount(4L));

        List<Account> allAccountsByUser = userService.getAllAccountsByUser(1L);
        List<Operation> allOperationsByUser = userService.getAllOperationsByUser(1L);
        List<Account> accountsSortedByBalance = userService.getAccountsSortedByBalance(1L);
        List<Operation> allOperationsByAccount = accountService.getAllOperationsByAccount(4L);
        List<Operation> sortedOperationsByAmount = accountService.sortOperationsByAmount(4L);
        List<Operation> sortedOperationsByCategory = accountService.sortOperationsByCategory(4L, Category.INCOME);
        LocalDateTime startDate = LocalDateTime.of(2023, 12, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.now();
        List<Operation> operationsInPeriod = accountService.findOperationsInPeriod(4L, startDate, endDate);

        String fileDir = "D:\\A-Level\\Home work\\module_hibernate\\reports\\";
        csvExportAccountStatement.exportReportToCsv(allAccountsByUser, fileDir + "allAccountsByUser.csv");
        csvExportAccountStatement.exportReportToCsv(accountsSortedByBalance, fileDir + "accountsSortedByBalance.csv");
        csvExportOperationStatement.exportReportToCsv(allOperationsByUser, fileDir + "allOperationsByUser.csv");
        csvExportOperationStatement.exportReportToCsv(allOperationsByAccount, fileDir + "allOperationsByAccount.csv");
        csvExportOperationStatement.exportReportToCsv(sortedOperationsByAmount, fileDir + "sortedOperationsByAmount.csv");
        csvExportOperationStatement.exportReportToCsv(sortedOperationsByCategory, fileDir + "sortedOperationsByCategory.csv");
        csvExportOperationStatement.exportReportToCsv(operationsInPeriod, fileDir + "operationsInPeriod.csv");


    }
}
