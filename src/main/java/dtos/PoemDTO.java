package dtos;

import entities.Poem;
import entities.Type;
import lombok.Data;

@Data

public class PoemDTO {
    private String poem;
    private Type type;
    private String author;
    private String title;

    public PoemDTO(Poem thePoem) {
        this.author = author;
        this.poem = poem;
        this.title = title;
        this.type = type;
    }
}
