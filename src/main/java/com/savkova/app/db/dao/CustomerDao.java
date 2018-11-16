package com.savkova.app.db.dao;

import com.savkova.app.db.DbInitUtil;
import com.savkova.app.db.entities.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.savkova.app.db.util.Constants.Loggers.LOGGER;
import static com.savkova.app.db.util.Constants.DbTable;

public class CustomerDao implements Dao<Customer> {
    @Override
    public void add(Customer customer) {
        PreparedStatement ps = null;
        try {
            ps = DbInitUtil.getConn().prepareStatement(
                    "INSERT into " + DbTable.CUSTOMERS + "(first_name, last_name, phone, email) values (?,?,?,?)");

            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getEmail());
            ps.executeUpdate();
            LOGGER.info("Added " + customer);
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
    public Customer get(int id) {
        Customer customer = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = DbInitUtil.getConn().prepareStatement("SELECT * FROM orders.customers WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();
            customer = new Customer(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone"),
                    rs.getString("email"));
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
    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = DbInitUtil.getConn().prepareStatement("SELECT * FROM orders.customers");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone"),
                        rs.getString("email")));
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
    public void update(Customer customer) {
        PreparedStatement ps = null;
        try {
            ps = DbInitUtil.getConn().prepareStatement("UPDATE " + DbTable.CUSTOMERS
                    + " SET first_name = ?, last_name = ?, phone = ?, email = ? WHERE id = ?");

            ps.setString(1, customer.getFirstName());
            ps.setString(2, customer.getLastName());
            ps.setString(3, customer.getPhone());
            ps.setString(4, customer.getEmail());
            ps.setInt(5, customer.getId());
            ps.executeUpdate();
            LOGGER.info("Customer (id=" + customer.getId() + ") changed");
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
            ps = DbInitUtil.getConn().prepareStatement("DELETE FROM orders.customers WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            LOGGER.info("Customer (id=" + id + ") deleted");
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
