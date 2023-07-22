package peaksoft.dao.daoImpl;

import peaksoft.config.Config;
import peaksoft.dao.JobDao;
import peaksoft.model.Employee;
import peaksoft.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JobDaoImpl implements JobDao {
    public void createJobTable() {
        String sql = "create table jobs(" +
                "id serial primary key," +
                "position varchar," +
                "profession varchar," +
                "description varchar," +
                "experience int);";

        try (Connection connection = Config.getConnection();
             Statement statement = connection.createStatement();) {
            statement.executeUpdate(sql);
            System.out.println("-- Table job has created --");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addJob(Job job) {
        String sql = "insert into jobs(" +
                "position," +
                "profession," +
                "description," +
                "experience)" +
                "values(" +
                "?,?,?,?);";
        try (Connection connection = Config.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);) {
            preparedStatement.setString(1, job.getPosition());
            preparedStatement.setString(2, job.getProfession());
            preparedStatement.setString(3, job.getDescription());
            preparedStatement.setInt(4, job.getExperience());
            preparedStatement.executeUpdate();
            System.out.println("New job successfully saved");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Job getJobById(Long jobId) {
        Job job = new Job();
        String sql = "select * from jobs where id=?;";
        try (Connection conn = Config.getConnection();
             PreparedStatement pre = conn.prepareStatement(sql)) {
            pre.setLong(1, jobId);
            ResultSet rs = pre.executeQuery();
            if (rs.next()) {
                job.setId(rs.getLong("id"));
                job.setPosition(rs.getString("position"));
                job.setProfession(rs.getString("profession"));
                job.setDescription(rs.getString("description"));
                job.setExperience(rs.getInt("experience"));
                System.out.println("exectly");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return job;
    }


    public List<Job> sortByExperience(String ascOrDesc) {
        List<Job> jobs = new ArrayList<>();

        if (ascOrDesc.equalsIgnoreCase("asc")) {
            String sql = "select * from jobs order by experience asc";
            try (Connection connection = Config.getConnection();
                 Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(sql);
                while (rs.next()) {
                    jobs.add(new Job(
                            rs.getLong("id"),
                            rs.getString("position"),
                            rs.getString("profession"),
                            rs.getString("description"),
                            rs.getInt("experience")
                    ));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } else if (ascOrDesc.equalsIgnoreCase("desc")) {
            String desc = "select * from jobs order by experience desc";
            try (Connection connection = Config.getConnection();
                 Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(desc);
                while (rs.next()) {
                    jobs.add(new Job(
                            rs.getLong("id"),
                            rs.getString("position"),
                            rs.getString("profession"),
                            rs.getString("description"),
                            rs.getInt("experience")));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return jobs;
    }

    public Job getJobByEmployeeId(Long employeeId) {
        Job job = new Job();
        String sql = "select j.* from employees e join jobs j on e.job_id=j.id where e.id=?";
        try (Connection connection = Config.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet==null) {
                System.out.println("Не вышло у Акылай эже спроси");
            } else {
                while (resultSet.next()) {
//                    job=new Job(
//                            resultSet.getLong("id"),
//                            resultSet.getString("position"),
//                            resultSet.getString("profession"),
//                            resultSet.getString("description"),
//                            resultSet.getInt("experience")
//                            );
                    job.setId(resultSet.getLong("id"));
                    job.setPosition(resultSet.getString("position"));
                    job.setProfession(resultSet.getString("profession"));
                    job.setDescription(resultSet.getString("description"));
                    job.setExperience(resultSet.getInt("experience"));
                }
                return job;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return job;
    }

    public void deleteDescriptionColumn() {
        String sql = "alter table jobs drop column description";
        try (Connection connection = Config.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
            System.out.println("Column description successfully deleted");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
}
