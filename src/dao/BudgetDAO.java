package dao;

import models.Budget;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAO {

    // Add or update budget
    public boolean setBudget(Budget budget) {
        // Use REPLACE or INSERT ... ON DUPLICATE KEY UPDATE
        String sql = "INSERT INTO budgets (user_id, category_id, month_year, amount) VALUES (?, ?, ?, ?) " +
                     "ON DUPLICATE KEY UPDATE amount = VALUES(amount)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, budget.getUserId());
            pstmt.setInt(2, budget.getCategoryId());
            pstmt.setDate(3, budget.getMonthYear());
            pstmt.setDouble(4, budget.getAmount());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get budgets for a user in a specific month
    public List<Budget> getBudgetsForUserMonth(int userId, Date monthYear) {
        List<Budget> list = new ArrayList<>();
        String sql = "SELECT * FROM budgets WHERE user_id = ? AND month_year = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setDate(2, monthYear);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Budget b = new Budget();
                b.setId(rs.getInt("id"));
                b.setUserId(rs.getInt("user_id"));
                b.setCategoryId(rs.getInt("category_id"));
                b.setMonthYear(rs.getDate("month_year"));
                b.setAmount(rs.getDouble("amount"));
                list.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Delete a budget
    public boolean deleteBudget(int budgetId, int userId) {
        String sql = "DELETE FROM budgets WHERE id = ? AND user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, budgetId);
            pstmt.setInt(2, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}