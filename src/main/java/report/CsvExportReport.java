package report;

import java.util.List;

public interface CsvExportReport<T> {
    void exportReportToCsv(List<T> data, String filePath);

}

