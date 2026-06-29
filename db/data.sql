USE StaffManagementDB;
GO

-- Insert Default Roles
IF NOT EXISTS (SELECT * FROM Role WHERE Role_ID = 1)
BEGIN
    INSERT INTO Role (Role_ID, Role_Name) VALUES (1, 'Admin');
END
IF NOT EXISTS (SELECT * FROM Role WHERE Role_ID = 2)
BEGIN
    INSERT INTO Role (Role_ID, Role_Name) VALUES (2, 'Staff');
END

-- Insert Default Admin User (Password is 'admin123' hashed with BCrypt)
IF NOT EXISTS (SELECT * FROM Staff WHERE Email = 'admin@example.com')
BEGIN
    INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime)
    VALUES ('Admin User', 1, '0123456789', 'admin@example.com', '$2a$10$w3q.xXJq3/01B5y0l/rT5euT6t40Wv67g8x4uC/L4oGZ2eP6c6G/O', 1, 1, 0, NULL);
END

-- Insert Sample Staff Users (Passwords are '123' and 'hashed_pw_x')
IF NOT EXISTS (SELECT * FROM Staff WHERE Email = 'tranthib@company.com')
BEGIN
    INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
    VALUES ('Tran Thi B', 0, '0987654321', 'tranthib@company.com', 'hashed_pw_1', 2, 1, 0, NULL, 'STF002', '1995-05-15', 'HR', 'Recruiter', 12000000, '2023-01-10');
END

IF NOT EXISTS (SELECT * FROM Staff WHERE Email = 'levanc@company.com')
BEGIN
    INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
    VALUES ('Le Van C', 1, '0912345678', 'levanc@company.com', 'hashed_pw_2', 2, 1, 0, NULL, 'STF003', '1990-10-20', 'IT', 'Developer', 18000000, '2022-03-15');
END

IF NOT EXISTS (SELECT * FROM Staff WHERE Email = 'user1@company.com')
BEGIN
    INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
    VALUES ('User One', 1, '0909090909', 'user1@company.com', '123', 2, 1, 0, NULL, 'STF004', '1998-12-25', 'Sales', 'Representative', 10000000, '2024-02-01');
END
