#!groovy

node('linux') {
  // System Dependent Locations
  def mvntool = tool name: 'maven3', type: 'hudson.tasks.Maven$MavenInstallation'
  def jdktool = tool name: 'jdk8', type: 'hudson.model.JDK'

  // Environment
  List mvnEnv = ["PATH+MVN=${mvntool}/bin", "PATH+JDK=${jdktool}/bin", "JAVA_HOME=${jdktool}/", "MAVEN_HOME=${mvntool}"]
  mvnEnv.add("MAVEN_OPTS=-Xms256m -Xmx1024m -Djava.awt.headless=true")

  try
  {
    stage 'Checkout'

    checkout scm
  } catch (Exception e) {
    SUBJ = "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - Checkout Failure (${e.message})!"
    BODY = "JENKINS_URL=${env.JENKINS_URL}\nBUILD_URL=${env.BUILD_URL}\nJOB_URL=${env.JOB_URL}"
    emailext subject: "${SUBJ}", to: "${env.EMAILADDRESS}", body: "${BODY}"
    throw e
  }

  try
  {
    stage 'Compile & Test'

    withEnv(mvnEnv) {
      sh "mvn -B clean install"
    }
  } catch (Exception e) {
    SUBJ = "${env.JOB_NAME} - Build # ${env.BUILD_NUMBER} - Compile & Test Failure (${e.message})!"
    BODY = "JENKINS_URL=${env.JENKINS_URL}\nBUILD_URL=${env.BUILD_URL}\nJOB_URL=${env.JOB_URL}"
    emailext subject: "${SUBJ}", to: "${env.EMAILADDRESS}", body: "${BODY}"
    throw e
  }
}
// vim: et:ts=2:sw=2:ft=groovy
