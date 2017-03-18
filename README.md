# SuperMemoPolaco
Java + SQLite

I wanted to learn spanish that is why I created this application. It is a kind of super memo app with spanish-polish vocabulary.

Classes:

    1. DatabaseHelper - to handle SQLite DB and db methods.
    2. MainActivity - to handle main page activities.
    3. Espanol and Espanol2 - 2 screens with "spanish" activities. 
    4. Polish and Polish2 - 2 screens with "polish" activities.
    
Layout:

    1. activity_main.xml - main page design.
    2. espanol.xml and espanol2.xml - 2 screens with "spanish" design. 
    3. polish.xml and polish2.xml - 2 screens with "polish" design.
    
    
Algorithm is simple but powerful:     

    1. 6 groups of spanish/polish words.
    2. 5 levels of words familiarity. Every time user guesses word, the word's counter increases by 1 and vice versa when user does not guess the word, counter decreases by 1. 
    3. The higher counter, the smaller probability  word will by drawn. 
    4. Firstly 15 words are drawn from specified group of words, from 5 levels of of words familiarity. 5 words for level 0, 4 words for level 1 etc.
    5. From the group of 15 words 1 word is drawn.
    
    
I still learn spanish :)    
