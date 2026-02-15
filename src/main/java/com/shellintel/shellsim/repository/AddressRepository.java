package com.shellintel.shellsim.repository;


import com.shellintel.shellsim.model.Address;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AddressRepository {

    private final JdbcTemplate jdbcTemplate;

    public AddressRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Insert Address
    public long save(Address address) {
        String sql = """
                INSERT INTO core.address (street, city, country, postal_code)
                VALUES (?, ?, ?, ?)
                RETURNING id
                """;

        return jdbcTemplate.queryForObject(sql,long.class,
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getPostalCode()
        );

    }

    // Fetch all
    public List<Address> findAll() {
        String sql = "SELECT * FROM core.address";
        return jdbcTemplate.query(sql, new AddressRowMapper());
    }

    public void linkCompanyAddress(Long companyId, Long addressId) {

        String sql = "INSERT INTO core.company_address (company_id, address_id) VALUES (?, ?)";

        jdbcTemplate.update(sql, companyId, addressId);
    }
    public Address findByCompanyId(Long companyId) {

        String sql = """
                SELECT a.id, a.street, a.city, a.country, a.postal_code
                FROM core.address a
                JOIN core.company_address ca
                  ON a.id = ca.address_id
                WHERE ca.company_id = ?
                """;

        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{companyId},
                (rs, rowNum) -> {
                    Address address = new Address();
                    address.setId(rs.getLong("id"));
                    address.setStreet(rs.getString("street"));
                    address.setCity(rs.getString("city"));
                    address.setCountry(rs.getString("country"));
                    address.setPostalCode(rs.getString("postal_code"));
                    return address;
                }
        );
    }

    // RowMapper
    private static class AddressRowMapper implements RowMapper<Address> {
        @Override
        public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
            Address address = new Address();
            address.setId(rs.getLong("id"));
            address.setStreet(rs.getString("street"));
            address.setCity(rs.getString("city"));
            address.setCountry(rs.getString("country"));
            address.setPostalCode(rs.getString("postal_code"));
            return address;
        }



    }
}

