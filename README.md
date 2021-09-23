# jmxmonitor
JMX Monitor

Un outil pour imprimer les statistiques de Hikari dans un fichier de logs.

Format: [date] [heure] [ActiveConnections] [IdleConnections] [ThreadsAwaitingConnection] [TotalConnections]

Exemple des logs :

`2021-09-23 16:44:44.200 0, 26, 0, 26`

# instructions

## build
`mvn assembly:assembly`

## run
`java -jar target/jmxmonitor-jar-with-dependencies.jar [host] [port] [frequency] [log-filename]`

frequency = fréquence des messages loggé en ms

## tips
J'ai du rajouté `-Djava.net.preferIPv4Stack=true` à `tomcat/bin/setenv.sh` pour que ça marche sur mon poste, sinnon la connexion échoue.
