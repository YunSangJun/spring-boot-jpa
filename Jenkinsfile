@Library('my-shared-libraries') _

javaPipeline(
    javaVersion: "8",
    appBuildArgs: "build -x test",
    dockerRegistryURL: "docker.io",
    dockerRepoName: "yunsangjun/spring-boot-jpa",
    dockerCredential: "registry_credential",
    kubeRepoName: "spring-boot-jpa-kube-resources",
    kubeRepoURL: "git@github.com:YunSangJun/spring-boot-jpa-kube-resources.git",
    gitCredential: "git_ssh_credential",
    gitUserEmail: "ysj188cm@gmail.com",
    gitUserName: "yunsangjun",
    gitDoamin: "github.com",
    slackCredential: "slack_token"
)
