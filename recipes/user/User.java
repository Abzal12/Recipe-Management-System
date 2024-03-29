package recipes.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import recipes.recipe.Recipe;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Email(regexp = "[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+$")
    @Column(unique = true)
    @NotBlank(message = "Email is required")
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "At least 8 characters are required")
    @NotBlank
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Recipe> recipes;

    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
