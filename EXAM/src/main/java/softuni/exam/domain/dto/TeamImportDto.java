package softuni.exam.domain.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "team")
@XmlAccessorType(XmlAccessType.FIELD)
public class TeamImportDto {
    @XmlElement
    private String name;
    @XmlElement(name = "picture")
    private PicturesImportDto picturesImportDto;

    public TeamImportDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PicturesImportDto getPicturesImportDto() {
        return picturesImportDto;
    }

    public void setPicturesImportDto(PicturesImportDto picturesImportDto) {
        this.picturesImportDto = picturesImportDto;
    }
}
