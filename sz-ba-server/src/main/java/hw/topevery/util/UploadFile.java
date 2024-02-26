package hw.topevery.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("附件")
public class UploadFile {
    @ApiModelProperty(
            hidden = true
    )
    private Integer id;
    @ApiModelProperty(
            name = "文件id"
    )
    private String fileId;
    @ApiModelProperty(
            name = "MP3文件id"
    )
    private String mp3FileId;
    @ApiModelProperty(
            name = "文件名称：带后缀"
    )
    private String fileName;
    @ApiModelProperty(
            name = "名称"
    )
    private String name;
    @ApiModelProperty(
            name = "0图片，1音频，2视频，3其他"
    )
    private int fileType;
    @ApiModelProperty(
            name = "系统类型：0.系统，1.微信（直接上传到系统的为系统文件，需要去微信服务器down下来的为微信系统文件）"
    )
    private int sysType;

    public static UploadFile.UploadFileBuilder builder() {
        return new UploadFile.UploadFileBuilder();
    }

    public Integer getId() {
        return this.id;
    }

    public String getFileId() {
        return this.fileId;
    }

    public String getMp3FileId() {
        return this.mp3FileId;
    }

    public String getFileName() {
        return this.fileName;
    }

    public String getName() {
        return this.name;
    }

    public int getFileType() {
        return this.fileType;
    }

    public int getSysType() {
        return this.sysType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setMp3FileId(String mp3FileId) {
        this.mp3FileId = mp3FileId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public void setSysType(int sysType) {
        this.sysType = sysType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof UploadFile)) {
            return false;
        } else {
            UploadFile other = (UploadFile)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label79: {
                    Object this$id = this.getId();
                    Object other$id = other.getId();
                    if (this$id == null) {
                        if (other$id == null) {
                            break label79;
                        }
                    } else if (this$id.equals(other$id)) {
                        break label79;
                    }

                    return false;
                }

                Object this$fileId = this.getFileId();
                Object other$fileId = other.getFileId();
                if (this$fileId == null) {
                    if (other$fileId != null) {
                        return false;
                    }
                } else if (!this$fileId.equals(other$fileId)) {
                    return false;
                }

                Object this$mp3FileId = this.getMp3FileId();
                Object other$mp3FileId = other.getMp3FileId();
                if (this$mp3FileId == null) {
                    if (other$mp3FileId != null) {
                        return false;
                    }
                } else if (!this$mp3FileId.equals(other$mp3FileId)) {
                    return false;
                }

                label58: {
                    Object this$fileName = this.getFileName();
                    Object other$fileName = other.getFileName();
                    if (this$fileName == null) {
                        if (other$fileName == null) {
                            break label58;
                        }
                    } else if (this$fileName.equals(other$fileName)) {
                        break label58;
                    }

                    return false;
                }

                label51: {
                    Object this$name = this.getName();
                    Object other$name = other.getName();
                    if (this$name == null) {
                        if (other$name == null) {
                            break label51;
                        }
                    } else if (this$name.equals(other$name)) {
                        break label51;
                    }

                    return false;
                }

                if (this.getFileType() != other.getFileType()) {
                    return false;
                } else if (this.getSysType() != other.getSysType()) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof UploadFile;
    }

    public int hashCode() {
        int result = 1;
        Object $id = this.getId();
        result = result * 59 + ($id == null ? 43 : $id.hashCode());
        Object $fileId = this.getFileId();
        result = result * 59 + ($fileId == null ? 43 : $fileId.hashCode());
        Object $mp3FileId = this.getMp3FileId();
        result = result * 59 + ($mp3FileId == null ? 43 : $mp3FileId.hashCode());
        Object $fileName = this.getFileName();
        result = result * 59 + ($fileName == null ? 43 : $fileName.hashCode());
        Object $name = this.getName();
        result = result * 59 + ($name == null ? 43 : $name.hashCode());
        result = result * 59 + this.getFileType();
        result = result * 59 + this.getSysType();
        return result;
    }

    public String toString() {
        return "UploadFile(id=" + this.getId() + ", fileId=" + this.getFileId() + ", mp3FileId=" + this.getMp3FileId() + ", fileName=" + this.getFileName() + ", name=" + this.getName() + ", fileType=" + this.getFileType() + ", sysType=" + this.getSysType() + ")";
    }

    public UploadFile() {
    }

    public UploadFile(Integer id, String fileId, String mp3FileId, String fileName, String name, int fileType, int sysType) {
        this.id = id;
        this.fileId = fileId;
        this.mp3FileId = mp3FileId;
        this.fileName = fileName;
        this.name = name;
        this.fileType = fileType;
        this.sysType = sysType;
    }

    public static class UploadFileBuilder {
        private Integer id;
        private String fileId;
        private String mp3FileId;
        private String fileName;
        private String name;
        private int fileType;
        private int sysType;

        UploadFileBuilder() {
        }

        public UploadFile.UploadFileBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public UploadFile.UploadFileBuilder fileId(String fileId) {
            this.fileId = fileId;
            return this;
        }

        public UploadFile.UploadFileBuilder mp3FileId(String mp3FileId) {
            this.mp3FileId = mp3FileId;
            return this;
        }

        public UploadFile.UploadFileBuilder fileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public UploadFile.UploadFileBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UploadFile.UploadFileBuilder fileType(int fileType) {
            this.fileType = fileType;
            return this;
        }

        public UploadFile.UploadFileBuilder sysType(int sysType) {
            this.sysType = sysType;
            return this;
        }

        public UploadFile build() {
            return new UploadFile(this.id, this.fileId, this.mp3FileId, this.fileName, this.name, this.fileType, this.sysType);
        }

        public String toString() {
            return "UploadFile.UploadFileBuilder(id=" + this.id + ", fileId=" + this.fileId + ", mp3FileId=" + this.mp3FileId + ", fileName=" + this.fileName + ", name=" + this.name + ", fileType=" + this.fileType + ", sysType=" + this.sysType + ")";
        }
    }
}
