package com.savkova.app.db.dao;

import com.savkova.app.db.DbInitUtil;
import com.savkova.app.db.entities.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.savkova.app.db.util.Constants.Loggers.LOGGER;
import static com.savkova.app.db.util.Constants.DbTable;

public class ProductDao implements Dao<Product> {
    @Override
    public void add(Product product) {
        PreparedStatement ps = null;
        try {
            ps = DbInitUtil.getConn().prepareStatement(
                    "INSERT INTO " + DbTable.PRODUCTS + "(product_id, product_name, description) values (?,?,?)");

            ps.setInt(1, product.getVendorCode());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.executeUpdate();
            LOGGER.info("Added " + product);
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
    public Product get(int id) {
        Product product = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = DbInitUtil.getConn().prepareStatement("SELECT * FROM orders.products WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();
            rs.next();
            product = new Product(
                    rs.getInt("id"),
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    rs.getString("description"));
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
        return product;
    }

    @Override
    public List<Product> getAll() {
        List<Product> list = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = DbInitUtil.getConn().prepareStatement("SELECT * FROM orders.products");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        rs.getString("description")));
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
    public void update(Product product) {
        PreparedStatement ps = null;
        try {
            ps = DbInitUtil.getConn().prepareStatement("UPDATE " + DbTable.PRODUCTS
                    + " SET product_id = ?, product_name = ?, description = ? WHERE id = ?");

            ps.setInt(1, product.getVendorCode());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.setInt(4, product.getId());
            ps.executeUpdate();
            LOGGER.info("Product (id=" + product.getId() + ") changed");
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
            ps = DbInitUtil.getConn().prepareStatement("DELETE FROM orders.products WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            LOGGER.info("Product (id=" + id + ") deleted");
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