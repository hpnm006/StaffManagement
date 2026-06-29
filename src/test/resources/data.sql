INSERT INTO Role (Role_ID, Role_Name) VALUES (1, 'Admin');
INSERT INTO Role (Role_ID, Role_Name) VALUES (2, 'Staff');

-- Default admin user (password 'admin123' hashed with BCrypt)
INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime)
VALUES ('Admin User', true, '0123456789', 'admin@example.com', '$2a$10$w3q.xXJq3/01B5y0l/rT5euT6t40Wv67g8x4uC/L4oGZ2eP6c6G/O', 1, true, 0, NULL);

-- Legacy/sample user accounts
INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
VALUES ('Tran Thi B', false, '0987654321', 'tranthib@company.com', 'hashed_pw_1', 2, true, 0, NULL, 'STF002', '1995-05-15', 'HR', 'Recruiter', 12000000.0, '2023-01-10');

INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
VALUES ('Le Van C', true, '0912345678', 'levanc@company.com', 'hashed_pw_2', 2, true, 0, NULL, 'STF003', '1990-10-20', 'IT', 'Developer', 18000000.0, '2022-03-15');

INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
VALUES ('User One', true, '0909090909', 'user1@company.com', '123', 2, true, 0, NULL, 'STF004', '1998-12-25', 'Sales', 'Representative', 10000000.0, '2024-02-01');
