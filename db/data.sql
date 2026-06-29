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

-- Extra Pagination Test Data (Password is '123')
IF NOT EXISTS (SELECT * FROM Staff WHERE Email = 'nguyenvand@company.com')
BEGIN
    INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
    VALUES ('Nguyen Van D', 1, '0900000001', 'nguyenvand@company.com', '123', 2, 1, 0, NULL, 'STF005', '1996-01-01', 'IT', 'Developer', 15000000, '2023-05-01');
END

IF NOT EXISTS (SELECT * FROM Staff WHERE Email = 'tranthie@company.com')
BEGIN
    INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
    VALUES ('Tran Thi E', 0, '0900000002', 'tranthie@company.com', '123', 2, 1, 0, NULL, 'STF006', '1997-02-02', 'HR', 'Recruiter', 11000000, '2023-06-01');
END

IF NOT EXISTS (SELECT * FROM Staff WHERE Email = 'levanf@company.com')
BEGIN
    INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
    VALUES ('Le Van F', 1, '0900000003', 'levanf@company.com', '123', 2, 1, 0, NULL, 'STF007', '1998-03-03', 'Sales', 'Representative', 9000000, '2023-07-01');
END

IF NOT EXISTS (SELECT * FROM Staff WHERE Email = 'phamthig@company.com')
BEGIN
    INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
    VALUES ('Pham Thi G', 0, '0900000004', 'phamthig@company.com', '123', 2, 1, 0, NULL, 'STF008', '1999-04-04', 'Marketing', 'Specialist', 13000000, '2023-08-01');
END

IF NOT EXISTS (SELECT * FROM Staff WHERE Email = 'hoangvanh@company.com')
BEGIN
    INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
    VALUES ('Hoang Van H', 1, '0900000005', 'hoangvanh@company.com', '123', 2, 1, 0, NULL, 'STF009', '1990-05-05', 'IT', 'Manager', 25000000, '2021-01-01');
END

IF NOT EXISTS (SELECT * FROM Staff WHERE Email = 'vuthii@company.com')
BEGIN
    INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
    VALUES ('Vu Thi I', 0, '0900000006', 'vuthii@company.com', '123', 2, 1, 0, NULL, 'STF010', '1991-06-06', 'Sales', 'Manager', 20000000, '2021-06-01');
END

IF NOT EXISTS (SELECT * FROM Staff WHERE Email = 'phanvanj@company.com')
BEGIN
    INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
    VALUES ('Phan Van J', 1, '0900000007', 'phanvanj@company.com', '123', 2, 1, 0, NULL, 'STF011', '1992-07-07', 'Finance', 'Accountant', 14000000, '2022-01-01');
END

IF NOT EXISTS (SELECT * FROM Staff WHERE Email = 'dothik@company.com')
BEGIN
    INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
    VALUES ('Do Thi K', 0, '0900000008', 'dothik@company.com', '123', 2, 1, 0, NULL, 'STF012', '1993-08-08', 'Finance', 'Analyst', 16000000, '2022-08-01');
END

IF NOT EXISTS (SELECT * FROM Staff WHERE Email = 'buivanl@company.com')
BEGIN
    INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
    VALUES ('Bui Van L', 1, '0900000009', 'buivanl@company.com', '123', 2, 1, 0, NULL, 'STF013', '1994-09-09', 'IT', 'Support', 10000000, '2023-09-01');
END

IF NOT EXISTS (SELECT * FROM Staff WHERE Email = 'dangthim@company.com')
BEGIN
    INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
    VALUES ('Dang Thi M', 0, '0900000010', 'dangthim@company.com', '123', 2, 1, 0, NULL, 'STF014', '1995-10-10', 'IT', 'Tester', 11000000, '2023-10-01');
END
