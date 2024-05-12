# Rendu SIR BACK - Sacha Fernandez-Soltane et Paul Leclercq


* Prérequis : Avoir JAVA_HOME et MAVEN_HOME dans les variables d'environnements
* Avoir phpmyadmin en local et changer les identifiants pour se connecter à la base de données si besoin dans le fichier
  persistence.xml
---
# POUR EVITER DES PROBLEMES DE CACHE MERCI D'UTILISER LA NAVIGATION PRIVEE #

### Démarrer le projet ####

Pour démarrer le projet, faire la commande :

```
cd /JaxRSOpenAPI 
```
puis dans le pom exécuter la commande :

```
mvn clean:install
```
Ensuite lancer le serveur <ins>(fichier RestServeur + fichier run-....)</ins>,ensuite vous devrez créer la base de données dans phpmyadmin que vous appelerez <ins>"bdd_backend"</ins> puis lancer la classe situé dans "/jpa/src/main/java/jpa/JpaTest.java", cette classe devrait
<ins>créer puis insérér des informations en base de données.</ins>

Ainsi vous aurez le back de fonctionnel et une base de données déjà remplie avec un jeu de test


### Pour se connecter en tant que User : ###
test 
test 

### Pour se connecter en tant que Admin : ###
test2
test2
