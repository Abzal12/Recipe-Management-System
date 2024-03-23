package recipes.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
@Entity
@Data
public class Recipe {

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @NotBlank
   // @JsonProperty(required = true)
    private String name;
    @NotBlank
   // @JsonProperty(required = true)
    private String description;
    @ElementCollection
    @Size(min = 1)
    @NotNull
    private List<String> ingredients;
    @ElementCollection
    @Size(min = 1)
    @NotNull
    private List<String> directions;
    public Recipe() {}
    public Recipe(String name, String description, List<String> ingredients, List<String> directions) {

        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }
}
