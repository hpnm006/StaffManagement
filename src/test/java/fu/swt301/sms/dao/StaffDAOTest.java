package fu.swt301.sms.dao;

import fu.swt301.sms.entity.Role;
import fu.swt301.sms.entity.Staff;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StaffDAOTest {

    private StaffDAO staffDAO;
    private RoleDAO roleDAO;

    @BeforeAll
    public static void setUpClass() {
        System.out.println("H2 Test DB initialized (schema.sql + data.sql)");
    }

    @BeforeEach
    public void setUp() {
        staffDAO = new StaffDAO();
        roleDAO = new RoleDAO();
        try (java.sql.Connection conn = fu.swt301.sms.utils.DBUtils.getConnection();
             java.sql.Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Staff");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Staff sample() {
        Staff s = new Staff();
        s.setFullName("Nguyen Van A");
        s.setGender(true);
        s.setPhoneNumber("0123456789");
        s.setEmail("a@example.com");
        s.setPassword("123456");
        s.setRole(roleDAO.getRoleById(2)); // STAFF
        s.setIsActive(true);
        s.setStaffCode("STF001");
        s.setDateOfBirth(LocalDate.of(1999, 1, 1));
        s.setDepartment("IT");
        s.setPosition("Developer");
        s.setSalary(15000000);
        s.setHireDate(LocalDate.of(2024, 1, 1));
        return s;
    }

    // -----------------------------
    // 15 TEST CASES
    // -----------------------------

    @Test
    public void testCreateStaff() {
        Staff s = sample();
        staffDAO.createStaff(s);
        assertNotNull(staffDAO.getStaffByEmail("a@example.com"));
    }

    @Test
    public void testUpdateStaff() {
        Staff s = sample();
        staffDAO.createStaff(s);

        s.setFullName("Updated Name");
        staffDAO.updateStaff(s);

        assertEquals("Updated Name", staffDAO.getStaffByEmail("a@example.com").getFullName());
    }

    @Test
    public void testDeleteStaff() {
        Staff s = sample();
        staffDAO.createStaff(s);

        staffDAO.deleteStaff(s.getStaffID());
        assertNull(staffDAO.getStaffById(s.getStaffID()));
    }

    @Test
    public void testGetStaffById() {
        Staff s = sample();
        staffDAO.createStaff(s);

        assertNotNull(staffDAO.getStaffById(s.getStaffID()));
    }

    @Test
    public void testGetStaffByEmail() {
        Staff s = sample();
        staffDAO.createStaff(s);

        assertNotNull(staffDAO.getStaffByEmail("a@example.com"));
    }

    @Test
    public void testGetStaffByStaffCode() {
        Staff s = sample();
        staffDAO.createStaff(s);

        assertNotNull(staffDAO.getStaffByStaffCode("STF001"));
    }

    @Test
    public void testGetAllStaff() {
        Staff s = sample();
        staffDAO.createStaff(s);

        assertFalse(staffDAO.getAllStaff().isEmpty());
    }

    @Test
    public void testGetStaffByDepartment() {
        Staff s = sample();
        staffDAO.createStaff(s);

        assertFalse(staffDAO.getStaffByDepartment("IT").isEmpty());
    }

    @Test
    public void testGetStaffByRole() {
        Staff s = sample();
        staffDAO.createStaff(s);

        assertFalse(staffDAO.getStaffByRole(2).isEmpty());
    }

    @Test
    public void testSearchStaffByName() {
        Staff s = sample();
        staffDAO.createStaff(s);

        assertFalse(staffDAO.searchStaffByName("Nguyen").isEmpty());
    }

    @Test
    public void testSearchStaffAdvanced() {
        Staff s = sample();
        staffDAO.createStaff(s);

        assertFalse(staffDAO.searchStaffAdvanced("Nguyen", "IT", "Developer", true).isEmpty());
    }

    @Test
    public void testIsEmailExists() throws Exception {
        Staff s = sample();
        staffDAO.createStaff(s);

        assertTrue(staffDAO.isEmailExists("a@example.com", 0));
    }

    @Test
    public void testIsFullNameExists() throws Exception {
        Staff s = sample();
        staffDAO.createStaff(s);

        assertTrue(staffDAO.isFullNameExists("Nguyen Van A", 0));
    }

    @Test
    public void testIsPhoneNumberExists() throws Exception {
        Staff s = sample();
        staffDAO.createStaff(s);

        assertTrue(staffDAO.isPhoneNumberExists("0123456789", 0));
    }

    @Test
    public void testCheckLogin() {
        Staff s = sample();
        staffDAO.createStaff(s);

        assertNotNull(staffDAO.checkLogin("a@example.com", "123456"));
    }
}
