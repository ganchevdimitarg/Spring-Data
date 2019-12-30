package softuni.exam.domain.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "pictures")
@XmlAccessorType(XmlAccessType.FIELD)
public class PicturesRootDto implements Serializable {

    @XmlElement(name = "picture")
    private List<PicturesImportDto> picturesImportDtos;

    public PicturesRootDto() {
    }

    public List<PicturesImportDto> getPicturesImportDtos() {
        return picturesImportDtos;
    }

    public void setPicturesImportDtos(List<PicturesImportDto> picturesImportDtos) {
        this.picturesImportDtos = picturesImportDtos;
    }
}
