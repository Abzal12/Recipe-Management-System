package recipes;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import recipes.recipe.Recipe;
import recipes.recipe.RecipeRepo;
import recipes.recipe.RecipeService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;
@RestController
@Validated
@RequestMapping(value = "api/recipe/")
public class RecipesController {

    private final RecipeService recipeService;

    public RecipesController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("new")
    public Map<String, Long> addRecipe(@Valid @RequestBody Recipe recipe) {
        return Map.of("id", recipeService.add(recipe));
    }

    @GetMapping("{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable long id) {
        Optional<Recipe> recipe = recipeService.findById(id);
        return ResponseEntity.of(recipe);
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteRecipe(@PathVariable long id) {
        boolean status = recipeService.deleteById(id);
        return status ?
                ResponseEntity.status(204).build() :
                ResponseEntity.status(404).build();
    }

    @PutMapping("{id}")
    public ResponseEntity updateRecipe(@PathVariable long id,
                                       @Valid @RequestBody Recipe recipe) {
        boolean status = recipeService.updateById(id, recipe);
        return status ?
                ResponseEntity.status(204).build() :
                ResponseEntity.status(404).build();
    }

    @GetMapping("search")
    public ResponseEntity<?> searchByCategoryOrByName(@RequestParam Map<String, String> requestParam) {
        if (requestParam.size() != 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (requestParam.containsKey("category")) {
            Iterable<Recipe> specificRecipes = recipeService.findByCategory(
                    requestParam.get("category").toLowerCase(Locale.ENGLISH)
            );
            return new ResponseEntity<>(specificRecipes, HttpStatus.OK);
        } else if (requestParam.containsKey("name")) {
            Iterable<Recipe> specificRecipes = recipeService.findByName(
                    requestParam.get("name").toLowerCase(Locale.ENGLISH)
            );
            return new ResponseEntity<>(specificRecipes, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
