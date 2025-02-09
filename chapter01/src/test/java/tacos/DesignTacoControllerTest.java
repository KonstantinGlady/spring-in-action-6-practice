package tacos;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tacos.web.DesignTacoController;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static tacos.Ingredient.Type.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DesignTacoController.class)
class DesignTacoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private static List<Ingredient> ingredients;

    @BeforeAll
    static void setup() {
        ingredients = Arrays.asList(
                new Ingredient("FLTO", "Flour Tortilla", WRAP),
                new Ingredient("COTO", "Corn Tortilla", WRAP),
                new Ingredient("GRBF", "Ground Beef", PROTEIN),
                new Ingredient("CARN", "Carnitas", PROTEIN),
                new Ingredient("TMTO", "Diced Tomatoes", VEGGIES),
                new Ingredient("LETC", "Lettuce", VEGGIES),
                new Ingredient("CHED", "Cheddar", CHEESE),
                new Ingredient("JACK", "Monterey Jack", CHEESE),
                new Ingredient("SLSA", "Salsa", SAUCE),
                new Ingredient("SRCR", "Sour Cream", SAUCE)
        );
    }

    @Test
    void showDesignForm() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/design"))
                .andExpect(status().isOk())
                .andExpect(view().name("design"))
                .andExpect(model().attribute("wrap", ingredients.subList(0, 2)))
                .andExpect(model().attribute("protein", ingredients.subList(2, 4)))
                .andExpect(model().attribute("veggies", ingredients.subList(4, 6)))
                .andExpect(model().attribute("cheese", ingredients.subList(6, 8)))
                .andExpect(model().attribute("sauce", ingredients.subList(8, 10)));
    }
}