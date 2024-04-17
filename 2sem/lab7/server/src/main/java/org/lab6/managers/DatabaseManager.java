package org.lab6.managers;

import common.models.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.*;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Vector;
public class DatabaseManager {
    private final String url;
    private final String user;
    private final String password;

    public DatabaseManager(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    public Vector<Dragon> loadCollection() {
        Vector<Dragon> dragons = new Vector<>();
        try (Connection connection = connect()) {
            String query = "SELECT dragon.*, coordinates.id as coord_id, coordinates.x as coord_x, coordinates.y as coord_y, " +
                    "person.id as person_id, person.name as person_name, person.birthday as person_birthday, person.height as person_height, person.weight as person_weight, " +
                    "location.id as loc_id, location.x as loc_x, location.y as loc_y, location.z as loc_z " +
                    "FROM dragon " +
                    "JOIN coordinates ON dragon.coordinates_id = coordinates.id " +
                    "LEFT JOIN person ON dragon.killer_id = person.id " +
                    "LEFT JOIN location ON person.location_id = location.id";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Coordinates coordinates = new Coordinates(resultSet.getInt("coord_id"),
                        resultSet.getDouble("coord_x"),
                        resultSet.getLong("coord_y"));
                Person killer = null;
                if (resultSet.getObject("person_id") != null) {
                    Location location = new Location(resultSet.getInt("loc_id"),
                            resultSet.getFloat("loc_x"),
                            resultSet.getFloat("loc_y"),
                            resultSet.getLong("loc_z"));
                    Timestamp birthdayTimestamp = resultSet.getTimestamp("person_birthday");
                    ZonedDateTime birthday = birthdayTimestamp == null ? null : birthdayTimestamp.toLocalDateTime().atZone(ZoneOffset.UTC);
                    killer = new Person(resultSet.getInt("person_id"),
                            resultSet.getString("person_name"),
                            birthday,
                            (resultSet.getObject("person_height") == null) ? null : (float) resultSet.getObject("person_height"),
                            (resultSet.getObject("person_weight") == null) ? null : (long) resultSet.getObject("person_weight"),
                            location);
                }
                Dragon dragon = new Dragon(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        coordinates,
                        resultSet.getInt("age"),
                        resultSet.getBoolean("speaking"),
                        Color.valueOf(resultSet.getString("color")),
                        resultSet.getObject("character") == null ? null : DragonCharacter.valueOf(resultSet.getString("character")),
                        killer);
                dragons.add(dragon);
            }
        } catch (SQLException e) {
            System.err.println("Failed to load collection from database: " + e.getMessage());
            System.exit(0);
        }
        return dragons;
    }

    public boolean clearDb() {
        String deleteDragonsSQL = "DELETE FROM dragon";
        String deletePersonsSQL = "DELETE FROM person";
        String deleteCoordinatesSQL = "DELETE FROM coordinates";
        String deleteLocationsSQL = "DELETE FROM location";

        try (Connection connection = connect()) {
            // Begin transaction
            connection.setAutoCommit(false);

            // Delete all dragons
            try (PreparedStatement pstmt = connection.prepareStatement(deleteDragonsSQL)) {
                pstmt.executeUpdate();
            }

            // Delete all persons
            try (PreparedStatement pstmt = connection.prepareStatement(deletePersonsSQL)) {
                pstmt.executeUpdate();
            }

            // Delete all coordinates
            try (PreparedStatement pstmt = connection.prepareStatement(deleteCoordinatesSQL)) {
                pstmt.executeUpdate();
            }

            // Delete all locations
            try (PreparedStatement pstmt = connection.prepareStatement(deleteLocationsSQL)) {
                pstmt.executeUpdate();
            }

            // Commit transaction
            connection.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to clear all dragons from database: " + e.getMessage());
            return false;
        }
    }

