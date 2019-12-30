package mostwanted.service;

import com.google.gson.Gson;
import mostwanted.domain.dtos.DistrictImportDto;
import mostwanted.domain.entities.District;
import mostwanted.domain.entities.Town;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static mostwanted.common.Constants.*;

@Service
public class DistrictServiceImpl implements DistrictService {

    private final static String DISTRICT_JSON_FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/files/districts.json";

    private final DistrictRepository districtRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final FileUtil fileUtil;
    private final ValidationUtil validationUtil;
    private final TownRepository townRepository;

    @Autowired
    public DistrictServiceImpl(DistrictRepository districtRepository, ModelMapper modelMapper, Gson gson, FileUtil fileUtil, ValidationUtil validationUtil, TownRepository townRepository) {
        this.districtRepository = districtRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.fileUtil = fileUtil;
        this.validationUtil = validationUtil;
        this.townRepository = townRepository;
    }

    @Override
    public Boolean districtsAreImported() {
        return this.districtRepository.count() > 0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        return this.fileUtil.readFile(DISTRICT_JSON_FILE_PATH);
    }

    @Override
    public String importDistricts(String districtsFileContent) {
        StringBuilder sb = new StringBuilder();

        DistrictImportDto[] districtImportDtos = this.gson.fromJson(districtsFileContent, DistrictImportDto[].class);

        for (DistrictImportDto districtImportDto : districtImportDtos) {
            District district = this.modelMapper.map(districtImportDto, District.class);
            Town town = this.townRepository.findByName(districtImportDto.getTownName());
            if (!this.validationUtil.isValid(district) || town == null) {
                sb.append(INCORRECT_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            if (this.districtRepository.findByName(districtImportDto.getName()) != null) {
                sb.append(DUPLICATE_DATA_MESSAGE).append(System.lineSeparator());
                continue;
            }
            district.setTown(town);
            this.districtRepository.saveAndFlush(district);
            sb.append(String.format(SUCCESSFUL_IMPORT_MESSAGE, district.getClass().getSimpleName(), district.getName())).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
