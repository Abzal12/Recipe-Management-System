package recipes;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.recipe.Recipe;
import recipes.recipe.RecipeRepo;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@RestController
public class RecipesController {

    private final RecipeRepo recipeRepo;
    Recipe recipe;

    @PostMapping("/api/recipe/new")
    public ResponseEntity<?> giveRecipe(@Valid @RequestBody Recipe recipeArg) {

        recipe = new Recipe(recipeArg.getName(), recipeArg.getDescription(),
                recipeArg.getIngredients(), recipeArg.getDirections(),
                recipeArg.getCategory());
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

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<?> updateById(@PathVariable long id,
                                        @Valid @RequestBody Recipe recipeArg) {
        if (!recipeRepo.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Recipe recipe1 = recipeRepo.findById(id).get();
        recipe1.setName(recipeArg.getName());
        recipe1.setCategory(recipeArg.getCategory());
        recipe1.setDate(LocalDateTime.now());
        recipe1.setDescription(recipeArg.getDescription());
        recipe1.setIngredients(recipeArg.getIngredients());
        recipe1.setDirections(recipeArg.getDirections());
        recipeRepo.save(recipe1);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/recipe/search")
    public ResponseEntity<?> searchByCategoryOrByName(@RequestParam Map<String, String> requestParam) {
        if (requestParam.size() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (requestParam.containsKey("category")) {
            Iterable<Recipe> specificRecipes = recipeRepo.findAllByCategory(
                    requestParam.get("category").toLowerCase(Locale.ENGLISH),
                    Sort.by("date").descending()
            );
            return new ResponseEntity<>(specificRecipes, HttpStatus.OK);
        } else if (requestParam.containsKey("name")) {
            Iterable<Recipe> specificRecipes = recipeRepo.findAllByNameContainsIgnoreCase(
                    requestParam.get("name").toLowerCase(Locale.ENGLISH),
                    Sort.by("date").descending()
            );
            return new ResponseEntity<>(specificRecipes, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
