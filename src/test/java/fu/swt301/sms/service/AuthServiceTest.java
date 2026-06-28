package fu.swt301.sms.service;

import fu.swt301.sms.dao.StaffDAO;
import fu.swt301.sms.entity.Staff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private StaffDAO staffDAO;

    @InjectMocks
    private AuthService authService;

    private Staff testStaff;
    private String rawPassword = "password123";
    private String hashedPassword;

    @BeforeEach
    public void setUp() {
        hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        testStaff = new Staff();
        testStaff.setStaffID(1);
        testStaff.setEmail("test@example.com");
        testStaff.setPassword(hashedPassword);
        testStaff.setFailedAttempts(0);
    }

    @Test
    public void testLogin_Success() throws AuthException {
        when(staffDAO.getStaffByEmail("test@example.com")).thenReturn(testStaff);

        Staff result = authService.login("test@example.com", rawPassword);

        assertNotNull(result);
        assertEquals(1, result.getStaffID());
        // Verify attempts were reset
        verify(staffDAO, never()).updateLoginAttempts(anyInt(), anyInt(), any());
    }

    @Test
    public void testLogin_Success_ResetAttempts() throws AuthException {
        testStaff.setFailedAttempts(2);
        when(staffDAO.getStaffByEmail("test@example.com")).thenReturn(testStaff);

        Staff result = authService.login("test@example.com", rawPassword);

        assertNotNull(result);
        verify(staffDAO, times(1)).updateLoginAttempts(eq(1), eq(0), isNull());
    }

    @Test
    public void testLogin_InvalidEmail() {
        when(staffDAO.getStaffByEmail("wrong@example.com")).thenReturn(null);

        AuthException exception = assertThrows(AuthException.class, () -> {
            authService.login("wrong@example.com", rawPassword);
        });

        assertEquals("Invalid email or password", exception.getMessage());
    }

    @Test
    public void testLogin_WrongPassword() {
        when(staffDAO.getStaffByEmail("test@example.com")).thenReturn(testStaff);

        AuthException exception = assertThrows(AuthException.class, () -> {
            authService.login("test@example.com", "wrongpassword");
        });

        assertEquals("Invalid email or password", exception.getMessage());
        verify(staffDAO, times(1)).updateLoginAttempts(eq(1), eq(1), isNull());
    }

    @Test
    public void testLogin_WrongPassword_Lockout() {
        testStaff.setFailedAttempts(4);
        when(staffDAO.getStaffByEmail("test@example.com")).thenReturn(testStaff);

        AuthException exception = assertThrows(AuthException.class, () -> {
            authService.login("test@example.com", "wrongpassword");
        });

        assertEquals("Bạn đã nhập sai 5 lần. Tài khoản bị khóa 5 phút.", exception.getMessage());
        verify(staffDAO, times(1)).updateLoginAttempts(eq(1), eq(5), any(Timestamp.class));
    }

    @Test
    public void testLogin_AccountLocked() {
        testStaff.setFailedAttempts(5);
        testStaff.setLockoutTime(new Timestamp(System.currentTimeMillis() + 60000)); // locked for 1 min
        when(staffDAO.getStaffByEmail("test@example.com")).thenReturn(testStaff);

        AuthException exception = assertThrows(AuthException.class, () -> {
            authService.login("test@example.com", rawPassword);
        });

        assertTrue(exception.getMessage().contains("Tài khoản bị khóa"));
        verify(staffDAO, never()).updateLoginAttempts(anyInt(), anyInt(), any());
    }

    @Test
    public void testLogin_LockoutExpired_Success() throws AuthException {
        testStaff.setFailedAttempts(5);
        testStaff.setLockoutTime(new Timestamp(System.currentTimeMillis() - 60000)); // expired 1 min ago
        when(staffDAO.getStaffByEmail("test@example.com")).thenReturn(testStaff);

        Staff result = authService.login("test@example.com", rawPassword);

        assertNotNull(result);
        verify(staffDAO, times(2)).updateLoginAttempts(eq(1), eq(0), isNull());
    }
}
