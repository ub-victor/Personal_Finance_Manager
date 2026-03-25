package dao;

import models.Transaction;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    // Add transaction
    public boolean addTransaction(Transaction t) {
        String sql = "INSERT INTO transactions (user_id, category_id, amount, date, description, type) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, t.getUserId());
            pstmt.setInt(2, t.getCategoryId());
            pstmt.setDouble(3, t.getAmount());
            pstmt.setDate(4, t.getDate());
            pstmt.setString(5, t.getDescription());
            pstmt.setString(6, t.getType());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get transactions for a specific user
    public List<Transaction> getTransactionsByUser(int userId) {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE user_id = ? ORDER BY date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("id"));
                t.setUserId(rs.getInt("user_id"));
                t.setCategoryId(rs.getInt("category_id"));
                t.setAmount(rs.getDouble("amount"));
                t.setDate(rs.getDate("date"));
                t.setDescription(rs.getString("description"));
                t.setType(rs.getString("type"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get all transactions (admin only)
    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY date DESC";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getInt("id"));
                t.setUserId(rs.getInt("user_id"));
                t.setCategoryId(rs.getInt("category_id"));
                t.setAmount(rs.getDouble("amount"));
                t.setDate(rs.getDate("date"));
                t.setDescription(rs.getString("description"));
                t.setType(rs.getString("type"));
                list.add(t);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Update transaction
    public boolean updateTransaction(Transaction t) {
        String sql = "UPDATE transactions SET category_id=?, amount=?, date=?, description=?, type=? WHERE id=? AND user_id=?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, t.getCategoryId());
            pstmt.setDouble(2, t.getAmount());
            pstmt.setDate(3, t.getDate());
            pstmt.setString(4, t.getDescription());
            pstmt.setString(5, t.getType());
            pstmt.setInt(6, t.getId());
            pstmt.setInt(7, t.getUserId());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete transaction
    public boolean deleteTransaction(int transactionId, int userId) {
        String sql = "DELETE FROM transactions WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Admin delete any transaction
    public boolean adminDeleteTransaction(int transactionId) {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, transactionId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}