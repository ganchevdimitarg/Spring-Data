package mostwanted.domain.dtos.races;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "race")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Setter
@Getter
public class RaceImportDto {

    @XmlElement
    private Integer laps;
    @XmlElement(name = "district-name")
    private String districtName;
    @XmlElement(name = "entries")
    private EntryImportRootDto entryImportRootDto;
}
