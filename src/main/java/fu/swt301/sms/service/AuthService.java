package fu.swt301.sms.service;

import fu.swt301.sms.dao.StaffDAO;
import fu.swt301.sms.entity.Staff;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Timestamp;

public class AuthService {
    private StaffDAO staffDAO;

    public AuthService() {
        this.staffDAO = new StaffDAO();
    }

    public AuthService(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    public Staff login(String email, String password) throws AuthException {
        Staff staff = staffDAO.getStaffByEmail(email);

        if (staff == null) {
            throw new AuthException("Invalid email or password");
        }

        boolean hadFailedAttempts = staff.getFailedAttempts() > 0 || staff.getLockoutTime() != null;

        // Check if locked out
        if (staff.getLockoutTime() != null) {
            long currentTime = System.currentTimeMillis();
            if (staff.getLockoutTime().getTime() > currentTime) {
                long remainingMillis = staff.getLockoutTime().getTime() - currentTime;
                long remainingMinutes = remainingMillis / (60 * 1000) + 1;
                throw new AuthException("Tài khoản bị khóa. Vui lòng thử lại sau " + remainingMinutes + " phút.");
            } else {
                // Lockout expired, reset attempts
                staff.setFailedAttempts(0);
                staff.setLockoutTime(null);
                staffDAO.updateLoginAttempts(staff.getStaffID(), 0, null);
            }
        }

        // Verify password
        if (BCrypt.checkpw(password, staff.getPassword())) {
            // Success
            if (hadFailedAttempts) {
                staff.setFailedAttempts(0);
                staff.setLockoutTime(null);
                staffDAO.updateLoginAttempts(staff.getStaffID(), 0, null);
            }
            return staff;
        } else {
            // Failed
            int attempts = staff.getFailedAttempts() + 1;
            Timestamp lockoutTime = null;
            
            if (attempts >= 5) {
                // Lock for 5 minutes
                lockoutTime = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000);
            }
            
            staffDAO.updateLoginAttempts(staff.getStaffID(), attempts, lockoutTime);
            
            if (attempts >= 5) {
                throw new AuthException("Bạn đã nhập sai 5 lần. Tài khoản bị khóa 5 phút.");
            } else {
                throw new AuthException("Invalid email or password");
            }
        }
    }
}
