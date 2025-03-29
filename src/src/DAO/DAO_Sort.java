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
import static src.DAO.HandleException.HandleException;
import src.Model.Model_Score;

public interface DAO_Sort {

    default List<Model_Score> getScoresSortById() {
        List<Model_Score> list = new ArrayList<>();
        String SQL = "SELECT * FROM SCORES ORDER BY STUDENT_ID ASC";
        try (
                Connection conn = Connection_ConnectorHelper.connection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(SQL);) {
            while (rs.next()) {
                list.add(new Model_Score(
                        rs.getString("STUDENT_ID"),
                        rs.getString("NAME"),
                        rs.getFloat("ENGLISH"),
                        rs.getFloat("COMPUTER"),
                        rs.getFloat("PHYSICAL")
                ));
            }
        } catch (SQLException e) {
            HandleException(e);
        }
        return list;
    }
}
