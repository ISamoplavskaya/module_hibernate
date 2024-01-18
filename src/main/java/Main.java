import entity.Account;
import entity.Category;
import entity.Operation;
import entity.User;
import service.AccountService;
import service.OperationService;
import service.UserService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserService();
        AccountService accountService = new AccountService();
        OperationService operationService = new OperationService();

        User user1 = User.builder()
                .firstname("Oleg")
                .lastname("SHevchuk")
                .email("oShevchuk@gmail.com")
                .build();
        //   userService.savePerson(user1);

        Account account1 = Account.builder()
                .accountName("Privat001")
                .cardNumber("5432145612381245")
                .balance(BigDecimal.valueOf(300.01))
                .build();
        //accountService.saveAccount(account1);

        Operation operation1 = Operation.builder()
                .name("Rent")
                .operationDate(LocalDateTime.of(2023, 11, 24, 15, 44))
                .amount(BigDecimal.valueOf(500.00))
                .category(Category.EXPENSE)
                .build();
        //  operationService.saveOperation(operation1);
        // accountService.addOperationToAccount(5L, operation1);
        userService.getAllAccountsByUser(1L).forEach(System.out::println);
        userService.getAllOperationsByUser(1L).forEach(System.out::println);
        userService.getAccountsSortedByBalance(1L).forEach(System.out::println);
        System.out.println(userService.getLargestOperationByUser(1L));

        accountService.getAllOperationsByAccount(4L).forEach(System.out::println);
        accountService.sortOperationsByAmount(4L).forEach(System.out::println);
        accountService.sortOperationsByCategory(4L,Category.INCOME).forEach(System.out::println);
        System.out.println(accountService.getLargestOperationByAccount(4l));
        LocalDateTime startDate = LocalDateTime.of(2023, 12, 01, 00, 00);
        LocalDateTime endDate = LocalDateTime.now();
        accountService.findOperationsInPeriod(4L,startDate,endDate).forEach(System.out::println);




    }
}
