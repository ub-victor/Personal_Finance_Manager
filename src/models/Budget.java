package models;

import java.sql.Date;

public class Budget {
    private int id;
    private int userId;
    private int categoryId;
    private Date monthYear;
    private double amount;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public Date getMonthYear() { return monthYear; }
    public void setMonthYear(Date monthYear) { this.monthYear = monthYear; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
}