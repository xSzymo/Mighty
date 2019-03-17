# Mighty
### Introduction
Mirror repository from gitlab - backend part.<br>
Browser game made by me and my dear friend @BobrImperator<br><br>
You can check demo with sample frontend - it also contains sample data<br>
Log in by one of 9 users - <br>
user1/Password123<br>
user2/Password123<br>
user3/Password123<br>
...<br>

<hr>

### Description

We want to create a game, where you can choose sides, you're either Knight or Mercenary.
Knights and Mercenaries tend to fight each other as there's never ending conflict between them.

Knights and Mercenaries can go on missions, to gain experience, and gold, both are needed to become stronger, you pay gold to learn, and get items, and also new crew. 
They can go to work, to gain lots of gold. 

Knights live in castles, and they can attack Mercenaries going down to dungeons, on the other hand, Mercenaries who live in dungeons, can attack castles, which are right above them.
By clearing floors, they gain gold, experience, crew slots and items.

Both Knights and Mercenaries can gather to create guild, guilds will later have their own missions, which would make use of all men from community they create.

There's arena everyone can fight on, Knights vs Knights, Mercenaries vs Mercenaries, Knights vs Mercenaries, Free For All you'd say, they gain fame, gold, and then become distinguished on boards in every town, so everyone knows who they are.
<hr>

### How to run with docker : 
- make sure your ports 8080 and 8000 are free
- copy docker-compose.yml file (src/docker/docker-compose.yml) from this repository to your local environment
- locate docker-compose.yml file and run command in same directory : docker-compose up
- get onto localhost:8000

### Stack :
- Spring Data
- Spring MVC
- Spring Security
- Spring framework
- Swagger
- Junit
  <br><br>
  
<hr>

## FUNCTIONALITIES :

### The Beggining/Kingdom
- [x] Has to pick which side he wants to fight on
- [x] User gains free slot, and money to buy mercenary/knight.

### Profile
- [x] Check all user's data
- [x] Check all champions data
- [x] Take off champion's item
- [x] Equip item for champion
- [x] Add champion's points (armor, mr, etc)
- [x] Change place of items in inventory - ignore


### Options
- [x] Change login
- [x] Change password
- [x] Change e-mail


### Messages
- [x] Create chat with friend
- [x] Add messagess
- [x] Delete messages
- [x] Read messages


### Shop
- [x] Can buy items from shop
- [x] Can sell items to shop


### Guild
- [x] guild view
- [x] Create guild (guild master)
- [x] Delete guild (guild master)
- [x] Add new guild master (guild master)
- [x] Remove member (guild master)
- [x] Leave from guild (member)
- [x] -
- [x] Accept request(invite) to guild (guild master)
- [x] Delete request(invite) to guild (guild master)
- [x] Send request(invite) to guild (member)
- [x] -
- [x] Send messages to guild (message owner, guild master, admin, modifier)
- [x] Read messages in guild (message owner, guild master, admin, modifier)
- [x] Delete messages in guild (message owner, guild master, admin, modifier)
- [x] Add privileges [admin, modifier](/mighty-warriors/back-end-other/wikis/guild%20master%20or%20admin)
- [x] Remove privileges [admin, modifier](/mighty-warriors/back-end-other/wikis/guild%20master%20or%20admin)
- [x] -
- [x] Guild war - ignore


### Work - place where champions can work for hours
- [x] Can go to work
- [x] Can get payment from work
- [x] Can cancel work



### Arena - players ranking based on fights between them
- [x] Can fight on arena
- [x] Can check fight result



### Tavern - place where user can send champions on mission
- [x] Can depart on missions.
- [x] Can check fight result
- [x] Buy mercenaries/knights.


### Dungeon - place where players can send their champions to castles/dungeon

- [x] Can attack castles/dungeon
- [x] Can check fight result

### Leagues - all rankings

- [x] League ranking - players ranking based on their current strenght (refresh every day)
- [x] Arena ranking  - players ranking based on their fights in arena
- [x] Level ranking
- [x] Guild ranking - ignore



### 2. Other:

- [x] Can check active tasks 0 dungeon/tavern/works
- [x] Can check block time champions
- [x] Can login
- [x] Can register
- [x] Can enable account
- [x] Can remind password or login
- [x] Can choose kingdom
