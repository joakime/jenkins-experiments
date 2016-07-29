#!groovy

node('oss') {
  // System Dependent Locations
  def mvntool = tool name: 'maven3', type: 'hudson.tasks.Maven$MavenInstallation'
  def jdktool = tool name: 'jdk8', type: 'hudson.model.JDK'

  // Environment
  List mvnEnv = ["PATH+MVN=${mvntool}/bin", "PATH+JDK=${jdktool}/bin", "JAVA_HOME=${jdktool}/", "MAVEN_HOME=${mvntool}"]
  mvnEnv.add("MAVEN_OPTS=-Xms256m -Xmx1024m -XX:MaxPermSize=512m -Djava.awt.headless=true")

  stage 'Checkout'

  checkout scm

  stage 'Compile & Test'

  withEnv(mvnEnv) {
    sh "mvn -B clean install"
  }
}
// vim: et:ts=2:sw=2:ft=groovy
