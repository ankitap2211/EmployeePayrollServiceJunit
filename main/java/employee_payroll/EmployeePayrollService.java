package org.employee_payroll;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeePayrollService {
    public List<EmployeePayrollData> employeePayrollDataList;

    public enum IOService {CONSOLE_IO, FILE_IO, DB_IO, REST_IO}

    public EmployeePayrollService(List<EmployeePayrollData> employeePayrollData) {}

    public EmployeePayrollService() {
        employeePayrollDataList = new ArrayList<>();
    }

    private void readEmployeePayrollData(Scanner consoleInputReader) {
        System.out.print("Enter employee ID: ");
        int id = consoleInputReader.nextInt();
        System.out.print("Enter employee name: ");
        String name = consoleInputReader.next();
        System.out.print("Enter employee salary: ");
        double salary = consoleInputReader.nextDouble();
        EmployeePayrollData data = new EmployeePayrollData(id, name, salary);
        employeePayrollDataList.add(data);
    }

    public void writeEmployeePayrollData(IOService ioService) {
        if (ioService.equals(IOService.CONSOLE_IO))
            System.out.println("Writing Employee Payroll to console " + employeePayrollDataList);
        else if (ioService.equals(IOService.FILE_IO))
            new EmployeePayrollFileService().writeData(employeePayrollDataList);
    }

    public long countEntries(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
            return new EmployeePayrollFileService().countEntries();
        return 0;
    }

    public void printData(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
            new EmployeePayrollFileService().printData();
    }

    public long readEmployeePayrollData(IOService ioService) {
        if (ioService.equals(IOService.FILE_IO))
            this.employeePayrollDataList = new EmployeePayrollFileService().readData();
        return employeePayrollDataList.size();
    }

    public static void main(String[] args) {
        EmployeePayrollService employeePayrollService = new EmployeePayrollService();
        Scanner consoleInputReader = new Scanner(System.in);
        employeePayrollService.readEmployeePayrollData(consoleInputReader);
        employeePayrollService.writeEmployeePayrollData(IOService.FILE_IO);
    }
}
