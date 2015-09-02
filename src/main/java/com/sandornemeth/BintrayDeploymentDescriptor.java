package com.sandornemeth;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by sandor_nemeth on 02.09.15.
 */
public class BintrayDeploymentDescriptor {

  @JsonProperty("package")
  private PackageDescriptor packageDescriptor;

  public PackageDescriptor getPackageDescriptor() {
    return packageDescriptor;
  }

  public void setPackageDescriptor(
      PackageDescriptor packageDescriptor) {
    this.packageDescriptor = packageDescriptor;
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
}
