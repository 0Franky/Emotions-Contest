# Amministrator Guide

1. Spostare i due file batch e il file HowAppYou.jar, in una stessa directory a vostra scelta.
2. Inizializzare il DB. [Se si vuole creare un nuovo sheet vedi 2.1, altrimenti 2.2]<br>
2.1. Aprire mediante un editor di testo il file batch "HowAppYou HowAppYou [Build DB - makeSheet]". Il campo "TOKEN" indica il nome che assumerà lo sheet.<br>
2.2. Aprire mediante un editor di testo il file batch "HowAppYou HowAppYou [Build DB - setSheet]". Il campo "TOKEN" indica lo spid sul quale verrano salvati i dati.
3. Sostituire il campo "TOKEN" con lo specifico dato, quindi salvare il file .bat.
4. Eseguire il file bat desiderato, prima del primo avvio dell'applicazione. Al termine verrà mostrato lo spid corrente.
5. Eseguire l'applicazione mediante il file batch "HowAppYou [Run app]" quando necessario.

## N.B.:
- Abbiamo reso il programma quanto dinamico possibile. Sulla base delle specifiche esigenze si può creare uno spid per azienda o addirittura uno spid per ogni computer aziendale.
- Se campo "TOKEN" deve contenere spazi, racchiudere tra doppi apici il valore (es: "PC01 - AZIENDA X").

### Note:
* È possibile creare un link al file .bat sul desktop così da poter facilitare l'avvio del programma. È inoltre possibile personalizzare il collegamento come segue:<br>
	1. Tasto destro sul collegamento > Proprietà
	2. Selezionare nella voce 'Esegui:' l'opzione: "Ridotta a icona"
	3. Fare click su "cambia icona..." > Sfoglia > Recarsi nella directory dove è stato posto il programma e selezionare "Icon.png" > Apri. (Al momento tale icona è salvata nella cartella "AmministratorGuide").
	4. OK > OK
	<br>
* E' possibile utilizzare il tool : [SqliteBrowser](https://sqlitebrowser.org/) per verificare il contenuto del database in SQLite.
