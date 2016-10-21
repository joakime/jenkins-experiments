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
      sh "mvn -B -Dmaven.test.failure.ignore clean install"
      step([$class: 'JUnitResultArchiver', testResults: '**/target/surefire-reports/TEST-*.xml'])
          step([$class: 'WarningsPublisher', 
              consoleParsers: [
                  [parserName: 'Maven'],
                  [parserName: 'JavaDoc'],
                  [parserName: 'JavaC'],
                  [parserName: 'stacktraces']
              ]])
      if(isUnstable())
      {
        notifyBuild("Unstable / Test Failures")
      }
    }
  } catch (Exception e) {
    notifyBuild("Compile & Test Failure")
    throw e
  }
}

def isActiveBranch()
{
  def branchName = "${env.BRANCH_NAME}"
  echo "current branch name = " + branchName
  return ( branchName == "master" ||
           branchName.startsWith("jetty-") ||
           branchName.startsWith("jenkins-") );
}

def isUnstable()
{
  return currentBuild.result == "UNSTABLE"
}

def notifyBuild(String buildStatus)
{
  if ( !isActiveBranch() )
  {
    echo "not an branch we care about"
    return
  }

  // no point emailing on this
  return 

  // default the value
  buildStatus = buildStatus ?: "UNKNOWN"

  def email = "${env.EMAILADDRESS}"
  def summary = "${env.JOB_NAME}#${env.BUILD_NUMBER} - ${buildStatus}"
  def detail = """<h4>Job: <a href='${env.JOB_URL}'>${env.JOB_NAME}</a> [#${env.BUILD_NUMBER}]</h4>
  <p><b>${buildStatus}</b></p>
  <table>
    <tr><td>Build</td><td><a href='${env.BUILD_URL}'>${env.BUILD_URL}</a></td><tr>
    <tr><td>Console</td><td><a href='${env.BUILD_URL}console'>${env.BUILD_URL}console</a></td><tr>
    <tr><td>Test Report</td><td><a href='${env.BUILD_URL}testReport/'>${env.BUILD_URL}testReport/</a></td><tr>
  </table>
  """

  emailext (
    to: email,
    subject: summary,
    body: detail
  )

}


// vim: et:ts=2:sw=2:ft=groovy
