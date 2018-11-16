package com.savkova.app.db.dao;

import com.savkova.app.db.DbInitUtil;
import com.savkova.app.db.entities.Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.savkova.app.db.util.Constants.Loggers.LOGGER;
import static com.savkova.app.db.util.Constants.DbTable;

public class OrderDao implements Dao<Order> {
    @Override
    public void add(Order order) {
        PreparedStatement ps = null;
        try {
            ps = DbInitUtil.getConn().prepareStatement("INSERT INTO " + DbTable.ORDERS
                    + "(customer_id, product_id, order_date, contract_number, quantity, price, status)"
                    + "VALUES (?,?,?,?,?,?,?)");

            ps.setInt(1, order.getCustomerId());
            ps.setInt(2, order.getProductId());
            ps.setDate(3, order.getOrderDate());
            ps.setInt(4, order.getContractNumber());
            ps.setInt(5, order.getQuantity());
            ps.setDouble(6, order.getPrice());
            ps.setString(7, order.getStatus());
            ps.executeUpdate();
            LOGGER.info("Added " + order);
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Order get(int id) {
        Order customer = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = DbInitUtil.getConn().prepareStatement("SELECT * FROM orders.orders WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();
            customer = new Order(
                    rs.getInt("id"),
                    rs.getInt("contract_number"),
                    rs.getInt("product_id"),
                    rs.getInt("customer_id"),
                    rs.getDate("order_date"),
                    rs.getInt("quantity"),
                    rs.getDouble("price"),
                    rs.getString("status"));
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();

                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return customer;
    }

    @Override
    public List<Order> getAll() {
        List<Order> list = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = DbInitUtil.getConn().prepareStatement("SELECT * FROM orders.orders");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("contract_number"),
                        rs.getInt("product_id"),
                        rs.getInt("customer_id"),
                        rs.getDate("order_date"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (rs != null)
                    rs.close();

                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    @Override
    public void update(Order order) {
        PreparedStatement ps = null;
        try {
            ps = DbInitUtil.getConn().prepareStatement("UPDATE " + DbTable.ORDERS
                    + " SET customer_id = ?, product_id = ?, order_date = ?, "
                    + "contract_number = ?, quantity = ?, price = ?, status = ? WHERE id = ?");

            ps.setInt(1, order.getCustomerId());
            ps.setInt(2, order.getProductId());
            ps.setDate(3, order.getOrderDate());
            ps.setInt(4, order.getContractNumber());
            ps.setInt(5, order.getQuantity());
            ps.setDouble(6, order.getPrice());
            ps.setString(7, order.getStatus());
            ps.setInt(8, order.getId());
            ps.executeUpdate();
            LOGGER.info("Order (id=" + order.getId() + ") changed");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void delete(int id) {
        PreparedStatement ps = null;
        try {
            ps = DbInitUtil.getConn().prepareStatement("DELETE FROM orders.orders WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            LOGGER.info("Order (id=" + id + ") deleted");
        } catch (SQLException e) {
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                if (ps != null)
                    ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
