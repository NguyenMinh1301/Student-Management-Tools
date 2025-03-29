package src.Service;

import java.util.List;
import src.DAO.DAO_Sort;
import src.Model.Model_Score;

public class Service_Sort {

    public List<Model_Score> sortById() {
        return new DAO_Sort() {
        }.getScoresSortById();
    }
}
