package recipes;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.recipe.Recipe;
import recipes.recipe.RecipeRepo;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class RecipesController {

    private final RecipeRepo recipeRepo;
    Recipe recipe;

    @PostMapping("/api/recipe/new")
    public ResponseEntity<?> giveRecipe(@Valid @RequestBody Recipe recipeArg) {

        recipe = new Recipe(recipeArg.getName(), recipeArg.getDescription(),
                recipeArg.getIngredients(), recipeArg.getDirections());
        recipeRepo.save(recipe);

        HashMap<String, Long> map = new HashMap<>();
        map.put("id", recipe.getId());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<?> getRecipe(@PathVariable long id) {
        Optional<Recipe> recipeFromDB = recipeRepo.findById(id);
        if (recipeFromDB.isPresent()) {
            return new ResponseEntity<>(recipeFromDB, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@PathVariable long id) {
        if (!recipeRepo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        recipeRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
