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
    notifyBuild("Checkout Failure")
    throw e
  }

  try
  {
    stage 'Compile & Test'

    withEnv(mvnEnv) {
      sh "mvn -B clean install"
    }
  } catch (Exception e) {
    notifyBuild("Compile & Test Failure")
    throw e
  }
}

def notifyBuild(String buildStatus)
{
  // default the value
  buildStatus = buildStatus ?: "UNKNOWN"

  def summary = "${env.JOB_NAME}#${env.BUILD_NUMBER} - ${buildStatus}"
  def detail = """<p>Job: ${env.JOB_NAME} [#${env.BUILD_NUMBER}]</p>
  <p><b>${buildStatus}</b></p>
  <table>
    <tr><td>Build</td><td><a href='${env.BUILD_URL}'>${env.BUILD_URL}</a></td><tr>
    <tr><td>Job</td><td><a href='${env.JOB_URL}'>${env.JOB_URL}</a></td><tr>
    <tr><td>Console</td><td><a href='${env.JOB_URL}console'>${env.JOB_URL}console</a></td><tr>
    <tr><td>Test Report</td><td><a href='${env.JOB_URL}testReport/'>${env.JOB_URL}testReport/</a></td><tr>
  </table>
  """

  emailext (
    subject = summary,
    body = detail,
    to = "${env.EMAILADDRESS}"
  )

}


// vim: et:ts=2:sw=2:ft=groovy
