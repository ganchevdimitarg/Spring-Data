package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.TeamImportDto;
import softuni.exam.domain.dto.TeamRootDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Position;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final static String TEAM_FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\xml\\teams.xml";

    private final TeamRepository teamRepository;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;
    private final PictureRepository pictureRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, FileUtil fileUtil, XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validatorUtil, PictureRepository pictureRepository) {
        this.teamRepository = teamRepository;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public String importTeams() throws JAXBException {
        List<String> lines = new ArrayList<>();

        TeamRootDto teamRootDto = this.xmlParser.importXML(TeamRootDto.class, TEAM_FILE_PATH);

        for (TeamImportDto teamDto: teamRootDto.getTeams()) {
            Team team = this.modelMapper.map(teamDto, Team.class);
            Picture picture = this.pictureRepository.findByUrl(teamDto.getPicturesImportDto().getUrl());
            team.setPicture(picture);

            if (!validatorUtil.isValid(team)){
                lines.add("Invalid team");
                continue;
            }

            this.teamRepository.saveAndFlush(team);
            lines.add(String.format("Successfully imported team - %s", team.getName()));
        }

        return String.join("\n", lines);
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() != 0;
    }

    @Override
    public String readTeamsXmlFile() throws IOException {
        return this.fileUtil.readFile(TEAM_FILE_PATH);
    }
}
