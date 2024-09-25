package entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="poems")
@Builder

@NamedQueries( {
        @NamedQuery(name = "Poem.getAllPoems", query = "SELECT p FROM Poem p"),
        @NamedQuery(name = "Poem.getPoemById", query = "SELECT p FROM Poem p WHERE p.id = :id"),
})

public class Poem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String poem;
    @Enumerated(EnumType.STRING)
    private Type type;
    private String author;
    private String title;
}
