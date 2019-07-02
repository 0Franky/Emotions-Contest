# Amministrator Guide

## Configurazione Sheet

**(0.0) ||Pre configurazione||** Spostare il file batch `HowAppYou [-conf].bat` nella stessa directory del file `HowAppYou.jar`

**(1.0) Per configurare l'applicazione:** Eseguire il file batch `HowAppYou [-conf].bat`. Esso avvierà il file `HowAppYou.jar`

**(2.1) Per creare online un nuovo sheet:** Inserire nel campo _"Name Sheet"_ un nome identificativo per lo Sheet (ES: Azienda01) in seguito premere il tasto _"done"_<br>
In seguito verrà visualizzata un finestra di conferma di corretta avvenuta dell'operazione desiderata, quindi chiudere la finestra di Configurazione.

**(2.2) Per impostare un sheet già esistente:** Inserire nel campo _"SPID"_ lo spid identificativo dello sheet in seguito premere il tasto _"done"_ <br>
In seguito verrà visualizzata un finestra di conferma di corretta avvenuta dell'operazione desiderata, quindi chiudere la finestra di Configurazione.<br>
**SPID Esempio: (sezione del link in bold)** _https: // docs.google.com/spreadsheets/d/**12LDExGnKgl2QTUaP033afeAw_rxFEGfsuNkweUjnlUw**/edit#gid=0_ <br>
Per semplificare l'operazione di setting di uno sheet, è possibile inserire nella stessa directory del file `HowAppYou.jar` il file `HowAppYou.conf` generatosi automaticamente dopo la creazione dello sheet **"(2.1)"**.
<br>Questa operazione permette alla finestra di configurazione di effettuare l'auto-compilazione del campo _"SPID"_  utilizzando quello già conservato nel `HowAppYou.conf`

**(3.0) Per avviare l'applicazione:** Eseguire il file batch `HowAppYou [Run].bat`, quando necessario.

## N.B.:
- Il "making" **(2.1)** o il "setting" **(2.2)** di uno sheet per un'applicazione invierà in modo automatico una e-mail di conferma dell' operazione effettuata 
- il programma è stato reso il più dinamico possibile. Sulla base delle specifiche esigenze si può creare uno spid per azienda o addirittura uno spid per ogni computer aziendale,
generando dempre un nuovo Sheet per ogni macchina/azienda/gruppo lavorativo

## Note:
**Per rendere più semplice l'accesso all'applicazione:**<br>
* È consigliabile creare un collegamento del file .jar sul desktop.<br>
* Inoltre è consigliabile personalizzarne l'icona dell'applicazione utilizzando il file `Icon.png` contenuto nella stessa directory di questa guida<br>
	1. Tasto destro sul collegamento > Proprietà<br>
	2. Selezionare nella voce 'Esegui:' l'opzione: "Ridotta a icona"<br>
	3. Fare click su "cambia icona..." > Sfoglia > Recarsi nella directory dove è stato posto il programma e selezionare `Icon.png` > Apri. <br>
	4. OK > OK
* Per l'avvio manuale del jar eseguire il seguente comando: ```java -jar HowAppYou.jar```
	
**Per rendere più semplice il controllo dll'applicazione:**<br>
* E' possibile utilizzare il tool : [**Sqlite Browser**](https://sqlitebrowser.org/) per verificare il contenuto corrente del database in SQLite.
