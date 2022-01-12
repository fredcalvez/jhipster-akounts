package com.akounts.myapp.service.dto;

import com.akounts.myapp.domain.enumeration.Status;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.akounts.myapp.domain.FileImport} entity.
 */
public class FileImportDTO implements Serializable {

    private Long id;

    private String fileName;

    private Status status;

    private Instant reviewDate;

    private String filePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Instant reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileImportDTO)) {
            return false;
        }

        FileImportDTO fileImportDTO = (FileImportDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fileImportDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileImportDTO{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", status='" + getStatus() + "'" +
            ", reviewDate='" + getReviewDate() + "'" +
            ", filePath='" + getFilePath() + "'" +
            "}";
    }
}
