# Installationsanleitung
Es gibt drei Optionen, um die Answerium-Applikation zu starten:  
– Virtual Machine  
– Docker  
– https://answerium.ch  

# Virtual Machine (VM)  
## Voraussetzungen ##       
– Eine Applikation, die VM-Images ausführen kann, wie die Oracle VM VirtualBox (https://www.virtualbox .org /)  
Wir haben eine VM vorbereitet, in der die Applikation bereits läuft:  
– Das Answerium-Image.ova finden Sie auf dem beiligenden Dateiträger oder kann unter http://bit.ly/answerium heruntergeladen  werden.  
(Die Datei beträgt über 4 GB, somit kann der Download einige Minuten dauern.)  
– Das Image in die VirtualBox importieren über das Menü File > Import Appliance.  
– Importieren Sie das Image mit den Standardeinstellungen.  
– Nach dem Import und dem Starten des Images können Sie den Browser in der VM unter http://localhost:3000 starten.  
# Docker  
## Voraussetzungen  
– Docker und Docker-Compose installiert.  
– Die Möglichkeit Linux-Container auszuführen.  
– Internetanschluss.  
# Sie können das Projekt lokal mit dieser Anleitung starten:  
– Wechseln in Ihrem Terminal in das Unterverzeichnis /src im Projektordner.  
– Vergewissern Sie sich, dass Sie im Verzeichnis sind, das das Dokument docker-compose.yml
beinhaltet.  
– Mit dem Befehl docker-compose up wird Docker die 3 Images erstellen und starten.  
Dies kann einige Minuten in Anspruch nehmen.  
– Im Anschluss ist die Applikation erreichbar unter http://localhost:3000.  
# Troubleshooting  
Sollten die Frontend-Dependencies nicht korrekt installiert worden sein (sichtbar im Terminalout- put wie
[//.]Can’t resolve ‘/./axios’[//.]  
In diesem Falle kann man die Dependencies wie folgt manuell installieren:  
(Voraussetzung: NodeJs und NPM installiert.)  
– Ins Verzeichnis /src/frontend wechseln.  
– Dependencies installieren mit npm install -y.  
www.answerium.ch  
Wir haben die Applikation auf einem Webserver installiert für Demozwecke:  
https://answerium.ch/  
