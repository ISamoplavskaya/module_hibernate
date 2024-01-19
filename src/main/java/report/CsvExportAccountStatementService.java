package report;

import com.opencsv.CSVWriter;
import entity.Account;
import exeptions.CsvExportException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExportAccountStatementService implements CsvExportReport<Account> {
    @Override
    public void exportReportToCsv(List<Account> accounts, String filePath) {

        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            String[] header = {"Firstname", "Lastname", "Account name", "Cart number", "Balance"};
            writer.writeNext(header);
            for (Account account : accounts) {
                String[] data = {
                        account.getUser().getFirstname(),
                        account.getUser().getLastname(),
                        account.getAccountName(),
                        account.getCardNumber(),
                        account.getBalance().toString(),
                };
                writer.writeNext(data);
            }
        } catch (IOException e) {
            throw new CsvExportException("Error when exporting to CSV", e);
        }
    }
}