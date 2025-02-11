package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tacos.Ingredient;
import tacos.Taco;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static tacos.Ingredient.Type.*;
import static tacos.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    @ModelAttribute
    private void addIngredientsToModel(Model model) {

        List<Ingredient> ingredients = Arrays.asList(
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

        for (Type type : Type.values()) {

            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(type, ingredients));
        }
    }

    private Iterable<Ingredient> filterByType(Type type, List<Ingredient> ingredients) {

        return ingredients.stream()
                .filter(i -> i.getType().equals(type))
                .collect(Collectors.toList());
    }

    @GetMapping
    public String showDesignForm(Model model) {
        model.addAttribute("taco", new Taco());

        return "design";
    }

    @PostMapping
    public String processTaco(Taco taco) {
        //save taco
        log.info("Processing taco {}", taco);

        return "redirect:/orders/current";
    }
}
