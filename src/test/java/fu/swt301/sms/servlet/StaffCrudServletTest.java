package fu.swt301.sms.servlet;

import fu.swt301.sms.dao.RoleDAO;
import fu.swt301.sms.dao.StaffDAO;
import fu.swt301.sms.entity.Role;
import fu.swt301.sms.entity.Staff;
import fu.swt301.sms.service.StaffService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StaffCrudServletTest {

    @Mock
    private StaffService staffService;

    @Mock
    private StaffDAO staffDAO;

    @Mock
    private RoleDAO roleDAO;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher dispatcher;

    @InjectMocks
    private StaffCrudServlet servlet;

    @BeforeEach
    void setUp() {
        servlet.setStaffService(staffService);
        servlet.setStaffDAO(staffDAO);
        servlet.setRoleDAO(roleDAO);
    }

    @Test
    void testCreateStaffSuccess() throws Exception {
        when(request.getParameter("action")).thenReturn("create");
        when(request.getParameter("staffID")).thenReturn("");
        when(request.getParameter("fullName")).thenReturn("Nguyen Van A");
        when(request.getParameter("gender")).thenReturn("true");
        when(request.getParameter("phoneNumber")).thenReturn("0123456789");
        when(request.getParameter("email")).thenReturn("a@gmail.com");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("roleID")).thenReturn("1");
        when(request.getParameter("isActive")).thenReturn("true");

        servlet.doPost(request, response);

        verify(staffService).createStaff(any(Staff.class));
        verify(response).sendRedirect("staff-list");
    }

    @Test
    void testUpdateStaffSuccess() throws Exception {
        when(request.getParameter("action")).thenReturn("update");
        when(request.getParameter("staffID")).thenReturn("1");
        when(request.getParameter("fullName")).thenReturn("Nguyen Van B");
        when(request.getParameter("gender")).thenReturn("true");
        when(request.getParameter("phoneNumber")).thenReturn("0987654321");
        when(request.getParameter("email")).thenReturn("b@gmail.com");
        when(request.getParameter("roleID")).thenReturn("1");
        when(request.getParameter("isActive")).thenReturn("true");

        servlet.doPost(request, response);

        verify(staffService).updateStaff(any(Staff.class));
        verify(response).sendRedirect("staff-list");
    }

    @Test
    void testCreateStaff_EmailAlreadyExists() throws Exception {
        when(request.getParameter("action")).thenReturn("create");
        when(request.getParameter("staffID")).thenReturn("");
        when(request.getParameter("fullName")).thenReturn("Nguyen Van A");
        when(request.getParameter("gender")).thenReturn("true");
        when(request.getParameter("phoneNumber")).thenReturn("0123456789");
        when(request.getParameter("email")).thenReturn("admin@example.com");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("roleID")).thenReturn("1");
        when(request.getParameter("isActive")).thenReturn("true");

        doThrow(new IllegalArgumentException("Email already exists. Please choose another one."))
                .when(staffService).createStaff(any(Staff.class));

        when(roleDAO.getAllRoles()).thenReturn(new ArrayList<>());
        when(request.getRequestDispatcher("staff-form.jsp"))
                .thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute(
                eq("errorMessage"),
                contains("Email already exists"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testCreateStaff_FullNameAlreadyExists() throws Exception {
        when(request.getParameter("action")).thenReturn("create");
        when(request.getParameter("staffID")).thenReturn("");
        when(request.getParameter("fullName")).thenReturn("Nguyen Van A");
        when(request.getParameter("gender")).thenReturn("true");
        when(request.getParameter("phoneNumber")).thenReturn("0123456789");
        when(request.getParameter("email")).thenReturn("abc@gmail.com");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("roleID")).thenReturn("1");
        when(request.getParameter("isActive")).thenReturn("true");

        doThrow(new IllegalArgumentException("Full name already exists. Please choose another one."))
                .when(staffService).createStaff(any(Staff.class));

        when(roleDAO.getAllRoles()).thenReturn(new ArrayList<>());
        when(request.getRequestDispatcher("staff-form.jsp"))
                .thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute(
                eq("errorMessage"),
                contains("Full name already exists"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testCreateStaff_PhoneNumberAlreadyExists() throws Exception {
        when(request.getParameter("action")).thenReturn("create");
        when(request.getParameter("staffID")).thenReturn("");
        when(request.getParameter("fullName")).thenReturn("Nguyen Van A");
        when(request.getParameter("gender")).thenReturn("true");
        when(request.getParameter("phoneNumber")).thenReturn("0123456789");
        when(request.getParameter("email")).thenReturn("abc@gmail.com");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("roleID")).thenReturn("1");
        when(request.getParameter("isActive")).thenReturn("true");

        doThrow(new IllegalArgumentException("Phone number already exists. Please choose another one."))
                .when(staffService).createStaff(any(Staff.class));

        when(roleDAO.getAllRoles()).thenReturn(new ArrayList<>());
        when(request.getRequestDispatcher("staff-form.jsp"))
                .thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute(
                eq("errorMessage"),
                contains("Phone number already exists"));
        verify(dispatcher).forward(request, response);
    }

    @Test
    void testDeleteStaffSuccess() throws Exception {
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("id")).thenReturn("1");

        servlet.doGet(request, response);

        verify(staffDAO).deleteStaff(1);
        verify(response).sendRedirect("staff-list");
    }
}
