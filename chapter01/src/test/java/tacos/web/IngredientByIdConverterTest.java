package tacos.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tacos.Ingredient;
import tacos.data.IngredientRepository;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IngredientByIdConverterTest {

    private IngredientByIdConverter converter;

    @BeforeEach
    void setup() {
        IngredientRepository ingredientRepo = mock(IngredientRepository.class);

        when(ingredientRepo.findById("AAAA"))
                .thenReturn(Optional.of(
                        new Ingredient("AAAA", "TEST INGREDIENT", Ingredient.Type.CHEESE)));

        when(ingredientRepo.findById("ZZZZ"))
                .thenReturn(Optional.empty());

        converter = new IngredientByIdConverter(ingredientRepo);
    }

    @Test
    void returnsIngredient() {
        assertThat(converter.convert("AAAA"))
                .isEqualTo(new Ingredient("AAAA", "TEST INGREDIENT", Ingredient.Type.CHEESE));
    }

    @Test
    void reportsEmptyIngredient() {
        assertThat(converter.convert("ZZZZ"))
                .isNull();
    }
}
