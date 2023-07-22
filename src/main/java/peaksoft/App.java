package peaksoft;

import peaksoft.model.Employee;
import peaksoft.model.Job;
import peaksoft.service.EmployeeService;
import peaksoft.service.JobService;
import peaksoft.service.serviceImpl.EmployeeServiceImpl;
import peaksoft.service.serviceImpl.JobServiceImpl;

import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        EmployeeService employeeService = new EmployeeServiceImpl();
        JobService jobService = new JobServiceImpl();



       //


           /*
                     --  EMPLOYEE  --


           CREATE -> employeeService.createEmployee();
               ADD-> employeeService.addEmployee(new Employee("Aizada","Abdyrazakova",20,"aiza@gmail.com",1));
employeeService.addEmployee(new Employee("Hiro","Midoriya",25, "hiro@gmail",2));
            UPDATE-> employeeService.updateEmployee(2L,new Employee("HIRO","DEKU",18,"DEKU@gmail.com",2));
             CLEAN-> employeeService.cleanTable();
              DROP-> employeeService.dropTable();
           GET ALL-> employeeService.getAllEmployees().forEach(System.out::println);
           GET BY POSITION-> System.out.println(employeeService.getEmployeeByPosition("president"));
           GET BY ID ->System.out.println(employeeService.getEmployeeById(1L));
           GET BY EMAIL->System.out.println(employeeService.findByEmail("aiza@gmail.com"));

                       --  JOB  --

              CREATE->jobService.createJobTable();
                 ADD->jobService.addJob(new Job("Glavny disayner","Disainer","origin disains",5));
                      jobService.addJob(new Job("president","president","glavnye president",6));
           GET BY ID->System.out.println(jobService.getJobById(1L));
 DELETE DESCRIPTION ->jobService.deleteDescriptionColumn();
                SORT->System.out.println(jobService.sortByExperience("desc"));
                FIND JOB BY EMPLOYEE->System.out.println(jobService.getJobByEmployeeId(2L));

        */



    }
}
