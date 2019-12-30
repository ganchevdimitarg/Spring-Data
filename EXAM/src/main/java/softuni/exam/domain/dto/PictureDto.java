package softuni.exam.domain.dto;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class PictureDto implements Serializable {
    @Expose
    private String url;

    public PictureDto() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
