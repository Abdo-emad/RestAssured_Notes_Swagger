package Classes_Serialization;

import com.fasterxml.jackson.annotation.JsonProperty;

public class P01_LoginRequest {
    @JsonProperty("email")
     public String Email;
    @JsonProperty("password")
    public String Password;
}
