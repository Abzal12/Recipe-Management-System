package recipes.recipe;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final RecipeRepo recipeRepo;


    public RecipeService(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    public Optional<Recipe> findById(long id) {
        return recipeRepo.findById(id);
    }

    public long add(Recipe recipe) {
        return recipeRepo.save(recipe).getId();
    }

    public boolean deleteById(long id) {
        if (recipeRepo.existsById(id)) {
            recipeRepo.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean updateById(long id, Recipe recipe) {
        Optional<Recipe> optional = recipeRepo.findById(id);
        if (optional.isPresent()) {
            Recipe oldRecipe = optional.get();
            oldRecipe.copyOf(recipe);
            recipeRepo.save(oldRecipe);
            return true;
        } else {
            return false;
        }
    }

    public List<Recipe> findByCategory(String category) {
        return recipeRepo.findAllByCategoryIgnoreCaseOrderByDateDesc(category);
    }

    public List<Recipe> findByName(String name) {
        return recipeRepo.findAllByNameContainsIgnoreCaseOrderByDateDesc(name);
    }
}
