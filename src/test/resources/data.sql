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

-- Extra Pagination Test Data (Password is '123')
INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
VALUES ('Nguyen Van D', true, '0900000001', 'nguyenvand@company.com', '123', 2, true, 0, NULL, 'STF005', '1996-01-01', 'IT', 'Developer', 15000000.0, '2023-05-01');

INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
VALUES ('Tran Thi E', false, '0900000002', 'tranthie@company.com', '123', 2, true, 0, NULL, 'STF006', '1997-02-02', 'HR', 'Recruiter', 11000000.0, '2023-06-01');

INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
VALUES ('Le Van F', true, '0900000003', 'levanf@company.com', '123', 2, true, 0, NULL, 'STF007', '1998-03-03', 'Sales', 'Representative', 9000000.0, '2023-07-01');

INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
VALUES ('Pham Thi G', false, '0900000004', 'phamthig@company.com', '123', 2, true, 0, NULL, 'STF008', '1999-04-04', 'Marketing', 'Specialist', 13000000.0, '2023-08-01');

INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
VALUES ('Hoang Van H', true, '0900000005', 'hoangvanh@company.com', '123', 2, true, 0, NULL, 'STF009', '1990-05-05', 'IT', 'Manager', 25000000.0, '2021-01-01');

INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
VALUES ('Vu Thi I', false, '0900000006', 'vuthii@company.com', '123', 2, true, 0, NULL, 'STF010', '1991-06-06', 'Sales', 'Manager', 20000000.0, '2021-06-01');

INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
VALUES ('Phan Van J', true, '0900000007', 'phanvanj@company.com', '123', 2, true, 0, NULL, 'STF011', '1992-07-07', 'Finance', 'Accountant', 14000000.0, '2022-01-01');

INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
VALUES ('Do Thi K', false, '0900000008', 'dothik@company.com', '123', 2, true, 0, NULL, 'STF012', '1993-08-08', 'Finance', 'Analyst', 16000000.0, '2022-08-01');

INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
VALUES ('Bui Van L', true, '0900000009', 'buivanl@company.com', '123', 2, true, 0, NULL, 'STF013', '1994-09-09', 'IT', 'Support', 10000000.0, '2023-09-01');

INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate)
VALUES ('Dang Thi M', false, '0900000010', 'dangthim@company.com', '123', 2, true, 0, NULL, 'STF014', '1995-10-10', 'IT', 'Tester', 11000000.0, '2023-10-01');
