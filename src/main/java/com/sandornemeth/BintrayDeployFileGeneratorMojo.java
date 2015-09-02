package com.sandornemeth;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * Goal which touches a timestamp file.
 */
@Mojo(name = "generate-bintray-descriptor", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class BintrayDeployFileGeneratorMojo extends AbstractMojo {
  /**
   * Location of the file.
   */
  @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
  private File outputDirectory;

  @Parameter(defaultValue = "${project.artifactId}", required = true, property = "package")
  private String packageName;

  @Parameter(required = true, property = "repository")
  private String repository;

  @Parameter(required = true)
  private String subject;

  @Parameter(defaultValue = "${project.version}", required = true)
  private String version;

  @Parameter(defaultValue = "${project.artifactId}", required = true, readonly = true)
  private String artifactId;

  @Parameter(defaultValue = "${project.groupId}", required = true, readonly = true)
  private String groupId;

  public void execute() throws MojoExecutionException {
    File f = outputDirectory;
    if (!f.exists()) {
      f.mkdirs();
    }

    ObjectMapper objectMapper = new ObjectMapper();

    BintrayDeploymentDescriptor.PackageDescriptor packageDescriptor =
        new BintrayDeploymentDescriptor.PackageDescriptor();
    packageDescriptor.setName(packageName);
    packageDescriptor.setRepo(repository);
    packageDescriptor.setSubject(subject);
    BintrayDeploymentDescriptor.VersionDescriptor versionDescriptor =
        new BintrayDeploymentDescriptor.VersionDescriptor();
    versionDescriptor.setName(version);

    String groupPath = groupId.replace(".", "/");
    String pathBase = groupPath + "/" + artifactId + "/" + version;

    BintrayDeploymentDescriptor.UploadPattern artifactPattern = new BintrayDeploymentDescriptor
        .UploadPattern();
    artifactPattern.setIncludePattern("target/(" + artifactId + ".*\\.jar)");
    artifactPattern.setUploadPattern(pathBase + "/$1");

    BintrayDeploymentDescriptor.UploadPattern pomPattern = new BintrayDeploymentDescriptor
        .UploadPattern();
    pomPattern.setIncludePattern("./(pom.xml)");
    pomPattern.setUploadPattern(pathBase + "/" + artifactId + "-" + version + ".pom");

    BintrayDeploymentDescriptor deploymentDescriptor = new BintrayDeploymentDescriptor();
    deploymentDescriptor.setPackageDescriptor(packageDescriptor);
    deploymentDescriptor.setVersionDescriptor(versionDescriptor);
    deploymentDescriptor.setFiles(Arrays.asList(new BintrayDeploymentDescriptor.UploadPattern[]
        {artifactPattern, pomPattern}));

    File outputFile = new File(f, "bintray-deploy.json");
    try {
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(outputFile, deploymentDescriptor);
    } catch (IOException e) {
      throw new MojoExecutionException("Error creating deployment descriptor " + outputFile, e);
    }
  }
}
