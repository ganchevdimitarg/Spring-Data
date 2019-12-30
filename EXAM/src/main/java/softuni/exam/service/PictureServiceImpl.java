package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.PicturesImportDto;
import softuni.exam.domain.dto.PicturesRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@Service
public class PictureServiceImpl implements PictureService {

    private final static String PICTURE_FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\xml\\pictures.xml";

    private final PictureRepository pictureRepository;
    private final FileUtil  fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, FileUtil fileUtil, XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }


    @Override
    public String importPictures() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        PicturesRootDto picturesRootDto = this.xmlParser.importXML(PicturesRootDto.class, PICTURE_FILE_PATH);

        for (PicturesImportDto picturesDto  : picturesRootDto.getPicturesImportDtos()) {
            Picture picture = this.modelMapper.map(picturesDto, Picture.class);
            if (!this.validatorUtil.isValid(picture)){
                sb.append("Invalid picture").append(System.lineSeparator());
                continue;
            }

            this.pictureRepository.saveAndFlush(picture);
            sb.append(String.format("Successfully imported  picture - %s", picture.getUrl())).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() != 0;
    }

    @Override
    public String readPicturesXmlFile() throws IOException, IOException {
        return this.fileUtil.readFile(PICTURE_FILE_PATH);
    }

}
