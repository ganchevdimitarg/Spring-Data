package mostwanted.service;

import mostwanted.domain.dtos.races.EntryImportDto;
import mostwanted.domain.dtos.races.RaceImportDto;
import mostwanted.domain.dtos.races.RaceImportRootDto;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RaceRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static mostwanted.common.Constants.*;

@Service
public class RaceServiceImpl implements RaceService {

    private final static String RACES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/races.xml";

    private final RaceRepository raceRepository;
    private final ModelMapper modelMapper;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final RaceEntryRepository raceEntryRepository;
    private final DistrictRepository districtRepository;

    @Autowired
    public RaceServiceImpl(RaceRepository raceRepository, ModelMapper modelMapper, FileUtil fileUtil, ValidationUtil validationUtil, RaceEntryRepository raceEntryRepository, DistrictRepository districtRepository) {
        this.raceRepository = raceRepository;
        this.modelMapper = modelMapper;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.raceEntryRepository = raceEntryRepository;
        this.districtRepository = districtRepository;
    }

    @Override
    public Boolean racesAreImported() {
        return this.raceRepository.count() > 0;
    }

    @Override
    public String readRacesXmlFile() throws IOException {
        return this.fileUtil.readFile(RACES_XML_FILE_PATH);
    }

    @Override
    public String importRaces() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext jaxbContext =JAXBContext.newInstance(RaceImportRootDto.class);
        Unmarshaller unmarshaller =jaxbContext.createUnmarshaller();

        RaceImportRootDto raceEntryImportRootDto = (RaceImportRootDto) unmarshaller.unmarshal(new File(RACES_XML_FILE_PATH));

        for (RaceImportDto raceImportDto : raceEntryImportRootDto.getRaceImportDtos()) {
            List<RaceEntry> raceEntries = new ArrayList<>();
            for (EntryImportDto entryImportDto : raceImportDto.getEntryImportRootDto().getEntryImportDtos()) {
                RaceEntry raceEntry = this.raceEntryRepository.findById(Integer.parseInt(entryImportDto.getId()));
                raceEntries.add(raceEntry);
            }
//            Race race = this.modelMapper.map(raceImportDto, Race.class);
            Race race = new Race();
            race.setLaps(raceImportDto.getLaps());
            race.setDistrict(this.districtRepository.findByName(raceImportDto.getDistrictName()));
            race.setRaceEntries(raceEntries);
            if (!this.validationUtil.isValid(race)){
                sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            if (this.raceRepository.findByDistrictName(raceImportDto.getDistrictName()) != null) {
                sb.append(DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            this.raceRepository.saveAndFlush(race);
            sb.append(sb.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, race.getClass().getSimpleName(), race.getId())).append(System.lineSeparator()));

        }

        return sb.toString().trim();
    }
}