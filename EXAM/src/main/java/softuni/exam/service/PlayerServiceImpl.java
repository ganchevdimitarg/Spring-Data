package softuni.exam.service;

import com.google.gson.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dto.PlayerDto;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Player;
import softuni.exam.domain.entities.Team;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final static String PLAYERS_FILE_PATH = System.getProperty("user.dir") + "\\src\\main\\resources\\files\\json\\players.json";
    private final static BigDecimal SALARY = new BigDecimal("100000");
    
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, TeamRepository teamRepository, PictureRepository pictureRepository, FileUtil fileUtil, ModelMapper modelMapper, Gson gson, ValidatorUtil validatorUtil) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public String importPlayers() throws FileNotFoundException {
        List<String> lines = new ArrayList<>();

        Reader reader = new FileReader(PLAYERS_FILE_PATH);

        PlayerDto[] playerDtos = this.gson.fromJson(reader, PlayerDto[].class);

        for (PlayerDto playerDto : playerDtos) {
            Player player = this.modelMapper.map(playerDto, Player.class);
            Picture picture =this.pictureRepository.findByUrl(playerDto.getPicture().getUrl());
            Team team = this.teamRepository.findByName(playerDto.getTeam().getName());
            player.setTeam(team);
            player.setPicture(picture);
            if (!validatorUtil.isValid(player)) {
                lines.add("Invalid team");
                continue;
            }

            this.playerRepository.saveAndFlush(player);
            lines.add(String.format("Successfully imported team - %s %s", player.getFirstName(), player.getLastName()));
        }

        return String.join("\n", lines);
    }

    @Override
    public boolean areImported() {
        return this.playerRepository.count() != 0;
    }

    @Override
    public String readPlayersJsonFile() throws IOException {
        return this.fileUtil.readFile(PLAYERS_FILE_PATH);
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();

        List<Player> players = this.playerRepository.findSalaryOrderBySalaryDesc();

        for (Player player : players) {
            sb.append(String.format("Player name: %s %s", player.getFirstName(), player.getLastName())).append(System.lineSeparator());
            sb.append(String.format("\tNumber: %d", player.getNumber())).append(System.lineSeparator());
            sb.append(String.format("\tSalary: %s", player.getSalary())).append(System.lineSeparator());
            sb.append(String.format("\tTeam: %s", player.getTeam().getName())).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();

        List<Player> players = this.playerRepository.findAllByTeamOrderById(this.teamRepository.findByName("North Hub"));
        sb.append("Team: North Hub").append(System.lineSeparator());
        for (Player player : players) {
            sb.append(String.format("\tPlayer name: %s %s - %s", player.getFirstName(), player.getLastName(), player.getPosition())).append(System.lineSeparator());
            sb.append(String.format("\tNumber: %d", player.getNumber())).append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
