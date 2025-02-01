package Classes_Serialization;

import com.fasterxml.jackson.annotation.JsonProperty;

public class P02_CreateNewNote {
    @JsonProperty("title")
    public String title;
    @JsonProperty("description")
    public String description;
    @JsonProperty("category")
    public String category;
}
