# Amministrator Guide

## Configurazione Sheet

**(0.0) //Pre configurazione\\** Spostare il file batch `"HowAppYou [Run].bat"` nella stessa directory del file `"HowAppYou.jar"`

**(1.0) Per configurare l'applicazione:** Eseguire il file batch `"HowAppYou [Run].bat"`. Esso avvierà il file `"HowAppYou.jar"`

**(2.1) Per creare online un nuovo sheet:** Inserire nel campo _"Name Sheet"_ un nome identificativo per lo Sheet (ES: Azienda01)
in seguito premere il tasto _"done"_

**(2.2) Per impostare un sheet già esistente:** Inserire nel campo _"SPID"_ lo spid identificativo dello sheet in seguito premere il tasto _"done"_<br>
**SPID Esempio:** _https: // docs.google.com/spreadsheets/d/**12LDExGnKgl2QTUaP033afeAw_rxFEGfsuNkweUjnlUw**/edit#gid=0_ <br>
Per facilitare l'operazione, è possibile inserire nella stessa directory del file `"HowAppYou.jar"` il file `HowAppYou.conf` generatosi automaticamente dopo la creazione dello sheet di riferimento seguendo il passo **(2.1)**

**(3.0) Per avviare l'applicazione:** Eseguire il file batch `"HowAppYou [Run].bat"`, quando necessario.

## N.B.:
- Il "making" **(2.1)** o il "setting" **(2.2)** di uno sheet per un'applicazione invierà in modo automatico una e-mail di conferma dell' operazione effettuata 
- il programma è stato reso il più dinamico possibile. Sulla base delle specifiche esigenze si può creare uno spid per azienda o addirittura uno spid per ogni computer aziendale,
generando dempre un nuovo Sheet per ogni macchina/azienda/gruppo lavorativo

## Note:
*Per rendere più semplice l'accesso all'applicazione:*<br>
* È consigliabile creare un collegamento del file .jar sul desktop.<br>
	È inoltre possibile personalizzarne l'icona utilizzando il file "Icon.png" contenuto nella stessa directory di questa guida<br>
	1. Tasto destro sul collegamento > Proprietà<br>
	2. Selezionare nella voce 'Esegui:' l'opzione: "Ridotta a icona"<br>
	3. Fare click su "cambia icona..." > Sfoglia > Recarsi nella directory dove è stato posto il programma e selezionare "Icon.png" > Apri. (Al momento tale icona è salvata nella cartella "AmministratorGuide").<br>
	4. OK > OK
	<br>
*Per rendere più semplice il controllo dll'applicazione:*<br>
* E' possibile utilizzare il tool : [SqliteBrowser](https://sqlitebrowser.org/) per verificare il contenuto corrente del database in SQLite.
