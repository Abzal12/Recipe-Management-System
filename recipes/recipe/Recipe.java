package recipes.recipe;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Recipe {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private long id = 1;
    private String name;
    private String description;
    private List<String> ingredients;
    private List<String> directions;
    private static long counter = 0;
    public Recipe() {}
    public Recipe(String name, String description, List<String> ingredients, List<String> directions) {
        counter++;
        this.id = counter;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getDirections() {
        return directions;
    }

    public void setDirections(List<String> directions) {
        this.directions = directions;
    }
}
