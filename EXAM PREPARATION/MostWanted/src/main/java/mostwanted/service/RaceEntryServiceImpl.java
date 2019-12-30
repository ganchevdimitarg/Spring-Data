package mostwanted.service;

import mostwanted.domain.dtos.raceentries.RaceEntryImportDto;
import mostwanted.domain.dtos.raceentries.RaceEntryImportRootDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.RaceEntry;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RaceEntryRepository;
import mostwanted.repository.RacerRepository;
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

import static mostwanted.common.Constants.*;

@Service
public class RaceEntryServiceImpl implements RaceEntryService {

    private final static String RACE_ENTRIES_XML_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/race-entries.xml";

    private final RaceEntryRepository raceEntryRepository;
    private final CarRepository carRepository;
    private final RacerRepository racerRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final FileUtil fileUtil;

    @Autowired
    public RaceEntryServiceImpl(RaceEntryRepository raceEntryRepository, CarRepository carRepository, RacerRepository racerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, FileUtil fileUtil) {
        this.raceEntryRepository = raceEntryRepository;
        this.carRepository = carRepository;
        this.racerRepository = racerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.fileUtil = fileUtil;
    }


    @Override
    public Boolean raceEntriesAreImported() {
        return this.raceEntryRepository.count() > 0;
    }

    @Override
    public String readRaceEntriesXmlFile() throws IOException {
       return this.fileUtil.readFile(RACE_ENTRIES_XML_FILE_PATH);
    }

    @Override
    public String importRaceEntries() throws JAXBException {
        StringBuilder sb = new StringBuilder();

        JAXBContext jaxbContext =JAXBContext.newInstance(RaceEntryImportRootDto.class);
        Unmarshaller unmarshaller =jaxbContext.createUnmarshaller();

        RaceEntryImportRootDto raceEntryImportRootDto = (RaceEntryImportRootDto) unmarshaller.unmarshal(new File(RACE_ENTRIES_XML_FILE_PATH));

        for (RaceEntryImportDto raceEntryImportDto : raceEntryImportRootDto.getRaceEntryImportDtos()) {
            Car car = this.carRepository.findById(Integer.parseInt(raceEntryImportDto.getCarId()));
            Racer racer = this.racerRepository.findByName(raceEntryImportDto.getRacer());
            RaceEntry raceEntry = new RaceEntry();
            raceEntry.setCar(car);
            raceEntry.setFinishTime(Double.parseDouble(raceEntryImportDto.getFinishTime()));
            raceEntry.setHasFinished(Boolean.parseBoolean(raceEntryImportDto.getHasFinished()));
            raceEntry.setRacer(racer);

            if (!this.validationUtil.isValid(raceEntry) || car == null){
                sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            if (this.raceEntryRepository.findByCar(car) != null) {
                sb.append(DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            this.raceEntryRepository.saveAndFlush(raceEntry);
            sb.append(sb.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, raceEntry.getClass().getSimpleName(), raceEntry.getId())).append(System.lineSeparator()));

        }

        return sb.toString().trim();
    }
}
