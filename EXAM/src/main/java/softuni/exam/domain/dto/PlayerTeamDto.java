package softuni.exam.domain.dto;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class PlayerTeamDto implements Serializable {

    @Expose
    private String name;
    @Expose
    private PictureDto picture;

    public PlayerTeamDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PictureDto getPicture() {
        return picture;
    }

    public void setPicture(PictureDto picture) {
        this.picture = picture;
    }
}
