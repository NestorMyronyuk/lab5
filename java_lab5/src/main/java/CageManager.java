import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CageManager {
    final String url = "jdbc:mysql://localhost:3306/Base";
    final String user = "root";
    final String pass = "nestor2001";

    private Connection connection;

    public CageManager(){
        try{
            connection = DriverManager.getConnection(url,user,pass);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void close() throws SQLException {
        connection.close();
    }
    public void createTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE cages(id INT(15), supervisorId INT(15));");
        statement.executeUpdate("CREATE TABLE animalsInCage(cageId INT(15), animalId INT(15));");
    }
    public void deleteTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE cages;");
        statement.executeUpdate("DROP TABLE animalsInCage;");
    }
    public void addCage(Cage cage) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO cages VALUES (?,?);");
        statement.setInt(1, cage.getNumber());
        statement.setInt(2, cage.getSupervisor());
        statement.execute();

        for (var anId: cage.getAnimals()) {
            PreparedStatement statement1 = connection.prepareStatement("INSERT INTO animalInCage VALUES (?,?);") ;
            statement1.setInt(1,cage.getNumber());
            statement1.setInt(2,anId);
            statement1.execute();
        }
    }
    public void removeCage(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM cages WHERE id = ?;");
        PreparedStatement statement1 = connection.prepareStatement("DELETE FROM animalInCage WHERE cageId = ?;");
        statement.setInt(1,id);
        statement1.setInt(2,id);
        statement.execute();
        statement1.execute();
    }
    public Collection<Cage> selectAll() throws SQLException {
        Statement statement = connection.createStatement();
        Collection<Cage> cages = new ArrayList<>();
        ResultSet set = statement.executeQuery("SELECT * FROM cages;");

        while (set.next()){
            PreparedStatement pr = connection.prepareStatement("SELECT * FROM animalInCage WHERE cageId = ?;");
            pr.setInt(1,set.getInt("id"));
            Collection<Integer> ids = new ArrayList<>();
            ResultSet resultSet = pr.executeQuery();
            while(resultSet.next()){
                ids.add(resultSet.getInt("animalId"));
            }

            cages.add(
                    new Cage.Builder()
                            .addAnimals((List<Integer>) ids)
                            .addCageNumber(set.getInt("id"))
                            .addSupervisor(set.getInt("supervisorId"))
                            .build());
        }
        return cages;
    }
}
