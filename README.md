# Standalone Clothing Store Application

## Overview

This repository contains the code for a standalone application developed as part of the final project for the JavaFX module. The application is a POS (Point of Sale) system designed to manage a clothing store's operations efficiently. It leverages JavaFX with Jfoenix, Hibernate, MySQL, and Maven to create a robust, scalable, and user-friendly system.

## Objectives

- **Understand Requirements**: Design a comprehensive plan for developing a standalone clothing store application, including system architecture, database design, and user interface design.
- **Gain Proficiency**: Develop skills in JavaFX, Hibernate, Jasper Reporting, and layered architecture.
- **Implement Functionality**: Process and validate data using Java, implementing core functionalities of the application.
- **Learn Concepts**: Understand architectures and design patterns relevant to the project.

## Project Architecture

The project follows a layered architecture, consisting of several components to ensure a systematic and organized development process.

## Interfaces

<div style="display: flex; justify-content: center; align-items: center;">
   <img src="" alt="Interface 01" width="1200" height="600">
</div>

---

<table>
  <tr>
    <td><img src="" alt="Interface 02" width="1200" height="400"></td>
    <td><img src="" alt="Interface 03" width="1200" height="400"></td>
  </tr>
  
  <tr>
    <td><img src="" alt="Interface 04" width="1200" height="400"></td>
    <td><img src="" alt="Interface 05" width="1200" height="400"></td>
  </tr>
  
  <tr>
    <td><img src="" alt="Interface 06" width="1200" height="400"></td>
    <td><img src="" alt="Interface 07" width="1200" height="400"></td>
  </tr>
  
</table>

---


## Project Requirements

### System Overview

The application consists of two interfaces:
- **User Interface**: For store employees to manage day-to-day operations.
- **Admin Interface**: For the store owner to oversee and manage the system.

### User Management

- **User Registration**: 
  - Two types of users: Default user (store employee) and Admin user (store owner/manager).
  - Account creation with email and password (8 characters or more, including numbers, letters, and symbols).
  - Password updates.
  - Only the admin user can register new users.
- **User Authentication**: 
  - Email and password verification during login.
  - OTP pin for password reset.
  - Redirect to appropriate dashboard based on user type.

### Product Management

- **Product Catalog**:
  - Categories: Ladies, Gents, and Kids.
  - Product details: ID, name, size, price, quantity, and image.
  - Adding new products with supplier details.
- **Inventory Management**:
  - Real-time stock updates.
  - Stock addition and update capabilities.
  - Handling order returns.

### Supplier and Employee Management

- **Supplier Management**:
  - Add, update, and remove suppliers.
  - Auto-generated supplier ID.
- **Employee Management**:
  - Add, update, and remove employees.
  - Auto-generated employee ID.

### Order Management

- **Order Placement**:
  - Capture order details including ID, items, total cost, payment type, and employee details.
  - Auto-generated order ID.
  - Email receipts to customers.
- **Order Details**:
  - View past order details.

### Reports

- **Generate Reports**:
  - Receipts for orders.
  - Inventory, employee, and supplier reports.
  - Sales reports (daily, monthly, annual) for admin users.
  - Display reports using line and pie charts.
  - Generated using Jasper Reports.

## Contact
For any inquiries or feedback, please contact:

Email: ravinduheshan99@gmail.com

## Authors
- [@ravinduheshan99](https://github.com/ravinduheshan99)
