package softuni.exam.domain.dto;

import softuni.exam.domain.entities.Team;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "teams")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamRootDto implements Serializable {

    @XmlElement(name = "team")
    private List<TeamImportDto> teams;

    public TeamRootDto() {
    }

    public List<TeamImportDto> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamImportDto> teams) {
        this.teams = teams;
    }
}
