import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    static List<Employee> employeeList = new ArrayList<>();
    public static void main(String[] args) {
       extractData();
       loadDataIntoTable();

    }
    static void extractData() {
        String filePath = "C:\\Users\\ASUS\\OneDrive\\Desktop\\Test\\Question3\\employees.jason";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(filePath);
            employeeList = objectMapper.readValue(file, new TypeReference<List<Employee>>() {});
            for (int i = 0; i < employeeList.size(); i++) {
                System.out.println("id: " + employeeList.get(i).getId());
                System.out.println("name: " + employeeList.get(i).getName());
                System.out.println("department: " + employeeList.get(i).getDepartment());
                System.out.println("salary: " + employeeList.get(i).getSalary());
                System.out.println("join_date: " + employeeList.get(i).getJoin_date());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static Date convertData (Employee employee) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try {
            date = dateFormat.parse(employee.getJoin_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    static void loadDataIntoTable () {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/new_servlet", "root", "doanhuy29");
            String sql = "insert into employees (id, name, department, salary, join_date) values (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(sql);

            for (Employee employee : employeeList) {
                Date date = new Date();
                date = convertData(employee);
                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                statement.setInt(1, employee.getId());
                statement.setString(2, employee.getName());
                statement.setString(3, employee.getDepartment());
                statement.setInt(4, employee.getSalary());
                statement.setDate(5, sqlDate);
                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}