package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.TownImportDto;
import mostwanted.domain.entities.Town;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.apache.logging.log4j.util.StringBuilderFormattable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static mostwanted.common.Constants.*;

@Service
public class TownServiceImpl implements TownService{

    private final static String TOWNS_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/towns.json";

    private final TownRepository townRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;

    @Autowired
    public TownServiceImpl(TownRepository townRepository, ModelMapper modelMapper, Gson gson, FileUtil fileUtil, ValidationUtil validationUtil) {
        this.townRepository = townRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
    }

    @Override
    public Boolean townsAreImported() {
        return this.townRepository.count() > 0;
    }

    @Override
    public String readTownsJsonFile() throws IOException {
        return this.fileUtil.readFile(TOWNS_JSON_FILE_PATH);
    }

    @Override
    public String importTowns(String townsFileContent) {
        StringBuilder sb = new StringBuilder();

        TownImportDto[] townImportDtos = this.gson.fromJson(townsFileContent, TownImportDto[].class);

        for (TownImportDto townImportDto : townImportDtos) {
            Town town = this.modelMapper.map(townImportDto, Town.class);
            if (!this.validationUtil.isValid(town)){
                sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            if (this.townRepository.findByName(townImportDto.getName()) != null){
                sb.append(DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }

            this.townRepository.saveAndFlush(town);
            sb.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, town.getClass().getSimpleName(), town.getName() )).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    @Override
    public String exportRacingTowns() {
        List<Town> towns = this.townRepository.findAllByRacers();
        StringBuilder sb = new StringBuilder();

        for (Town town : towns) {
            sb.append(String.format("Name: %s%nRacers: %s%n%n", town.getName(), town.getRacers().size()));
        }
        return sb.toString().trim();
    }
}
