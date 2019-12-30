package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.CarImportDto;
import mostwanted.domain.entities.Car;
import mostwanted.domain.entities.Race;
import mostwanted.domain.entities.Racer;
import mostwanted.repository.CarRepository;
import mostwanted.repository.RacerRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static mostwanted.common.Constants.*;

@Service
public class CarServiceImpl implements CarService{

    private final static String CARS_JSON_FILE_PATH = System.getProperty("user.dir")+"/src/main/resources/files/cars.json";

    private final CarRepository carRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final RacerRepository racerRepository;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper, Gson gson, FileUtil fileUtil, ValidationUtil validationUtil, RacerRepository racerRepository) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.racerRepository = racerRepository;
    }

    @Override
    public Boolean carsAreImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsJsonFile() throws IOException {
        return this.fileUtil.readFile(CARS_JSON_FILE_PATH);
    }

    @Override
    public String importCars(String carsFileContent) {
        StringBuilder sb = new StringBuilder();

        CarImportDto[] carImportDtos = this.gson.fromJson(carsFileContent, CarImportDto[].class);

        for (CarImportDto carImportDto : carImportDtos) {
            Car car = this.modelMapper.map(carImportDto, Car.class);
            Racer racer = this.racerRepository.findByName(carImportDto.getRacerName());
            if (!this.validationUtil.isValid(car) || racer == null) {
                sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            if (this.carRepository.findByPrice(carImportDto.getPrice()) != null) {
                sb.append(DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            car.setRacer(racer);
            this.carRepository.saveAndFlush(car);
            String entityField = String.format("%s %s @ %d", car.getBrand(), car.getModel(), car.getYearOfProduction());
            sb.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, car.getClass().getSimpleName(), entityField)).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
