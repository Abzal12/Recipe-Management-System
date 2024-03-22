package recipes;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.recipe.Recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class RecipesController {

    List<Recipe> recipeList = new ArrayList<>();
    Recipe recipe;


    @PostMapping("/api/recipe/new")
    public ResponseEntity<?> giveRecipe(@RequestBody Recipe recipeArg) {

        recipe = new Recipe(recipeArg.getName(), recipeArg.getDescription(),
                recipeArg.getIngredients(), recipeArg.getDirections());
        recipeList.add(recipe);

        HashMap<String, Long> map = new HashMap<>();
        map.put("id", recipe.getId());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable long id) {

        try {
            return new ResponseEntity<>(recipeList.get((int) id - 1), HttpStatus.OK);
        } catch (IndexOutOfBoundsException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
