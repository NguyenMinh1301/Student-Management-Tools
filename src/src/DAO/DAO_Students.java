package src.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import src.Model.Model_Students;
import src.Connection.Connection_ConnectorHelper;
import static src.Service.Handle_Exception.HandleException;

public interface DAO_Students {

    default List<Model_Students> getAllStudents() {
        List<Model_Students> studentsList = new ArrayList<>();
        String SQL = "SELECT * FROM STUDENTS";
        try (
                Connection conn = Connection_ConnectorHelper.connection(); Statement stm = conn.createStatement(); ResultSet rs = stm.executeQuery(SQL);) {
            while (rs.next()) {
                studentsList.add(new Model_Students(
                        rs.getString("IdStudent"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getBoolean("Gender"),
                        rs.getString("Address"),
                        rs.getString("Avatar")
                ));
            }

        } catch (SQLException ex) {
            HandleException(ex);
        }
        return studentsList;
    }

    default List<Model_Students> searchStudent(String keyword) {
        List<Model_Students> studentsList = new ArrayList<>();
        String SQL = "SELECT * FROM STUDENTS WHERE IdStudent LIKE ? OR Name LIKE ?";
        try (
                Connection conn = Connection_ConnectorHelper.connection(); PreparedStatement prstm = conn.prepareStatement(SQL)) {
            String key = "%" + keyword + "%";
            prstm.setString(1, key);
            prstm.setString(2, key);
            ResultSet rs = prstm.executeQuery();

            while (rs.next()) {
                studentsList.add(new Model_Students(
                        rs.getString("IdStudent"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Phone"),
                        rs.getBoolean("Gender"),
                        rs.getString("Address"),
                        rs.getString("Avatar")
                ));
            }

        } catch (SQLException ex) {
            HandleException(ex);
        }
        return studentsList;
    }

    default List<Model_Students> getAllStudentWithIdAndName() {
        List<Model_Students> studentsList = new ArrayList<>();
        String SQL = "SELECT IdStudent, Name FROM STUDENTS;";

        try (
                Connection conn = Connection_ConnectorHelper.connection(); Statement stm = conn.createStatement(); ResultSet rs = stm.executeQuery(SQL);) {
            while (rs.next()) {
                Model_Students studentList = new Model_Students();
                studentList.setId(rs.getString("IdStudent"));
                studentList.setName(rs.getString("Name"));
                studentsList.add(studentList);
            }
        } catch (SQLException ex) {
            HandleException(ex);
        }

        return studentsList;
    }

    default String getNextStudentIdPreview() {
        String nextId = "TV00001";
        String SQL = "SELECT LastNumber + 1 AS nextNum FROM STUDENT_COUNTER";
        try (
                Connection conn = Connection_ConnectorHelper.connection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(SQL);) {
            if (rs.next()) {
                int num = rs.getInt("nextNum");
                nextId = "TV" + String.format("%05d", num);
            }
        } catch (SQLException e) {
            HandleException(e);
        }
        return nextId;
    }

    default int addStudent(String name, String email, String phone, int gender, String address, String avatar) {
        String SQL = "INSERT INTO STUDENTS (Name, Email, Phone, Gender, Address, Avatar) VALUES (?, ?, ?, ?, ?, ?);";
        int check = 0;
        try (
                Connection conn = Connection_ConnectorHelper.connection(); PreparedStatement prstm = conn.prepareStatement(SQL);) {
            prstm.setString(1, name);
            prstm.setString(2, email);
            prstm.setString(3, phone);
            prstm.setInt(4, gender);
            prstm.setString(5, address);
            prstm.setString(6, avatar);
            prstm.executeUpdate();
            check = 1;
            return check;
        } catch (SQLException ex) {
            HandleException(ex);
        }
        return check;
    }

    default boolean updateStudent(String id, String name, String email, String phone, int gender, String address, String avatar) {
        String SQL = "UPDATE STUDENTS SET Name = ?, Email = ?, Phone = ?, Gender = ?, Address = ?, Avatar = ? WHERE IdStudent = ?";
        try (
                Connection conn = Connection_ConnectorHelper.connection(); PreparedStatement prstm = conn.prepareStatement(SQL);) {
            prstm.setString(1, name);
            prstm.setString(2, email);
            prstm.setString(3, phone);
            prstm.setInt(4, gender);
            prstm.setString(5, address);
            prstm.setString(6, avatar);
            prstm.setString(7, id);

            int rows = prstm.executeUpdate();
            if (rows > 0) {
                return true;
            }
        } catch (SQLException ex) {
            HandleException(ex);
        }
        return false;
    }

    default boolean deleteStudent(String id) {
        String SQL = "DELETE FROM STUDENTS WHERE IdStudent = ?";
        try (
                Connection conn = Connection_ConnectorHelper.connection(); PreparedStatement prstm = conn.prepareStatement(SQL);) {
            prstm.setString(1, id);
            prstm.executeUpdate();
            return true;
        } catch (SQLException ex) {
            HandleException(ex);
        }
        return false;
    }

}
