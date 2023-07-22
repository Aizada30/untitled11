package peaksoft.dao.daoImpl;

import peaksoft.config.Config;
import peaksoft.dao.EmployeeDao;
import peaksoft.model.Employee;
import peaksoft.model.Job;

import java.sql.*;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {


    public void createEmployee() {

        String sql = "create table employees(id serial primary key," +
                "first_name varchar," +
                "last_name varchar," +
                "age int," +
                "email varchar," +
                "job_id int references jobs(id));";
        try {
            Connection connection = Config.getConnection();
            Statement statement = connection.createStatement();
            statement.executeUpdate(sql);
            System.out.println("-- Table Employee has created --");
            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addEmployee(Employee employee) {
        String sql = "insert into employees (first_name,last_name,age,email,job_id)values (?,?,?,?,?);";
        try {
            Connection connection = Config.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setLong(5, employee.getJobId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("New employee successfully saved");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropTable() {
        String drop = "drop table employees;";
        try (Connection conn = Config.getConnection();
             Statement sta = conn.createStatement()) {
            sta.executeUpdate(drop);
            System.out.println("Table employee successfully deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void cleanTable() {
        String clean = "delete from employees ";
        try (Connection conn = Config.getConnection();
             Statement sta = conn.createStatement()) {
            sta.executeUpdate(clean);
            System.out.println("Table employee successfully cleaned");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateEmployee(Long id, Employee employee) {
        String sql = "update employees set first_name=?,last_name=?,age=?,email=?,job_id=? where id=?";
        try {
            Connection connection = Config.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setInt(5, employee.getJobId());
            preparedStatement.setLong(6, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("-- Employee has updated --");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "select * from employees";
        try (Connection connection = Config.getConnection();
             Statement statement = connection.createStatement();) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                employees.add(new Employee(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getInt("job_id")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employees;
    }

    public Employee findByEmail(String email) {
        String sql = "select * from employees where email=?";
        Employee employee = new Employee();
        try(Connection connection = Config.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setString(1,email);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                employee.setId(rs.getLong("id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setAge(rs.getInt("age"));
                employee.setEmail(rs.getString("email"));
                employee.setJobId(rs.getInt("job_id"));
            }
        }catch (SQLException e ){
            System.out.println(e.getMessage());
        }
        return employee;
    }

    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        Job job = new Job();
        Employee employee = new Employee();
        Map<Employee, Job> employeeJobMap = new HashMap<>();
        String sql = "select j.* , e.* from employees e join jobs j on e.job_id=j.id where e.id=?";
        try (Connection connection = Config.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, employeeId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                employee.setId(rs.getLong("id"));
                employee.setFirstName(rs.getString("first_name"));
                employee.setLastName(rs.getString("last_name"));
                employee.setAge(rs.getInt("age"));
                employee.setEmail(rs.getString("email"));
                employee.setJobId(rs.getInt("job_id"));
                job.setId(rs.getLong("id"));
                job.setPosition(rs.getString("position"));
                job.setProfession(rs.getString("profession"));
                job.setDescription(rs.getString("description"));
                job.setExperience(rs.getInt("experience"));
            }
            employeeJobMap.put(employee, job);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employeeJobMap;
    }

    public List<Employee> getEmployeeByPosition(String position) {
        List<Employee>employees=new ArrayList<>();
        String getEmployee = "select * from employees e join jobs j on e.job_id=j.id where j.position=?";
        try(Connection connection = Config.getConnection();
        PreparedStatement pre = connection.prepareStatement(getEmployee)){
            pre.setString(1,position);
            ResultSet resultSet = pre.executeQuery();
           while(resultSet.next()){
               employees.add(new Employee(
                       resultSet.getLong("id"),
                       resultSet.getString("first_name"),
                       resultSet.getString("last_name"),
                       resultSet.getInt("age"),
                       resultSet.getString("email"),
                       resultSet.getInt("job_id"))
               );
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return employees;
    }
}

