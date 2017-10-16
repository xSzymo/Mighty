package game.mightywarriors.data.tables;

import javax.persistence.*;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "path")
    private String path;
    @Column(name = "file_type")
    private String fileType;

    public Image(String path, String name, String fileType) {
        this.name = name;
        this.path = path;
        this.fileType = "." + fileType;
        if (fileType.equals(""))
            this.fileType = "";
        if (fileType == null)
            this.fileType = "";
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = "." + fileType;
        if (fileType.equals(""))
            this.fileType = "";
        if (fileType == null)
            this.fileType = "";
    }
}
