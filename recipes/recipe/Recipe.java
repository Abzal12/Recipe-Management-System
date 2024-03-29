package recipes.recipe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import recipes.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
public class Recipe {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "abzal_id")
    @JsonIgnore
    private User user;

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

    public void copyOf(Recipe recipe) {
        name = recipe.name;
        description = recipe.description;
        category = recipe.category;
        ingredients = recipe.ingredients;
        directions = recipe.directions;
    }

    public Recipe(String name, String category, LocalDateTime date, String description, List<String> ingredients, List<String> directions) {
        this.name = name;
        this.category = category;
        this.date = date;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }

}
