package tacos.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tacos.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Ingredient> findAll() {
        return jdbcTemplate.query("Select id, name, type from Ingredient",
                this::mapRowToIngredient);
    }

    private Ingredient mapRowToIngredient(ResultSet row, int numRow) throws SQLException {
        return new Ingredient(
                row.getString("id"),
                row.getString("name"),
                Ingredient.Type.valueOf(row.getString("type")));
    }

    @Override
    public Optional<Ingredient> findById(String id) {

        List<Ingredient> ingredients = jdbcTemplate.query(
                "Select id, name, type from Ingredient where id=?",
                this::mapRowToIngredient,
                id);

        return ingredients.isEmpty() ?
                Optional.empty() :
                Optional.of(ingredients.get(0));
    }

    @Override
    public Ingredient save(Ingredient ingredient) {

        jdbcTemplate.update(
                "Insert into Ingredient (id, name, type) " +
                        "values (?,?,?)",
                ingredient.getId(),
                ingredient.getName(),
                ingredient.getType().toString()
        );

        return ingredient;
    }
}
