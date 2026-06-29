package fu.swt301.sms.dao;

import fu.swt301.sms.entity.Role;
import fu.swt301.sms.entity.Staff;
import fu.swt301.sms.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object (DAO) for the Staff entity.
 * This class provides all the necessary methods to interact with the 'Staff' table in the database.
 * It handles all CRUD (Create, Read, Update, Delete) operations as well as other specific queries.
 */
public class StaffDAO {

    private static final Logger LOGGER = Logger.getLogger(StaffDAO.class.getName());

    /**
     * A private helper method to map a row from the ResultSet to a Staff object.
     * This avoids code duplication in methods that retrieve staff data.
     * It performs a JOIN with the Role table to populate the nested Role object.
     * @param rs The ResultSet cursor, positioned at the row to be mapped.
     * @return A fully populated Staff object.
     * @throws SQLException if a database access error occurs.
     */
    private Staff extractStaffFromResultSet(ResultSet rs) throws SQLException {
        Staff staff = new Staff();
        staff.setStaffID(rs.getInt("StaffID"));
        staff.setFullName(rs.getString("FullName"));
        staff.setGender(rs.getBoolean("Gender"));
        staff.setPhoneNumber(rs.getString("PhoneNumber"));
        staff.setEmail(rs.getString("Email"));
        staff.setPassword(rs.getString("Password"));
        staff.setIsActive(rs.getBoolean("IsActive"));
        staff.setFailedAttempts(rs.getInt("FailedAttempts"));
        staff.setLockoutTime(rs.getTimestamp("LockoutTime"));

        Role role = new Role();
        role.setRoleID(rs.getInt("Role_ID"));
        role.setRoleName(rs.getString("Role_Name"));
        staff.setRole(role);

        java.sql.Date dob = rs.getDate("DateOfBirth");
        if (dob != null) {
            staff.setDateOfBirth(dob.toLocalDate());
        }
        java.sql.Date hire = rs.getDate("HireDate");
        if (hire != null) {
            staff.setHireDate(hire.toLocalDate());
        }
        staff.setStaffCode(rs.getString("StaffCode"));
        staff.setDepartment(rs.getString("Department"));
        staff.setPosition(rs.getString("Position"));
        staff.setSalary(rs.getInt("Salary"));

        return staff;
    }

