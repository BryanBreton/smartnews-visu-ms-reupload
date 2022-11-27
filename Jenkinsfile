#!groovy
@Library('gie') _

k8sContinuousDeployment(
        builder: 'mvn',
        imageForBuild: 'docker-registry-iris.groupement.systeme-u.fr/iris-factory/mvn:3.6.3-jdk11',
        deployer: 'kubernetes',
        authProvider: 'none',
        slackChannel: 'smartnews-stream',
        contextRoot: 'smartnews-visu-ms-v1',
        commercialName: 'smartnews-visu-ms',
        allowManualDeployment: true,
        forceEligibilityForRelease: true,
        kubernetesDeployer: [
                namespace: 'smartnews',
                manifestRepository: 'https://github.com/ugieiris/k8s-deploy-ded-ge-rhc.git',
                integration  : [
                        cluster: 'dev-gke-app'
                ],
                recette           : [
                        cluster: 'rec-gke-app'
                ],
                production        : [
                        cluster: 'prod-gke-app'
                ]
        ]
)