# Svar på spørsmål

*I denne filen legger du inn svar på spørsmålene som stilles i oppgaveteksten, eventuelt kommentarer og informasjon om kreative løsninger*

## Kommentarer
(Eventuelle kommentarer på oppgaven eller koden her).

## Spørsmål

## Oppgave 1 - Abstrakte Ting

### 1.1) 

1. HP - En "ting" trenger en måte og holde styr på om den nesten er borte fra spillverdenen
2. Cash - En verdi som man kan bruke til å gjøre fremskritt i spillet
3. Level (XP) - En visuell oversikt over hvor langt man har kommet
4. Damage - En metode som sier hvor mye skade en "ting" tar
5. Name - For å kunne gi "tingen" et navn

### 1.2) 

Under er det 5 metoder i IItem:

1. getMaxHealth()
2. getDefence()
3. getLongName()
4. handleDamage()
5. isDestroyed()

Alle disse metodene er velidg essensielle for alle spill-elementer av typen "ting". Ved å bruke disse metodene kan man generalisere "ting".


### 1.3)

Her er 3 metoder fra 1.2 som carrot klassen implementerer:

1. getMaxHealth() - Denne implementeres ved å returnere verdien 5 som er gulerotens maxHealth.
2. handleDamage(int) - Denne implementeres ved å trekke fra et tall som den får inn slik at gulerotens liv blir oppdatert
3. getLongName() - Denne implementeres ved å returnere en string "carrot" som er navnet på guleroten.

En ekte egenskap ved en gulerot som blir implementert er at den hetter carrot, en egenskap som ikke er ekte er at gulleroten har "defence".


### 1.4)

Wall, Rabbit, Portal, Spider, Player, Carrot, Dust, Amulet

## Oppgave 2 - The Rabbit

### 2.1)
IActor utvider IItems. Dette betyr at alle klasser som implementerer IActor kan bruke dens metoder inkludert IItems metoder.

### 2.2)
Kanninen bruker doTurn() og denne funksjonen kaller på selectMove() som gir kaninen den retningen den skal gå. Retningen den gir er østover.

## Oppgave 3 - Objektfabrikken

### 3.1)
Player-objectet blir prepresentert ved en "@". Det fant jeg i ItemFactory.java classen og i metoden createItem. 
"Dust" har enda ikke blit implementert i ItemFactory.java klassen så svaret fant jeg i Dust.java i "objects" pakken. Symbolet til dust er ".".

### 3.2)
Man må endre 'R' til 'r' i flere klasser fordi symbolet blir definert i en klasse og brukt i andre klasser.

Problemet med Single Responsibility er nå fikset fordi hvis noen skal endre symbol på et object trenger man kun og endre det i en klasse, og ikke i f.eks. ItemFactory hvor objectene blir lagd.

## Oppgave 5

### 5.3)
Det jeg gjorde på denne oppgaven var å bruke metoden canGo(); for at kaninen ikke ble sittende fast i en vegg.

## Fri oppgave (Oppg. 7)

### Plan
Det første jeg planla var og skifte måten du beveger deg til "wasd" og at man kan gå utav programmet ved å trykke på "ESC". 

Jeg tenker også å implementere det slik at hvis man står i en røyk sky så mister man litt "HP"

Og kansje til slutt implementere et bar objekter. 

### Utførelse
Det var nokså enkelt og skifte til "WASD" styremåte, jeg la også til "ESC" og at man plukker opp med "E" og dropper med "Q". (Fikset også på alle testene for å sjekke om alt virket som det skulle)

Jeg fikk implementert dødlig støv, også får å unngå at du ikke mister liv hele tiden la jeg også til et nytt object - "FaceMask" som man kan ta på seg.

Jeg lagde en "Sword" class som gjør det mulig for at sverd er med i spillet, og satt et sverd ut på kartet

Jeg modifiserte også litt på kartet for å gjøre det passe vanseklig nok.

Etterpå fikset jeg slik at man spiste gulleroten hvis man plukket den opp.

Til slutt gjorde jeg ederkoppen litt smarere slik at den kan angripe spileren, og at de ikke angripe hverandre og sitter fast i hverandre.