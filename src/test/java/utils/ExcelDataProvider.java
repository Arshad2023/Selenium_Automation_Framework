package utils;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelDataProvider {
    /**
     * DataProvider that reads sheet name from the test class simple name by default.
     * You can call with a test method located in a class named same as sheet
     * or pass system property 'sheetName' to override.
     */
    @DataProvider(name = "getDataFromExcel", parallel = true)
    public Object[][] getDataFromExcel(Method method) {
        String sheetName = System.getProperty("sheetName");
        if (sheetName == null || sheetName.isEmpty()) {
            // fallback to class simple name
            sheetName = method.getDeclaringClass().getSimpleName();
        }

        List<Map<String, String>> allRows = ExcelUtils.getAllRows(sheetName);
        List<Map<String, String>> filtered = new ArrayList<>();
        for (Map<String, String> row : allRows) {
            if ("Y".equalsIgnoreCase(row.getOrDefault("RunMode", ""))) {
                filtered.add(row);
            }
        }
        Object[][] data = new Object[filtered.size()][1];
        for (int i = 0; i < filtered.size(); i++) {
            data[i][0] = filtered.get(i);
        }
        return data;
    }
}
