package tacos.web;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import tacos.Ingredient;

import java.util.HashMap;
import java.util.Map;

import static tacos.Ingredient.Type.*;

@Component
public class IngredientByIdConverter implements Converter<String, Ingredient> {

    private final Map<String, Ingredient> ingredients = new HashMap<>();

    public IngredientByIdConverter() {
        ingredients.put("FLTO", new Ingredient("FLTO", "Flour Tortilla", WRAP));
        ingredients.put("COTO", new Ingredient("COTO", "Corn Tortilla", WRAP));
        ingredients.put("GRBF", new Ingredient("GRBF", "Ground Beef", PROTEIN));
        ingredients.put("CARN", new Ingredient("CARN", "Carnitas", PROTEIN));
        ingredients.put("TMTO", new Ingredient("TMTO", "Diced Tomatoes", VEGGIES));
        ingredients.put("LETC", new Ingredient("LETC", "Lettuce", VEGGIES));
        ingredients.put("CHED", new Ingredient("CHED", "Cheddar", CHEESE));
        ingredients.put("JACK", new Ingredient("JECK", "Monterey Jack", CHEESE));
        ingredients.put("SLSA", new Ingredient("SLSA", "Salsa", SAUCE));
        ingredients.put("SRCR", new Ingredient("SRCR", "Sour Cream", SAUCE));
    }

    @Override
    public Ingredient convert(String id) {
      return  ingredients.get(id);
    }
}
