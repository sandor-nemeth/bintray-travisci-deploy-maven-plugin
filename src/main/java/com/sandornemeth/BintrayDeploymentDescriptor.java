package com.sandornemeth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * Created by sandor_nemeth on 02.09.15.
 */
@JsonPropertyOrder({ "package", "version", "files", "publish"})
public class BintrayDeploymentDescriptor {

  @JsonProperty("package")
  private PackageDescriptor packageDescriptor;
  @JsonProperty("version")
  private VersionDescriptor versionDescriptor;
  private List<UploadPattern> files;
  private boolean publish = false;

  public PackageDescriptor getPackageDescriptor() {
    return packageDescriptor;
  }

  public void setPackageDescriptor(
      PackageDescriptor packageDescriptor) {
    this.packageDescriptor = packageDescriptor;
  }

  public VersionDescriptor getVersionDescriptor() {
    return versionDescriptor;
  }

  public void setVersionDescriptor(
      VersionDescriptor versionDescriptor) {
    this.versionDescriptor = versionDescriptor;
  }

  public List<UploadPattern> getFiles() {
    return files;
  }

  public void setFiles(
      List<UploadPattern> files) {
    this.files = files;
  }

  public boolean isPublish() {
    return publish;
  }

  public void setPublish(boolean publish) {
    this.publish = publish;
  }

  public static class PackageDescriptor {
    private String name;
    private String repo;
    private String subject;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getRepo() {
      return repo;
    }

    public void setRepo(String repo) {
      this.repo = repo;
    }

    public String getSubject() {
      return subject;
    }

    public void setSubject(String subject) {
      this.subject = subject;
    }
  }

  public static class VersionDescriptor {
    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }

  public static class UploadPattern {
    private String includePattern;
    private String uploadPattern;

    public String getIncludePattern() {
      return includePattern;
    }

    public void setIncludePattern(String includePattern) {
      this.includePattern = includePattern;
    }

    public String getUploadPattern() {
      return uploadPattern;
    }

    public void setUploadPattern(String uploadPattern) {
      this.uploadPattern = uploadPattern;
    }
  }
}
