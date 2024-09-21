package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private final DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM tests.product";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product product = new Product(rs.getLong(1), rs.getString(2), rs.getDouble(3));
                products.add(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM tests.product WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Product product = new Product(rs.getLong(1), rs.getString(2), rs.getDouble(3));
                return Optional.of(product);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE tests.product SET name = ?, price = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setLong(3, product.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void save(Product product) {
        String sql = "INSERT INTO tests.product VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, product.getId());
            ps.setString(2, product.getName());
            ps.setDouble(3, product.getPrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM tests.product WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
