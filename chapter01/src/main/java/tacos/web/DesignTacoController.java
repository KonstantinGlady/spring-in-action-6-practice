package tacos.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import tacos.Ingredient;
import tacos.Taco;
import tacos.TacoOrder;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static tacos.Ingredient.Type;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientsRepo;
    private final TacoRepository tacoRepo;

    public DesignTacoController(IngredientRepository ingredientsRepo,
                                TacoRepository tacoRepo) {

        this.ingredientsRepo = ingredientsRepo;
        this.tacoRepo = tacoRepo;
    }


    @ModelAttribute
    public void addIngredientsToModel(Model model) {

        List<Ingredient> ingredients = new ArrayList<>();
        ingredientsRepo.findAll().forEach(ingredients::add);

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

    @ModelAttribute("taco")
    public Taco taco() {
        return new Taco();
    }

    @ModelAttribute("tacoOrder")
    public TacoOrder order() {
        return new TacoOrder();
    }

    @GetMapping
    public String showDesignForm() {

        return "design";
    }

    @PostMapping
    public String processTaco(@Valid Taco taco, Errors errors,
                              @ModelAttribute TacoOrder tacoOrder) {

        log.info("Saving taco {}", taco);

        if (errors.hasErrors()) {
            return "design";
        }

        Taco savedTaco = tacoRepo.save(taco);
        tacoOrder.addTaco(savedTaco);


        return "redirect:/orders/current";
    }
}
