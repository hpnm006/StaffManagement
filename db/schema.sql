-- SQL Server Database Schema Setup
-- Run this script in SQL Server Management Studio (SSMS) or Azure Data Studio

IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'StaffManagementDB')
BEGIN
    CREATE DATABASE StaffManagementDB;
END
GO

USE StaffManagementDB;
GO

-- Create Role Table
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Role]') AND type in (N'U'))
BEGIN
    CREATE TABLE Role (
        Role_ID INT PRIMARY KEY,
        Role_Name NVARCHAR(50) NOT NULL UNIQUE
    );
END
GO

-- Create Staff Table
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Staff]') AND type in (N'U'))
BEGIN
    CREATE TABLE Staff (
        StaffID INT PRIMARY KEY IDENTITY(1,1),
        FullName NVARCHAR(100) NOT NULL,
        Gender BIT NOT NULL,
        PhoneNumber VARCHAR(20),
        Email VARCHAR(100) NOT NULL UNIQUE,
        Password VARCHAR(255) NOT NULL,
        Role_ID INT NOT NULL,
        IsActive BIT NOT NULL,
        FailedAttempts INT DEFAULT 0,
        LockoutTime DATETIME NULL,
        StaffCode VARCHAR(20),
        DateOfBirth DATE,
        Department NVARCHAR(100),
        Position NVARCHAR(100),
        Salary INT,
        HireDate DATE,
        CONSTRAINT FK_Staff_Role FOREIGN KEY (Role_ID) REFERENCES Role(Role_ID)
    );
END
GO
