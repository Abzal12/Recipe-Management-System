package recipes.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data
public class Recipe {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Setter(AccessLevel.NONE)
    private long id;

    @NotBlank(message = "name cannot be blank")
    private String name;

    @NotBlank(message = "category cannot be blank")
    private String category;

    @UpdateTimestamp
    private LocalDateTime date;

    @NotBlank(message = "description cannot be blank")
    private String description;

    @ElementCollection
    @Size(min = 1, message = "ingredients must be >= 1")
    @NotEmpty(message = "ingredients cannot be empty")
    private List<String> ingredients;

    @ElementCollection
    @Size(min = 1)
    @NotEmpty(message = "directions cannot be empty")
    private List<String> directions;
    public Recipe() {}

    public void copyOf(Recipe recipe) {
        name = recipe.name;
        description = recipe.description;
        category = recipe.category;
        ingredients = recipe.ingredients;
        directions = recipe.directions;
    }
}
