# Employee and Department Management with JDBC

## Description

This Java project aims to develop an application using JDBC for the management of employees and departments within a company. The data will be stored in a relational database with a logical data model including the "Employee" and "Department" tables.

### Logical Data Model

- Table "Employee" (IdEmp, NomEmp, Salaire, Age, #RefDept)
- Table "Department" (IdDept, NomDept)

## Tasks to Accomplish

### 1. Database Creation

- Design the database schema with tables for employees and departments.
- Write the SQL script to create the tables.

### 2. Data Insertion

- Insert test data for employees and departments.

### 3. Displaying Employees of a Department

- Prompt the user to enter the name or identifier of a department.
- Display the list of employees working in that department.

### 4. Adding a New Employee

- Prompt the user to enter the information of a new employee.
- Add this employee to the database and update the list of employees in the relevant department.

### 5. Modifying Employee Information

- Prompt the user to enter the identifier of an employee.
- Allow the user to update the information of that employee.

### 6. Deleting an Employee

- Prompt the user to enter the identifier of an employee.
- Delete this employee from the database and update the list of employees in the relevant department.

### 7. Statistics

- Display the total number of employees in the company.
- Display the department with the highest number of employees.
- Display the number of employees per department.
- Calculate the total salary mass of the company or a department.

## Execution Instructions

1. Run CreateTables file to create the database and insert test data. (\EmployeJDBC\src\main\java\org\example\employejdbc\Models\CreateTables.java)
2. Edit datasource file and run your database.
3. Run the Java application.

---

