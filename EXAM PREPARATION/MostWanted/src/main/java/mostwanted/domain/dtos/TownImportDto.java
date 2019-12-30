package mostwanted.domain.dtos;


import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class TownImportDto {

    @Expose
    private String name;
}
