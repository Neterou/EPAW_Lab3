package epaw.lab3.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import epaw.lab3.model.User;

public class UserRepository extends BaseRepository {

    private static UserRepository instance;

    private UserRepository() {
        super();
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public boolean existsByUsername(String username) {
        String query = "SELECT COUNT(*) FROM users WHERE LOWER(username) = LOWER(?)";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsByEmail(String email) {
        String query = "SELECT COUNT(*) FROM users WHERE LOWER(email) = LOWER(?)";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsByDni(String dni) {
        String query = "SELECT COUNT(*) FROM users WHERE dni = ?";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, dni.toUpperCase());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int findBubbleId(String zip, String city, String country) {
        String query = "SELECT id FROM bubbles WHERE zip = ? AND LOWER(city) = LOWER(?) AND UPPER(country) = UPPER(?)";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, zip);
            statement.setString(2, city);
            statement.setString(3, country);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) return rs.getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean isBubbleOpen(int bubbleId) {
        String query = "SELECT open FROM bubbles WHERE id = ?";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setInt(1, bubbleId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) return rs.getInt("open") == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkLogin(User user) {
        String query = "SELECT id, name, email, username, picture, status, role, bubble_id FROM users WHERE LOWER(username) = LOWER(?) AND password = ?";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    user.setId(rs.getInt("id"));
                    user.setName(rs.getString("name"));
                    user.setEmail(rs.getString("email"));
                    user.setUsername(rs.getString("username"));
                    user.setPicture(rs.getString("picture"));
                    user.setStatus(rs.getString("status"));
                    user.setRole(rs.getString("role"));
                    user.setBubbleId(rs.getObject("bubble_id") != null ? rs.getInt("bubble_id") : null);
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void save(User user) {
        String query = "INSERT INTO users (name, email, phone, phone_country, dni, zip, city, country, gender, password, username, picture, status, role, bubble_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPhone());
            statement.setString(4, user.getPhoneCountry());
            statement.setString(5, user.getDni() != null ? user.getDni().toUpperCase() : null);
            statement.setString(6, user.getZip());
            statement.setString(7, user.getCity());
            statement.setString(8, user.getCountry() != null ? user.getCountry().toUpperCase() : null);
            statement.setString(9, user.getGender());
            statement.setString(10, user.getPassword());
            statement.setString(11, user.getUsername());
            statement.setString(12, user.getPicture());
            statement.setString(13, user.getStatus());
            statement.setString(14, user.getRole() != null ? user.getRole() : "USER");
            if (user.getBubbleId() != null) {
                statement.setInt(15, user.getBubbleId());
            } else {
                statement.setNull(15, java.sql.Types.INTEGER);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<User> findByUsername(String username) {
        String query = "SELECT id, name, email, username, picture, status, role, bubble_id FROM users WHERE LOWER(username) = LOWER(?)";
        try (PreparedStatement statement = db.prepareStatement(query)) {
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPicture(rs.getString("picture"));
                user.setStatus(rs.getString("status"));
                user.setRole(rs.getString("role"));
                user.setBubbleId(rs.getObject("bubble_id") != null ? rs.getInt("bubble_id") : null);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
