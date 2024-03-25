package recipes.recipe;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepo extends CrudRepository<Recipe, Long>, PagingAndSortingRepository<Recipe, Long> {
    Iterable<Recipe> findAllByCategory(String category, Sort sort);
    Iterable<Recipe> findAllByNameContainsIgnoreCase(String name, Sort sort);
}
