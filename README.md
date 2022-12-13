# Projet Mobile Dev - FunPark - FIREBASE
644-1 Cloud

**Auteurs** : Céline Vialard & Théo Clerc

Dans cette partie du projet, les données de l'applications FunPark ont été placées dans le service Google appelé "Firebase". Ces données sont maintenant stockeés en cloud et peuvent être consultées en temps réel par les utilisateurs.

## Fonctionnalités supplémentaires - FIREBASE : 
###  Ajout des notifications
*Pour tester, effectuez les étapes suivantes :*
1) Dans Firebase, se rendre dans "Messaging"
2) Cliquer sur "Promotion de Noël" et ensuite sur le boutton "Dupliquer"
3) Passer jusqu'au point 3 et sélectionner "Maintenant"
4) Ensuite appuyer sur "Consulter" et "Publier"
*Attendre jusqu'à 5 minutes pour la réception de la notification*

###  Ajout des messages In-App
*Pour tester, effectuez les étapes suivantes :*
**Dans AndroidStudio :**
1) Lancer l'application "FunPark"
2) Activer les logcat dans "View", "Tool Windows" et "Logcat"
3) Rechercher dans le logcat les éléments suivants : "with Installation ID" et récupérer l'ID 
*Exemple : I/FIAM.Headless: Starting InAppMessaging runtime with Installation ID* ***cqeIRK6ZQrquDGyQdwOMXM*** 

**Dans la console Firebase :**
1) Se rendre dans "Messaging"
2) Ouvrir "Draft Compaign"
3) Appuyer sur le boutton "Modifier"
4) Ensuite, appuyer sur le boutton "Tester sur un appareil"
5) Ajouter l'ID récupéré précédemment et appuyer sur "Tester"
6) Attendre le message "La campagne est prête pour le test"
7) Dans l'émulateur, fermer et ouvrir à nouveau l'application "FunPark"

## Difficultés rencontrées:
- Problèmes avec les émulateurs pour tester les différentes fonctions supplémentaires à intégrer dans Firebase
- Option "Dynamic Links": Pas possible d'ajouter cette option sans ajouter une empreinte de certificat SHA-256 au projet de base

## Version du projet:
Lors de l'élaboration de ce projet, l'application a été développée et testée pour une utilisation sur un émulateur "Pixel 3a API 30" et sur un appareil android "Sony F3111".
