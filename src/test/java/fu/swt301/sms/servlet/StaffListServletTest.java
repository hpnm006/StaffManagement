package fu.swt301.sms.servlet;

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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StaffListServletTest {

    @Mock
    private StaffService staffService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher dispatcher;

    @InjectMocks
    private StaffListServlet servlet;

    @BeforeEach
    void setUp() {
        servlet.setStaffService(staffService);
    }

    @Test
    void testDoGet_Success() throws Exception {
        when(request.getParameter("searchName")).thenReturn("Nguyen");
        when(request.getParameter("searchStatus")).thenReturn("true");
        when(request.getParameter("page")).thenReturn("2");
        when(request.getRequestDispatcher("staff-list.jsp")).thenReturn(dispatcher);

        StaffService.PaginatedList<Staff> dummyList = new StaffService.PaginatedList<>(
            new ArrayList<>(), 2, 5
        );
        when(staffService.getStaffListPaginated("Nguyen", "true", 2, 5)).thenReturn(dummyList);

        servlet.doGet(request, response);

        verify(request).setAttribute("staffList", dummyList.list());
        verify(request).setAttribute("currentPage", 2);
        verify(request).setAttribute("totalPages", 5);
        verify(request).setAttribute("searchName", "Nguyen");
        verify(request).setAttribute("searchStatus", "true");
        verify(dispatcher).forward(request, response);
    }
}
