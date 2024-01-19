package report;

import com.opencsv.CSVWriter;
import entity.Operation;
import exeptions.CsvExportException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExportOperationStatementService implements CsvExportReport<Operation> {
    @Override
    public void exportReportToCsv(List<Operation> operations, String filePath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            String[] header = {"Account", "Name", "Category", "Amount", "Date"};
            writer.writeNext(header);
            for (Operation operation : operations) {
                String[] data = {
                        operation.getAccount().getAccountName(),
                        operation.getName(),
                        operation.getCategory().toString(),
                        operation.getAmount().toString(),
                        operation.getOperationDate().toString()
                };
                writer.writeNext(data);
            }
        } catch (IOException e) {
            throw new CsvExportException("Error when exporting to CSV", e);
        }
    }
}
