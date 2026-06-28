package fu.swt301.sms.service;

import fu.swt301.sms.dao.StaffDAO;
import fu.swt301.sms.entity.Staff;
import java.sql.SQLException;

public class StaffService {
    private StaffDAO staffDAO;

    public StaffService() {
        this.staffDAO = new StaffDAO();
    }

    public StaffService(StaffDAO staffDAO) {
        this.staffDAO = staffDAO;
    }

    public void validateStaff(Staff staff) throws IllegalArgumentException {
        try {
            if (staffDAO.isEmailExists(staff.getEmail(), staff.getStaffID())) {
                throw new IllegalArgumentException("Email already exists. Please choose another one.");
            }
            if (staffDAO.isFullNameExists(staff.getFullName(), staff.getStaffID())) {
                throw new IllegalArgumentException("Full name already exists. Please choose another one.");
            }
            if (staffDAO.isPhoneNumberExists(staff.getPhoneNumber(), staff.getStaffID())) {
                throw new IllegalArgumentException("Phone number already exists. Please choose another one.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Database error during validation.", e);
        }
    }

    public void createStaff(Staff staff) {
        validateStaff(staff);
        staffDAO.createStaff(staff);
    }

    public void updateStaff(Staff staff) {
        validateStaff(staff);
        staffDAO.updateStaff(staff);
    }
}
