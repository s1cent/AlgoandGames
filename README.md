# AlgoandGames


~~Es ist jetzt mit Java Maven und JavaFx gemacht. Wenn wer was dagegen hat ist es auch oke. zum Ausführen wär es mvn clean javafx:run.~~
Vorzeitig deaktiviert


http://www.ist.tugraz.at/as19.html

IDE: IntelliJ

Falls in GameComGrpc.java @javax.annotation.Generated nicht erkannt/gefunden wird kann man dies auf @javax.annotation.processing.Generated ändern.

Im game Odner: Kompilieren mit "mvn clean install" und zum ausführen können "./run_game" oder die IntelliJ Konfigurationen verwendet werden.

\
**Setup (jeder muss das machen):**
* Verbinden mit Tu Graz VPN: https://tugnet.tugraz.at/zugang/vpn/
* UserRegistrationSettings.java mit persönlichen Daten befüllen
* mvn clean install
* Run registration ausführen
* 'Error_code: OK' sollte vom server zurückkommen
* UserToken merken/aufschreiben
* Eigenen User Token in UserToken datei einfügen
* UserToken in die UserTokens Textdatei einfügen
* Commit und Push (**Aber bitte nicht UserRegistrationSettings und UserToken**)