package mostwanted.domain.dtos.raceentries;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.xml.bind.annotation.*;

@XmlRootElement(name = "race-entry")
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
@Setter
@Getter
public class RaceEntryImportDto {

    @XmlAttribute(name = "has-finished")
    private String hasFinished;
    @XmlAttribute(name = "finish-time")
    private String finishTime;
    @XmlAttribute(name = "car-id")
    private String carId;
    @XmlElement
    private String racer;
}
