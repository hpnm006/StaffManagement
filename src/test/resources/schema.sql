CREATE TABLE Role (
    Role_ID INT PRIMARY KEY,
    Role_Name VARCHAR(50)
);

CREATE TABLE Staff (
    StaffID INT AUTO_INCREMENT PRIMARY KEY,
    FullName VARCHAR(100),
    Gender BIT,
    PhoneNumber VARCHAR(20),
    Email VARCHAR(100),
    Password VARCHAR(100),
    Role_ID INT,
    IsActive BIT,
    FailedAttempts INT DEFAULT 0,
    LockoutTime TIMESTAMP NULL,
    StaffCode VARCHAR(20),
    DateOfBirth DATE,
    Department VARCHAR(100),
    Position VARCHAR(100),
    Salary DOUBLE,
    HireDate DATE,
    FOREIGN KEY (Role_ID) REFERENCES Role(Role_ID)
);
