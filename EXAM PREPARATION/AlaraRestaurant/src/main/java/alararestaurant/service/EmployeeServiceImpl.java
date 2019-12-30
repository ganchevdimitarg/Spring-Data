package alararestaurant.service;

import alararestaurant.domain.dtos.EmployeeImportDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final static String EMPLOYEE_FILE_PATH = "C:\\Users\\Gyuler\\IdeaProjects\\AlaraRestaurant\\src\\main\\resources\\files\\employees.json";

    private final EmployeeRepository employeeRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;
    private final PositionRepository positionRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, FileUtil fileUtil, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil, PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.positionRepository = positionRepository;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEE_FILE_PATH);
    }

    @Override
    public String importEmployees(String employees) {
        StringBuilder sb = new StringBuilder();

        EmployeeImportDto[] employeeImportDto = this.gson.fromJson(employees, EmployeeImportDto[].class);

        for (EmployeeImportDto importDto : employeeImportDto) {
            Employee employee = this.modelMapper.map(importDto, Employee.class);
            if (!this.validationUtil.isValid(employee)) {
                sb.append("Invalid data format.").append(System.lineSeparator());

                continue;
            }

            Position position = this.positionRepository.findByName(importDto.getPosition());
            if (position == null) {
                position = new Position();
                position.setName(importDto.getPosition());
                if (!this.validationUtil.isValid(position)){
                    sb.append("Invalid data format.").append(System.lineSeparator());

                    continue;
                }
                position = this.positionRepository.saveAndFlush(position);
            }
            employee.setPosition(position);
            this.employeeRepository.saveAndFlush(employee);
            sb.append(String.format("Record %s successfully imported.", employee.getName())).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
