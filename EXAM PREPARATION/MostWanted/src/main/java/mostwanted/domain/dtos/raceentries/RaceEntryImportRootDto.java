package mostwanted.domain.dtos.raceentries;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "race-entries")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Setter
@Getter
public class RaceEntryImportRootDto {

    @XmlElement(name = "race-entry")
    private List<RaceEntryImportDto> raceEntryImportDtos;
}