    /**
     * Checks if a given email already exists in the Staff table, excluding a specific staff member.
     * This is crucial for validation during updates to prevent a user from taking another user's email.
     * @param email The email to check for existence.
     * @param currentStaffId The ID of the staff member being updated. Use 0 when creating a new staff member.
     * @return true if the email exists for another staff member, false otherwise.
     * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the database driver is not found.
     */
    public boolean isEmailExists(String email, int currentStaffId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM Staff WHERE Email = ? AND StaffID != ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setInt(2, currentStaffId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    /**
     * Checks if a given full name already exists in the Staff table, excluding a specific staff member.
     * @param fullName The full name to check.
     * @param currentStaffId The ID of the staff member being updated. Use 0 for new staff.
     * @return true if the name exists for another staff member, false otherwise.
     * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the database driver is not found.
     */
    public boolean isFullNameExists(String fullName, int currentStaffId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM Staff WHERE FullName = ? AND StaffID != ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, fullName);
            ps.setInt(2, currentStaffId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    /**
     * Checks if a given phone number already exists in the Staff table, excluding a specific staff member.
     * @param phoneNumber The phone number to check.
     * @param currentStaffId The ID of the staff member being updated. Use 0 for new staff.
     * @return true if the phone number exists for another staff member, false otherwise.
     * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the database driver is not found.
     */
    public boolean isPhoneNumberExists(String phoneNumber, int currentStaffId) throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) FROM Staff WHERE PhoneNumber = ? AND StaffID != ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, phoneNumber);
            ps.setInt(2, currentStaffId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() && rs.getInt(1) > 0;
            }
        }
    }

    /**
     * Authenticates a user by checking their email and password against the database.
     * @param email The user's email.
     * @param password The user's plain text password.
     * @return A populated Staff object if authentication is successful, null otherwise.
     */
    public Staff checkLogin(String email, String password) {
        String sql = "SELECT s.*, r.Role_Name FROM Staff s JOIN Role r ON s.Role_ID = r.Role_ID WHERE s.Email = ? AND s.Password = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractStaffFromResultSet(rs);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a staff member by their email.
     * @param email The user's email.
     * @return A populated Staff object if found, null otherwise.
     */
    public Staff getStaffByEmail(String email) {
        String sql = "SELECT s.*, r.Role_Name FROM Staff s JOIN Role r ON s.Role_ID = r.Role_ID WHERE s.Email = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractStaffFromResultSet(rs);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Updates the login attempt details for a user.
     * @param staffId The ID of the staff member.
     * @param attempts The new failed attempts count.
     * @param lockoutTime The lockout time (or null if not locked).
     */
    public void updateLoginAttempts(int staffId, int attempts, java.sql.Timestamp lockoutTime) {
        String sql = "UPDATE Staff SET FailedAttempts = ?, LockoutTime = ? WHERE StaffID = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, attempts);
            ps.setTimestamp(2, lockoutTime);
            ps.setInt(3, staffId);
            ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating login attempts", e);
        }
    }

    /**
     * Retrieves a list of staff members based on optional filter criteria.
     * @param name A string to search for in the full name (case-insensitive). Can be null or empty.
     * @param status The active status to filter by ("true" or "false"). Can be null or empty.
     * @return A list of Staff objects matching the criteria.
     */
    public List<Staff> getStaffByFilter(String name, String status) {
        List<Staff> staffList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT s.*, r.Role_Name FROM Staff s JOIN Role r ON s.Role_ID = r.Role_ID WHERE 1=1");
        if (name != null && !name.isEmpty()) {
            sql.append(" AND s.FullName LIKE ?");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND s.IsActive = ?");
        }
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (name != null && !name.isEmpty()) {
                ps.setString(paramIndex++, "%" + name + "%");
            }
            if (status != null && !status.isEmpty()) {
                ps.setBoolean(paramIndex, Boolean.parseBoolean(status));
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    staffList.add(extractStaffFromResultSet(rs));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting staff by filter", e);
        }
        return staffList;
    }

    /**
     * Inserts a new staff member into the database.
     * @param staff The Staff object containing the data to be inserted.
     */
    public void createStaff(Staff staff) {
        String sql = "INSERT INTO Staff (FullName, Gender, PhoneNumber, Email, Password, Role_ID, IsActive, FailedAttempts, LockoutTime, StaffCode, DateOfBirth, Department, Position, Salary, HireDate) VALUES (?, ?, ?, ?, ?, ?, ?, 0, NULL, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, staff.getFullName());
            ps.setBoolean(2, staff.isGender());
            ps.setString(3, staff.getPhoneNumber());
            ps.setString(4, staff.getEmail());
            ps.setString(5, staff.getPassword());
            ps.setInt(6, staff.getRole().getRoleID());
            ps.setBoolean(7, staff.isIsActive());
            ps.setString(8, staff.getStaffCode());
            ps.setDate(9, staff.getDateOfBirth() != null ? java.sql.Date.valueOf(staff.getDateOfBirth()) : null);
            ps.setString(10, staff.getDepartment());
            ps.setString(11, staff.getPosition());
            ps.setInt(12, staff.getSalary());
            ps.setDate(13, staff.getHireDate() != null ? java.sql.Date.valueOf(staff.getHireDate()) : null);
            ps.executeUpdate();
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    staff.setStaffID(generatedKeys.getInt(1));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Error creating staff", e);
        }
    }

    /**
     * Updates an existing staff member's information in the database.
     * The password is not updated via this method.
     * @param staff The Staff object containing the updated data. The StaffID must be set.
     */
    public void updateStaff(Staff staff) {
        String sql = "UPDATE Staff SET FullName = ?, Gender = ?, PhoneNumber = ?, Email = ?, Role_ID = ?, IsActive = ?, StaffCode = ?, DateOfBirth = ?, Department = ?, Position = ?, Salary = ?, HireDate = ? WHERE StaffID = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staff.getFullName());
            ps.setBoolean(2, staff.isGender());
            ps.setString(3, staff.getPhoneNumber());
            ps.setString(4, staff.getEmail());
            ps.setInt(5, staff.getRole().getRoleID());
            ps.setBoolean(6, staff.isIsActive());
            ps.setString(7, staff.getStaffCode());
            ps.setDate(8, staff.getDateOfBirth() != null ? java.sql.Date.valueOf(staff.getDateOfBirth()) : null);
            ps.setString(9, staff.getDepartment());
            ps.setString(10, staff.getPosition());
            ps.setInt(11, staff.getSalary());
            ps.setDate(12, staff.getHireDate() != null ? java.sql.Date.valueOf(staff.getHireDate()) : null);
            ps.setInt(13, staff.getStaffID());
            ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating staff", e);
        }
    }

    /**
     * Deletes a staff member from the database by their ID.
     * @param staffId The ID of the staff member to delete.
     */
    public void deleteStaff(int staffId) {
        String sql = "DELETE FROM Staff WHERE StaffID = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, staffId);
            ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Error deleting staff", e);
        }
    }

    /**
     * Retrieves a single staff member by their unique ID.
     * @param staffId The ID of the staff member to retrieve.
     * @return A populated Staff object if found, null otherwise.
     */
    public Staff getStaffById(int staffId) {
        String sql = "SELECT s.*, r.Role_Name FROM Staff s JOIN Role r ON s.Role_ID = r.Role_ID WHERE s.StaffID = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, staffId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractStaffFromResultSet(rs);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Staff getStaffByStaffCode(String staffCode) {
        String sql = "SELECT s.*, r.Role_Name FROM Staff s JOIN Role r ON s.Role_ID = r.Role_ID WHERE s.StaffCode = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staffCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractStaffFromResultSet(rs);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Staff> getAllStaff() {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT s.*, r.Role_Name FROM Staff s JOIN Role r ON s.Role_ID = r.Role_ID";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(extractStaffFromResultSet(rs));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Staff> getStaffByDepartment(String department) {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT s.*, r.Role_Name FROM Staff s JOIN Role r ON s.Role_ID = r.Role_ID WHERE s.Department = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, department);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractStaffFromResultSet(rs));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Staff> getStaffByRole(int roleId) {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT s.*, r.Role_Name FROM Staff s JOIN Role r ON s.Role_ID = r.Role_ID WHERE s.Role_ID = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractStaffFromResultSet(rs));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Staff> searchStaffByName(String name) {
        List<Staff> list = new ArrayList<>();
        String sql = "SELECT s.*, r.Role_Name FROM Staff s JOIN Role r ON s.Role_ID = r.Role_ID WHERE LOWER(s.FullName) LIKE ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + (name != null ? name.toLowerCase() : "") + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractStaffFromResultSet(rs));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Staff> searchStaffAdvanced(String name, String department, String position, Boolean isActive) {
        List<Staff> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT s.*, r.Role_Name FROM Staff s JOIN Role r ON s.Role_ID = r.Role_ID WHERE 1=1");
        if (name != null && !name.isEmpty()) {
            sql.append(" AND LOWER(s.FullName) LIKE ?");
        }
        if (department != null && !department.isEmpty()) {
            sql.append(" AND s.Department = ?");
        }
        if (position != null && !position.isEmpty()) {
            sql.append(" AND s.Position = ?");
        }
        if (isActive != null) {
            sql.append(" AND s.IsActive = ?");
        }
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (name != null && !name.isEmpty()) {
                ps.setString(idx++, "%" + name.toLowerCase() + "%");
            }
            if (department != null && !department.isEmpty()) {
                ps.setString(idx++, department);
            }
            if (position != null && !position.isEmpty()) {
                ps.setString(idx++, position);
            }
            if (isActive != null) {
                ps.setBoolean(idx, isActive);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(extractStaffFromResultSet(rs));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getStaffCountByFilter(String name, String status) {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Staff s WHERE 1=1");
        if (name != null && !name.isEmpty()) {
            sql.append(" AND s.FullName LIKE ?");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND s.IsActive = ?");
        }
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (name != null && !name.isEmpty()) {
                ps.setString(paramIndex++, "%" + name + "%");
            }
            if (status != null && !status.isEmpty()) {
                ps.setBoolean(paramIndex, Boolean.parseBoolean(status));
            }
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Staff> getStaffByFilterPaginated(String name, String status, int offset, int limit) {
        List<Staff> staffList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT s.*, r.Role_Name FROM Staff s JOIN Role r ON s.Role_ID = r.Role_ID WHERE 1=1");
        if (name != null && !name.isEmpty()) {
            sql.append(" AND s.FullName LIKE ?");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND s.IsActive = ?");
        }
        sql.append(" ORDER BY s.StaffID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (name != null && !name.isEmpty()) {
                ps.setString(paramIndex++, "%" + name + "%");
            }
            if (status != null && !status.isEmpty()) {
                ps.setBoolean(paramIndex++, Boolean.parseBoolean(status));
            }
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex, limit);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    staffList.add(extractStaffFromResultSet(rs));
                }
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return staffList;
    }

    public void updatePassword(int staffId, String newHashedPassword) {
        String sql = "UPDATE Staff SET Password = ? WHERE StaffID = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newHashedPassword);
            ps.setInt(2, staffId);
            ps.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.log(Level.SEVERE, "Error updating staff password", e);
        }
    }
}
