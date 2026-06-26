package fu.swt301.sms.dao;

import fu.swt301.sms.entity.Role;
import fu.swt301.sms.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleDAO {
    public List<Role> getAllRoles() {
        List<Role> roleList = new ArrayList<>();
        String sql = "SELECT * FROM Role";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Role role = new Role();
                role.setRoleID(rs.getInt("Role_ID"));
                role.setRoleName(rs.getString("Role_Name"));
                roleList.add(role);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return roleList;
    }

   // -----------------------------
    // GET ROLE BY ID
    // -----------------------------
    public Role getRoleById(int roleId) {
        String sql = "SELECT * FROM Role WHERE Role_ID = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roleId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Role r = new Role();
                    r.setRoleID(rs.getInt("Role_ID"));
                    r.setRoleName(rs.getString("Role_Name"));
                    return r;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // -----------------------------
    // GET ROLE BY NAME
    // -----------------------------
    public Role getRoleByName(String roleName) {
        String sql = "SELECT * FROM Role WHERE Role_Name = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, roleName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Role r = new Role();
                    r.setRoleID(rs.getInt("Role_ID"));
                    r.setRoleName(rs.getString("Role_Name"));
                    return r;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // -----------------------------
    // INSERT ROLE
    // -----------------------------
    public void insertRole(Role role) {
        String sql = "INSERT INTO Role (Role_ID, Role_Name) VALUES (?, ?)";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, role.getRoleID());
            ps.setString(2, role.getRoleName());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -----------------------------
    // UPDATE ROLE
    // -----------------------------
    public void updateRole(Role role) {
        String sql = "UPDATE Role SET Role_Name = ? WHERE Role_ID = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, role.getRoleName());
            ps.setInt(2, role.getRoleID());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // -----------------------------
    // DELETE ROLE
    // -----------------------------
    public void deleteRole(int roleId) {
        String sql = "DELETE FROM Role WHERE Role_ID = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, roleId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
