/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.util.ArrayList;
import java.util.List;
import model.Room;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 *
 * @author ADMIN
 */
public class RoomDAO extends DBContext {

    private static final Logger logger = Logger.getLogger(RoomDAO.class.getName());

    // Phương thức thêm một phòng mới vào cơ sở dữ liệu
    public boolean addRoom(Room room) {
        try (Connection connection = DBContext.getConnection(); PreparedStatement stm = connection.prepareStatement("INSERT INTO Room (id, name, status) VALUES (?, ?, ?)")) {

            stm.setInt(1, room.getId());
            stm.setString(2, room.getName());
            stm.setString(3, room.getStatus());

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Room successfully added.");
                return true;
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return false;
    }

    // Phương thức cập nhật thông tin một phòng trong cơ sở dữ liệu
    public boolean updateRoom(Room room) {
        String sql = "UPDATE Room SET name = ?, status = ? WHERE id = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement stm = connection.prepareStatement(sql)) {

            stm.setString(1, room.getName());
            stm.setString(2, room.getStatus());
            stm.setInt(3, room.getId());

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Room successfully updated.");
                return true;
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return false;
    }

    // Phương thức xóa một phòng khỏi cơ sở dữ liệu
    public boolean deleteRoom(int id) {
        String sql = "DELETE FROM Room WHERE id = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement stm = connection.prepareStatement(sql)) {

            stm.setInt(1, id);

            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                logger.info("Room successfully deleted.");
                return true;
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return false;
    }

    // Phương thức tìm một phòng theo ID
    public Room findRoomById(int id) {
        String sql = "SELECT * FROM Room WHERE id = ?";
        try (Connection connection = DBContext.getConnection(); PreparedStatement stm = connection.prepareStatement(sql)) {

            stm.setInt(1, id);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return new Room(rs.getInt("id"), rs.getString("name"), rs.getString("status"));
                }
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return null;
    }

    // Phương thức lấy tất cả các phòng từ cơ sở dữ liệu
    public List<Room> findAllRooms() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM Room";
        try (Connection connection = DBContext.getConnection(); PreparedStatement stm = connection.prepareStatement(sql); ResultSet rs = stm.executeQuery()) {

            while (rs.next()) {
                rooms.add(new Room(rs.getInt("id"), rs.getString("name"), rs.getString("status")));
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return rooms;
    }
}
