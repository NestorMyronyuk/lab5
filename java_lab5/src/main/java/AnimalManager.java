import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;


public class AnimalManager {
    final String url = "jdbc:mysql://localhost:3306/Base";
    final String user = "root";
    final String pass = "nestor2001";

    private Connection connection;

    public AnimalManager(){
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
        statement.executeUpdate("CREATE TABLE animals(id INT(15), type VARCHAR (15), name VARCHAR (15), sex INT(1), age INT(2));");
    }
    public void deleteTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE animals");
    }
    public void addAnimal(Animal animal) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO animals VALUES (?,?,?,?,?);");
        statement.setInt(1, animal.getId());
        statement.setString(2, animal.getType());
        statement.setString(3, animal.getName());
        statement.setInt(4, animal.isSex() ?1:0);
        statement.setInt(5, animal.getAge());
        statement.execute();
    }
    public void removeAnimal(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM animals WHERE id = ?;");
        statement.setInt(1,id);
        statement.execute();
    }
    public Collection<Animal> selectAll() throws SQLException {
        Statement statement = connection.createStatement();
        Collection<Animal> animals = new ArrayList<>();
        ResultSet set = statement.executeQuery("SELECT * FROM animals");
        while (set.next()){
            animals.add(
                    new Animal.Builder()
                            .addId(set.getInt("id"))
                            .addType(set.getString("type"))
                            .addSex(set.getInt("sex") == 1)
                            .addAge(set.getInt("age"))
                            .addName(set.getString("name"))
                            .build()
            );
        }
        return animals;
    }

}
