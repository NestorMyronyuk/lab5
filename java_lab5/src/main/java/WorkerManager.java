import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class WorkerManager {
    final String url = "jdbc:mysql://localhost:3306/Base";
    final String user = "root";
    final String pass = "nestor2001";

    private Connection connection;

    public WorkerManager(){
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
        statement.executeUpdate("CREATE TABLE workers(id INT(15), name VARCHAR (15), surname VARCHAR (15), position VARCHAR (15));");
    }
    public void deleteTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("DROP TABLE workers;");
    }
    public void addWorker(Worker worker) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO workers VALUES (?,?,?,?);");
        statement.setInt(1, worker.getId());
        statement.setString(2, worker.getName());
        statement.setString(3, worker.getSurname());
        statement.setString(4, worker.getPosition());

        statement.execute();
    }
    public void removeCage(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM workers WHERE id = ?;");
        statement.setInt(1,id);
        statement.execute();
    }
    public Collection<Worker> selectAll() throws SQLException {
        Statement statement = connection.createStatement();
        Collection<Worker> workers = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM workers;");

        while (resultSet.next()) {
            workers.add(
                    new Worker.Builder()
                            .addId(resultSet.getInt("id"))
                            .addName(resultSet.getString("name"))
                            .addSurname(resultSet.getString("surname"))
                            .addPosition(resultSet.getString("position"))
                            .build()
            );
        }
        return workers;
    }
}
