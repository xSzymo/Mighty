Będziesz królikiem doświadczalnym.

niezależnie od wybranego kroku pamiętaj by ustawić swoję dane w application.properties w resources (potrzebujesz m.in mysql)

1.Jeżeli chcesz uruchomić z konsoli :

- zainstaluj java i ustaw JAVA_HOME + path
// http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
// https://www.google.pl/search?q=how+to+set+java_home&oq=how+to+set+java_home&gs_l=psy-ab.3..0l4j0i203k1l6.6797.10421.0.10530.26.19.2.0.0.0.342.2238.0j9j1j2.12.0....0...1.1.64.psy-ab..13.13.1898...35i39k1j0i67k1j0i131k1j0i10k1j0i13i30k1j0i22i30k1.0.vAx8D_VWevU

- zainstaluj gradle i ustaw GRADLE_HOME + path 
//https://gradle.org/install/#install
//https://www.tutorialspoint.com/gradle/gradle_installation.htm

- pobierz repo 

z poziomu konsoli wejdź do repo : 
- gradle build
- gradle buildRun


2.Jeżeli chcesz uruchomić z intelj (łatwiejsza i przydatniejsza wersja, w końcu wypadało by miec jakieś przyzwoite środowisko)

- zainstaluj intelj 
//https://www.jetbrains.com/idea/download/#section=windows

- możesz powtórzyć kroki z góry, ale nie muszisz ( wystarczy tylko, że zainstalujesz java'e (i być może gradle już nie pamiętam) natomiast zmiennych środowiskowych nie musisz ustawiać)

- uruchom intelj 
- File > new > project from version control > git > podaj url 
- powinno tam gdzieś być set up your java path
- przejdź przez wszystkie kroki w momencie kiedy pojawi się opcja z "auto import" zaznacz ją (2 albo 3 okienko bodajże)
- jeżeli nie ustawiłeś java path  
    - File > project structure > project sdk > ustaw sobie jdk które pobrałeś 
    - project language level > 8 
    - po lewej stronie project settings > modules > nazwa projektu_main > ustaw language level 8
- src > main > java > game > mightywarriors > MightyWarriorsApplication kliknij dwukrotnie i kliknij zieloną strzałkę po lewej stronie
