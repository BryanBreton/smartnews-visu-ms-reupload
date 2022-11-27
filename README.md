<h1 align="center">Bienvenue dans le module smartnews-visu-ms du projet Smartnews👋</h1>
<p>
  <a href="https://github.com/ugieiris/smartnews-visu-ms#readme" target="_blank">
    <img alt="Documentation" src="https://img.shields.io/badge/documentation-yes-brightgreen.svg" />
  </a>
  <a href="https://github.com/ugieiris/smartnews-visu-ms/graphs/commit-activity" target="_blank">
    <img alt="Maintenance" src="https://img.shields.io/badge/Maintained%3F-yes-green.svg" />
  </a>
</p>

> Module du projet smartnews-visu<br />
API de visualisation sur site équipé de SmartNews

## Accès rapides 

### Docs projet
- 📚 [Drive projet](https://drive.google.com/drive/folders/17pPKeXlZw9Mr6r1Q8o6PFoFw2R1jm4WE)
- 📚 [Drive archi](https://drive.google.com/drive/folders/1zvZjyyqnkXZfPVit0BgJcRH3ZV_7g3ZW)

### Intégration

- 💻 [Application en intégration via l'API Gateway ](https://apim-int-dev.groupement.systeme-u.fr/smartnews-visu-ms/v1/accueil/42)
- 💻 [Application en intégration](http://smartnews-visu-ms.app-dev.groupement.systeme-u.fr/)

- :memo: [swagger](http://smartnews-visu-ms.app-dev.groupement.systeme-u.fr/swagger-ui.html)
- [version déployée](https://smartnews-visu-ms.app-dev.groupement.systeme-u.fr/actuator/info)

### Local

- :memo: [swagger](http://localhost:8080/swagger-ui.html)
- [actuator info](https://localhost:8080/actuator/info)

## Technologies

Cette API est développée avec les technologies suivantes :

- `Java 11 :` le langage

- `SpringBoot 2 :` le cadre de développement

- `Docker / Kubernetes :` l'application est conteneurisée et déployée sur un cluster Kubernetes, les logs sont émis dans la console uniquement et les variables d'environnements sont utilisées pour paramétrer l'application

- `u-iris-back-java :` socle technique Iris pour ajouter l'authentification, l'actuator, ... [documentation complète](https://github.com/ugieiris/u-iris-back-java/blob/develop/README.md)

- `Mockito :` développement des tests unitaires (obligatoires)

- `TestRestTemplate :` développement des tests d'intégrations (obligatoires)

- `Springdoc :` annotations permettant la génération d'un fichier de spécification de l'API au format [OpenAPI](https://www.openapis.org/).

## Getting started

### 📦 Paramétrer vos variables d'environnements

Pour paramétrer votre application, vous devez utiliser des variables d'environnement, pour cela :

- dans les fichiers properties, les propriétés qui varient selon l'environnement doivent référencer une variable d'environnement grâce à la notation :
  `ma.propriete=${MA_VARIABLE_ENVIRONNEMENT}`

- le fichier `src/main/resources/env.properties` permet de valoriser les variables d'environnements pour vos développements. 
  Pour les environnements déployés, le fichier `config.env` du [repository d'intégration](https://github.com/ugieiris/k8s-deploy-int/tree/master/CLOUD/apps) doit être initialisé.

### 🐳 Démarrage de la BDD en local avec Docker

[Commencer par installer Docker 🐳 sous Windows 10](https://confluence.systeme-u.com/display/DTDD/Configuration+de+Docker)

- Démarrage

```sh
docker-compose up
```

- Arrêt

```sh
docker-compose down
```

### 🎬 Démarrage de l'application

Installation des dépendances, compilation et lancement des tests :

```sh
mvn clean install
```

Lancement en ligne de commande :

```sh
mvn spring-boot:run "-Dspring-boot.run.profiles=dev"
```

Pour lancer l'application en local avec un IDE, il faut indiquer à SpringBoot d'utiliser le profil `dev` au démarrage de votre application en ajoutant l'option suivante à la JVM :

```sh
-Dspring.profiles.active=dev
```

### Pour lancer les TI en local, il faut lancer le conteneur

Puis lancer les TI depuis IntelliJ avec le paramètre JVM supplémentaire
```-Dspring.datasource.url=jdbc:postgresql://localhost/docker```

ou lancer la commande maven suivante :

```sh
mvn -B test-compile jacoco:prepare-agent-integration failsafe:integration-test jacoco:report-integration failsafe:verify -PIntegrationTests -Dspring.profiles.active=test -Dspring.datasource.url=jdbc:postgresql://localhost/docker
```

### 🚀 Déploiement

La configuration des descripteurs de déploiement Kubernetes est [décrite dans cette page](https://confluence.systeme-u.com/pages/viewpage.action?pageId=14195118).

Liens vers les repository principaux :
- 📁 [Descripteurs Kubernetes de base](https://github.com/ugieiris/k8s-deploy-base/tree/master/apps)
- 📁 [Descripteurs Kubernetes d'intégration](https://github.com/ugieiris/k8s-deploy-int/tree/master/CLOUD/apps)

> ⚠️ Par défaut sur les environnements déployés, l'application n'a pas accès à Forgerock pour la vérification des tokens transmis dans les requêtes. Il faut faire une [PR comme décrit dans la doc](https://confluence.systeme-u.com/pages/viewpage.action?pageId=14195518).


## Liens complémentaires

- 📄 [Smoke tests](https://confluence.systeme-u.com/pages/viewpage.action?pageId=14196041#iRiSPipelinesLes'TestRunners'-Exemple-SmokeTestsd'unewebapiavec%22JsonAPIhealthchecker%22)
- 📄 [Tests unitaires](https://confluence.systeme-u.com/display/DTDD/Tests+Unitaires)
- 📄 [Test d'intégration avec Springboot 2](https://confluence.systeme-u.com/pages/viewpage.action?pageId=14522740)
- 📄 [Migrer un projet existant en Java 11](https://confluence.systeme-u.com/display/DTDD/Migrer+un+projet+existant+en+Java+11)
- 📄 [Conteneuriser et migrer votre API Spring Boot sous Kubernetes](https://confluence.systeme-u.com/display/DTDD/Guide+de+migration+d%27une+application+Springboot+API+vers+Kubernetes)
- 📗 [Concevoir une API avec Stoplight](https://drive.google.com/drive/folders/1Pnd1cSZ5yEiqdTo5kLqEWEJhLwIU4Yqv)
- 🧮 [Créer et déployer une API sur l'API Manager](https://confluence.systeme-u.com/pages/viewpage.action?pageId=14523094)
