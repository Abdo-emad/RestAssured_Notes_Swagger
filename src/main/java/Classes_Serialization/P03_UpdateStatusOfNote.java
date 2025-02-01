package Classes_Serialization;

import com.fasterxml.jackson.annotation.JsonProperty;

public class P03_UpdateStatusOfNote {
    @JsonProperty("completed")
    public Boolean completed;
}
