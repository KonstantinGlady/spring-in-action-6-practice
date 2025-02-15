package tacos.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import tacos.Ingredient;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
public class IngredientRepositoryTest {

    @Autowired
    private IngredientRepository ingredientRepo;

    @Test
    void findById() {
        Optional<Ingredient> flto = ingredientRepo.findById("FLTO");
        assertThat(flto.isPresent()).isTrue();
        assertThat(flto.get()).isEqualTo(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));

        Optional<Ingredient> xxxx = ingredientRepo.findById("XXXX");
        assertThat(xxxx.isEmpty()).isTrue();
    }
}