    public int addCoordinates(Connection connection, Coordinates coordinates) throws SQLException {
        String query = "INSERT INTO coordinates (x, y) VALUES (?, ?) RETURNING id";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, coordinates.getX());
        preparedStatement.setLong(2, coordinates.getY());
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            throw new SQLException("Failed to add coordinates to database");
        }
    }

    private void updateCoordinates(Connection connection, Coordinates coordinates) throws SQLException {
        String query = "UPDATE coordinates SET x = ?, y = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setDouble(1, coordinates.getX());
        preparedStatement.setLong(2, coordinates.getY());
        preparedStatement.setInt(3, coordinates.getId());

        preparedStatement.executeUpdate();
    }

    public int addDragon(Dragon dragon) {
        Connection connection = null;
        try {
            //Thread.sleep(10000);
            connection = connect();
            // start transaction
            connection.setAutoCommit(false);

            int coordinatesId = addCoordinates(connection, dragon.getCoordinates());
            Integer killerId = null;
            if (dragon.getKiller() != null) {
                killerId = addKiller(connection, dragon.getKiller());
            }
            String query = "INSERT INTO dragon (name, coordinates_id, creation_date, age, speaking, color, character, killer_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dragon.getName());
            preparedStatement.setInt(2, coordinatesId);
            preparedStatement.setTimestamp(3, Timestamp.valueOf(dragon.getCreationDate()));
            preparedStatement.setInt(4, dragon.getAge());

            if (dragon.getSpeaking() != null)
                preparedStatement.setBoolean(5, dragon.getSpeaking());
            else
                preparedStatement.setNull(5, Types.BOOLEAN);

            preparedStatement.setObject(6, dragon.getColor().toString(), Types.OTHER);

            if (dragon.getCharacter() != null)
                preparedStatement.setObject(7, dragon.getCharacter().toString(), Types.OTHER);
            else
                preparedStatement.setNull(7, Types.OTHER);

            if (killerId != null) {
                preparedStatement.setInt(8, killerId);
            } else {
                preparedStatement.setNull(8, Types.INTEGER);
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // commit transaction if everything is okay
                connection.commit();
                return resultSet.getInt(1);
            } else {
                throw new SQLException("Failed to add dragon to database");
            }
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    // rollback transaction if something goes wrong
                    connection.rollback();
                } catch (SQLException ex) {
                    System.err.println("Failed to rollback transaction: " + ex.getMessage());
                }
            }
            System.err.println("Failed to add dragon to database: " + e.getMessage());
            return -1;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.err.println("Failed to close connection: " + ex.getMessage());
                }
            }
        }
    }

    public boolean updateDragon(Dragon dragon) {
        Connection connection = null;
        try {
            connection = connect();
            // start transaction
            connection.setAutoCommit(false);

            String query = "UPDATE dragon SET name = ?, age = ?, speaking = ?, color = ?, character = ?, killer_id = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dragon.getName());
            preparedStatement.setInt(2, dragon.getAge());
            if (dragon.getSpeaking() != null)
                preparedStatement.setBoolean(3, dragon.getSpeaking());
            else
                preparedStatement.setNull(3, Types.BOOLEAN);
            preparedStatement.setObject(4, dragon.getColor().toString(), Types.OTHER);
            if (dragon.getCharacter() != null)
                preparedStatement.setObject(5, dragon.getCharacter().toString(), Types.OTHER);
            else
                preparedStatement.setNull(5, Types.VARCHAR);
            updateCoordinates(connection, dragon.getCoordinates());
            if (dragon.getKiller() != null)
                if (dragon.getKiller().getId() != -1)
                    updateKiller(connection, dragon.getKiller());
                else
                    preparedStatement.setInt(6, addKiller(connection, dragon.getKiller()));
            else
                preparedStatement.setNull(6, Types.INTEGER);

            preparedStatement.setInt(7, dragon.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            connection.commit();
            return rowsAffected > 0;


        } catch (SQLException e) {
            if (connection != null) {
                try {
                    // rollback transaction if something goes wrong
                    connection.rollback();
                } catch (SQLException ex) {
                    System.err.println("Failed to rollback transaction: " + ex.getMessage());
                }
            }
            System.err.println("Failed to update dragon in database: " + e.getMessage());
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.err.println("Failed to close connection: " + ex.getMessage());
                }
            }
        }
    }

    public boolean removeDragon(int dragonId) {
        String deleteDragonSQL = "DELETE FROM dragon WHERE id = ?";
        String deletePersonSQL = "DELETE FROM person WHERE id = (SELECT killer_id FROM dragon WHERE id = ?)";
        String deleteCoordinatesSQL = "DELETE FROM coordinates WHERE id = (SELECT coordinates_id FROM dragon WHERE id = ?)";
        String deleteLocationSQL = "DELETE FROM location WHERE id NOT IN (SELECT location_id FROM person)";

        try (Connection connection = connect()) {
            // Begin transaction
            connection.setAutoCommit(false);

            // Delete the dragon
            try (PreparedStatement pstmt = connection.prepareStatement(deleteDragonSQL)) {
                pstmt.setInt(1, dragonId);
                pstmt.executeUpdate();
            }

            // Delete the person who is the killer of the dragon
            try (PreparedStatement pstmt = connection.prepareStatement(deletePersonSQL)) {
                pstmt.setInt(1, dragonId);
                pstmt.executeUpdate();
            }

            // Delete the coordinates of the dragon
            try (PreparedStatement pstmt = connection.prepareStatement(deleteCoordinatesSQL)) {
                pstmt.setInt(1, dragonId);
                pstmt.executeUpdate();
            }

            // Delete the location that is not referenced by any person
            try (PreparedStatement pstmt = connection.prepareStatement(deleteLocationSQL)) {
                pstmt.executeUpdate();
            }

            // Commit transaction
            connection.commit();
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to remove dragon from database: " + e.getMessage());
            return false;
        }
    }
    private int addLocation(Connection connection, Location location) throws SQLException {
        String query = "INSERT INTO location (x, y, z) VALUES (?, ?, ?) RETURNING id";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setFloat(1, location.getX());
        preparedStatement.setFloat(2, location.getY());
        preparedStatement.setLong(3, location.getZ());

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            throw new SQLException("Failed to add location to database");
        }
    }

    private void updateLocation(Connection connection, Location location) throws SQLException {
        String query = "UPDATE location SET x = ?, y = ?, z = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setFloat(1, location.getX());
        preparedStatement.setFloat(2, location.getY());
        preparedStatement.setLong(3, location.getZ());
        preparedStatement.setInt(4, location.getId());

        preparedStatement.executeUpdate();
    }

    private int addKiller(Connection connection, Person killer) throws SQLException {
        String query = "INSERT INTO person (name, birthday, height, weight, location_id) VALUES (?, ?, ?, ?, ?) RETURNING id";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, killer.getName());

        if (killer.getBirthday() != null)
            preparedStatement.setTimestamp(2, Timestamp.valueOf(killer.getBirthday().toLocalDateTime()));
        else
            preparedStatement.setNull(2, Types.TIMESTAMP);

        if (killer.getHeight() != null)
            preparedStatement.setFloat(3, killer.getHeight());
        else
            preparedStatement.setNull(3, Types.FLOAT);

        if (killer.getWeight() != null)
            preparedStatement.setLong(4, killer.getWeight());
        else
            preparedStatement.setNull(4, Types.BIGINT);

        Location location = killer.getLocation();
        if (location != null)
            preparedStatement.setInt(5, addLocation(connection, location));
        else
            preparedStatement.setNull(5, Types.INTEGER);

        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        } else {
            throw new SQLException("Failed to add killer to database");
        }
    }

    private void updateKiller(Connection connection, Person killer) throws SQLException {
        updateLocation(connection, killer.getLocation());

        String query = "UPDATE person SET name = ?, birthday = ?, height = ?, weight = ? WHERE id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, killer.getName());

        if (killer.getBirthday() != null)
            preparedStatement.setTimestamp(2, Timestamp.valueOf(killer.getBirthday().toLocalDateTime()));
        else
            preparedStatement.setNull(2, Types.TIMESTAMP);

        if (killer.getHeight() != null)
            preparedStatement.setFloat(3, killer.getHeight());
        else
            preparedStatement.setNull(3, Types.FLOAT);

        if (killer.getWeight() != null)
            preparedStatement.setLong(4, killer.getWeight());
        else
            preparedStatement.setNull(4, Types.BIGINT);

        preparedStatement.setInt(5, killer.getId());

        preparedStatement.executeUpdate();
    }
    private String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[10];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    private String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-384");
            md.update(salt.getBytes());
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addUser(User user) {
        String salt = generateSalt();
        String hashedPassword = hashPassword(user.getPassword(), salt);

        String query = "INSERT INTO users (name, password, salt) VALUES (?, ?, ?)";
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.setString(3, salt);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Failed to add user to database: " + e.getMessage());
            return false;
        }
    }

    public User getUser(User user) {
        String query = "SELECT * FROM users WHERE name = ?";
        try (Connection connection = connect()) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                String salt = resultSet.getString("salt");
                String hashedPassword = hashPassword(user.getPassword(), salt);

                if (storedPassword.equals(hashedPassword)) {
                    return new User(resultSet.getString("name"),
                            resultSet.getString("password"));
                }
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Failed to get user from database: " + e.getMessage());
            return null;
        }
    }



}