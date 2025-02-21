package org.playerapi;

import com.github.javafaker.Faker;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;
import java.util.List;

@Testcontainers
public abstract class AbstractTestcontainers {

    @BeforeAll
    static void beforeAll() {
        Flyway flyway = Flyway.configure().dataSource(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword()).load();
        flyway.migrate();
        printDatabaseSchema();

    }


    @Container
    protected final static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withUsername("test")
            .withPassword("test")
            .withDatabaseName("test");

    @DynamicPropertySource
    protected static void registerDataSourceProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password",postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    private static DataSource getDataSource(){
        return DataSourceBuilder.create().driverClassName(postgreSQLContainer.getDriverClassName())
                .url(postgreSQLContainer.getJdbcUrl())
                .username(postgreSQLContainer.getUsername())
                .password(postgreSQLContainer.getPassword())
                .build();
    }

    protected static JdbcTemplate getJdbcTemplate(){
        return new JdbcTemplate(getDataSource());
    }

    protected static final Faker faker = new Faker();

    public static void printDatabaseSchema() {
        // Query to fetch the table names and their columns from the database
        String query = "SELECT table_name, column_name, data_type " +
                "FROM information_schema.columns " +
                "WHERE table_schema = 'public'"; // You can also filter by 'public' schema in PostgreSQL

        // Execute the query and get the results
        List<ColumnInfo> columns = getJdbcTemplate().query(query, new RowMapper<ColumnInfo>() {
            @Override
            public ColumnInfo mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
                return new ColumnInfo(rs.getString("table_name"),
                        rs.getString("column_name"),
                        rs.getString("data_type"));
            }
        });

        // Print the results
        columns.forEach(column ->
                System.out.println("Table: " + column.tableName() + ", Column: " + column.columnName() + ", Type: " + column.dataType()));
    }

    public record ColumnInfo(String tableName, String columnName, String dataType) {
    }

}
