package recipes;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.recipe.Recipe;
import recipes.recipe.RecipeRepo;
import recipes.recipe.RecipeService;
import recipes.user.User;
import recipes.user.UserRepo;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@ComponentScan
@RequestMapping(value = "api/")
public class RecipesController {

    @Autowired
    private final RecipeService recipeService;
    private final UserRepo userRepo;
    private final RecipeRepo recipeRepo;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("recipe/new")
    public ResponseEntity<?> addRecipe(@Valid @RequestBody Recipe recipe,
                                       @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(403).build();
        }
        recipe.setUser(userRepo.findUserByEmail(userDetails.getUsername()).get());
        Recipe recipeOption = recipeRepo.save(recipe);
        return new ResponseEntity<>(Map.of("id", recipeOption.getId()), HttpStatus.OK);
    }

    @GetMapping("recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable long id) {
        Optional<Recipe> recipe = recipeService.findById(id);
        return ResponseEntity.of(recipe);
    }

    @DeleteMapping("recipe/{id}")
    public ResponseEntity<?> deleteRecipe(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {


        Optional<Recipe> recipeObj = recipeRepo.findById(id);

        if (recipeObj.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (recipeObj.get().getUser().getEmail().equals(userDetails.getUsername())) {
            recipeRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (!recipeObj.get().getUser().getEmail().equals(userDetails.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("recipe/{id}")
    public ResponseEntity updateRecipe(@PathVariable long id,
                                       @Valid @RequestBody Recipe recipe,
                                       @AuthenticationPrincipal UserDetails userDetails) {

        Optional<Recipe> recipeObj = recipeRepo.findById(id);

        if (recipeObj.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (recipeObj.get().getUser().getEmail().equals(userDetails.getUsername())) {

                Recipe oldRecipe = recipeObj.get();
                oldRecipe.copyOf(recipe);
                recipeRepo.save(oldRecipe);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else if (!recipeObj.get().getUser().getEmail().equals(userDetails.getUsername())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
//
    @GetMapping("recipe/search")
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

    @PostMapping("register")
    public ResponseEntity<?> registration(@RequestBody @Valid User user) {

        if (userRepo.existsUserByEmail(user.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
