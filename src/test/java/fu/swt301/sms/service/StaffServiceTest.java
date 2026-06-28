package fu.swt301.sms.service;

import fu.swt301.sms.dao.StaffDAO;
import fu.swt301.sms.entity.Staff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StaffServiceTest {

    @Mock
    private StaffDAO staffDAO;

    @InjectMocks
    private StaffService staffService;

    private Staff staff;

    @BeforeEach
    void setUp() {
        staff = new Staff();
        staff.setStaffID(1);
        staff.setEmail("test@gmail.com");
        staff.setFullName("Nguyen Van A");
        staff.setPhoneNumber("0123456789");
    }

    @Test
    void testValidateStaffSuccess() throws Exception {
        when(staffDAO.isEmailExists(anyString(), anyInt())).thenReturn(false);
        when(staffDAO.isFullNameExists(anyString(), anyInt())).thenReturn(false);
        when(staffDAO.isPhoneNumberExists(anyString(), anyInt())).thenReturn(false);

        assertDoesNotThrow(() -> staffService.validateStaff(staff));
    }

    @Test
    void testValidateStaffEmailExists() throws Exception {
        when(staffDAO.isEmailExists("test@gmail.com", 1)).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            staffService.validateStaff(staff);
        });
        assertEquals("Email already exists. Please choose another one.", ex.getMessage());
    }

    @Test
    void testValidateStaffFullNameExists() throws Exception {
        when(staffDAO.isEmailExists("test@gmail.com", 1)).thenReturn(false);
        when(staffDAO.isFullNameExists("Nguyen Van A", 1)).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            staffService.validateStaff(staff);
        });
        assertEquals("Full name already exists. Please choose another one.", ex.getMessage());
    }

    @Test
    void testValidateStaffPhoneNumberExists() throws Exception {
        when(staffDAO.isEmailExists("test@gmail.com", 1)).thenReturn(false);
        when(staffDAO.isFullNameExists("Nguyen Van A", 1)).thenReturn(false);
        when(staffDAO.isPhoneNumberExists("0123456789", 1)).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            staffService.validateStaff(staff);
        });
        assertEquals("Phone number already exists. Please choose another one.", ex.getMessage());
    }

    @Test
    void testValidateStaffSQLException() throws Exception {
        when(staffDAO.isEmailExists(anyString(), anyInt())).thenThrow(new SQLException("DB Error"));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            staffService.validateStaff(staff);
        });
        assertTrue(ex.getMessage().contains("Database error during validation"));
    }

    @Test
    void testCreateStaff() throws Exception {
        when(staffDAO.isEmailExists(anyString(), anyInt())).thenReturn(false);
        when(staffDAO.isFullNameExists(anyString(), anyInt())).thenReturn(false);
        when(staffDAO.isPhoneNumberExists(anyString(), anyInt())).thenReturn(false);

        staffService.createStaff(staff);

        verify(staffDAO, times(1)).createStaff(staff);
    }

    @Test
    void testUpdateStaff() throws Exception {
        when(staffDAO.isEmailExists(anyString(), anyInt())).thenReturn(false);
        when(staffDAO.isFullNameExists(anyString(), anyInt())).thenReturn(false);
        when(staffDAO.isPhoneNumberExists(anyString(), anyInt())).thenReturn(false);

        staffService.updateStaff(staff);

        verify(staffDAO, times(1)).updateStaff(staff);
    }
}
