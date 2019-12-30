package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.RacerImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Racer;
import mostwanted.domain.entities.Town;
import mostwanted.repository.RacerRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static mostwanted.common.Constants.*;

@Service
public class RacerServiceImpl implements RacerService {

    private final static String RACERS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/racers.json";

    private final RacerRepository racerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;

    @Autowired
    public RacerServiceImpl(RacerRepository racerRepository, ModelMapper modelMapper, Gson gson, FileUtil fileUtil, ValidationUtil validationUtil, TownRepository townRepository) {
        this.racerRepository = racerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
    }

    @Override
    public Boolean racersAreImported() {
        return this.racerRepository.count() > 0;
    }

    @Override
    public String readRacersJsonFile() throws IOException {
        return this.fileUtil.readFile(RACERS_JSON_FILE_PATH);
    }

    @Override
    public String importRacers(String racersFileContent) {
        StringBuilder sb = new StringBuilder();

        RacerImportDto[] racerImportDtos = this.gson.fromJson(racersFileContent, RacerImportDto[].class);

        for (RacerImportDto racerImportDto : racerImportDtos) {
            Racer racer = this.modelMapper.map(racerImportDto, Racer.class);
            Town town = this.townRepository.findByName(racerImportDto.getHomeTown());
            if (!this.validationUtil.isValid(racer) || town == null){
                sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            if (this.racerRepository.findByName(racerImportDto.getName()) != null){
                sb.append(DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            racer.setHomeTown(town);
            this.racerRepository.saveAndFlush(racer);
            sb.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, racer.getClass().getSimpleName(), racer.getName() )).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    @Override
    public String exportRacingCars() {
        StringBuilder sb = new StringBuilder();

        List<Racer> racers = this.racerRepository.findAllCars();

        for (Racer racer : racers) {
            sb.append(String.format("Name: %s%nCars:%n", racer.getName()));

            List<Car> cars = racer.getCar();

            for (Car car : cars) {
                sb.append(String.format("   %s %s %d%n", car.getBrand(), car.getModel(), car.getYearOfProduction()));
            }
            sb.append(System.lineSeparator());
        }


        return sb.toString().trim();
    }
}
