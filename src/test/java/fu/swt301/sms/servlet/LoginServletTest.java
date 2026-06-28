package fu.swt301.sms.servlet;

import fu.swt301.sms.dao.StaffDAO;
import fu.swt301.sms.entity.Staff;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoginServletTest {

    @Mock
    private StaffDAO staffDAO;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @InjectMocks
    private LoginServlet servlet;

    @BeforeEach
    void setUp() {
        servlet.setStaffDAO(staffDAO);
    }

    @Test
    void testLoginSuccess() throws Exception {

        Staff staff = new Staff();

        when(request.getParameter("email"))
                .thenReturn("admin@example.com");

        when(request.getParameter("password"))
                .thenReturn("admin123");

        when(staffDAO.checkLogin("admin@example.com", "admin123"))
                .thenReturn(staff);

        when(request.getSession())
                .thenReturn(session);

        servlet.doPost(request, response);

        verify(session).setAttribute("user", staff);

        verify(response).sendRedirect("staff-list");
    }

    @Test
    void testLoginFail() throws Exception {

        when(request.getParameter("email"))
                .thenReturn("abc@gmail.com");

        when(request.getParameter("password"))
                .thenReturn("123");

        when(staffDAO.checkLogin(anyString(), anyString()))
                .thenReturn(null);

        when(request.getRequestDispatcher("login.jsp"))
                .thenReturn(dispatcher);

        servlet.doPost(request, response);

        verify(request)
                .setAttribute("error",
                        "Invalid email or password");

        verify(dispatcher)
                .forward(request, response);
    }
}