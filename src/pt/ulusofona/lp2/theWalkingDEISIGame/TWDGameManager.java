package pt.ulusofona.lp2.theWalkingDEISIGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


public class TWDGameManager {


    private List<Creature> creatures = new ArrayList<>();
    private List<Item> items = new ArrayList<>();
    private List<SafeHaven> safeHavens = new ArrayList<>();
    private List<Integer> idsInSafeHaven = new ArrayList<>();
    private List<String> namesInSafeHaven = new ArrayList<>();
    private int[] worldSize = new int[2];
    private int startingTeam;
    private int currentTeamId;
    private boolean isDay = true;
    static int numberOfTurnsPlayed = 0;
    private boolean humanTransformed = false;
    private int numberOfTurnsWithNoTransformations = 0;
    private int counter = 0;
    private int nextLine = 0;
    public TWDGameManager() {
    }


    public void startGame(File ficheiroInicial) throws InvalidTWDInitialFileException, FileNotFoundException {
        // vars for exceptions
        int numberOfCreaturesRead = 0;
        boolean allCreaturesValid = true;
        String errorLine = "";
        // exception
        if (!ficheiroInicial.exists()) {
            System.out.println("threw !exist");
            throw new FileNotFoundException();
        }
        // count lines
        Scanner sc1 = new Scanner(ficheiroInicial);
        int countFileLines = 0;
        while (sc1.hasNextLine()) {
            String keeper = sc1.nextLine();
            countFileLines++;
        }

        String[] content = new String[countFileLines];
        Scanner sc = new Scanner(ficheiroInicial);
        for (int i = 0; sc.hasNextLine(); i++) {
            content[i] = sc.nextLine();
        }

        String[] worldSizeSplit = content[0].split(" ");
        this.worldSize[0] = Integer.parseInt(worldSizeSplit[0]);
        this.worldSize[1] = Integer.parseInt(worldSizeSplit[0]);
        this.startingTeam = Integer.parseInt(String.valueOf(content[1]));

        int creaturesToBeRead = Integer.parseInt(String.valueOf(content[2]));
        int itemsToBeRead = Integer.parseInt(String.valueOf(content[2 + creaturesToBeRead + 1]));
        int safeHavensToBeRead = Integer.parseInt(String.valueOf(content[2 + creaturesToBeRead + 1 + itemsToBeRead + 1]));

        for (int creaturesRead = 0; creaturesRead < creaturesToBeRead; creaturesRead++) {
            String str = content[3 + creaturesRead];
            errorLine = str; // exception var
            String[] arrOfStr = str.split(" : ");
            try {
                int idGotten = Integer.parseInt(arrOfStr[0]);
                int typeIdGotten = Integer.parseInt(arrOfStr[1]);
                String creatureNameGotten = arrOfStr[2];
                int xGotten = Integer.parseInt(arrOfStr[3]);
                int yGotten = Integer.parseInt(arrOfStr[4]);
                if (arrOfStr.length != 5) {
                    System.out.println("threw size");
                    throw new InvalidTWDInitialFileException(errorLine, true, false);
                }
                numberOfCreaturesRead++; //Exception var
                switch (typeIdGotten) {
                    case 0:
                        creatures.add(new Crianca(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 20));
                        break;
                    case 1:
                        creatures.add(new Adulto(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 20));
                        break;
                    case 2:
                        creatures.add(new Militar(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 20));
                        break;
                    case 3:
                        creatures.add(new Idoso(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 20));
                        break;
                    case 4:
                        creatures.add(new ZombieVampiro(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 20));
                        break;
                    case 5:
                        creatures.add(new Crianca(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 10));
                        break;
                    case 6:
                        creatures.add(new Adulto(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 10));
                        break;
                    case 7:
                        creatures.add(new Militar(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 10));
                        break;
                    case 8:
                        creatures.add(new Idoso(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 10));
                        break;
                    case 9:
                        creatures.add(new Cao(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 10));
                        break;
                    case 12:
                        creatures.add(new Coelho(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 10));
                        break;
                    case 13:
                        creatures.add(new Coelho(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 20));
                        break;
                }
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException i) {
                System.out.println("threw here OBE");
                allCreaturesValid = false;
                throw new InvalidTWDInitialFileException(errorLine, true, allCreaturesValid);
            }
            numberOfCreaturesRead++;
            currentTeamId = startingTeam;
        }

        for (int itemsRead = 0; itemsRead < itemsToBeRead; itemsRead++) {
            String str = content[4 + creaturesToBeRead + itemsRead];
            String[] arrOfStr = str.split(" : ");
            int idGotten = Integer.parseInt(arrOfStr[0]);
            int typeGotten = Integer.parseInt(arrOfStr[1]);
            int xGotten = Integer.parseInt(arrOfStr[2]);
            int yGotten = Integer.parseInt(arrOfStr[3]);
            items.add(new Item(idGotten, typeGotten, xGotten, yGotten));
        }

        for (int safeHavensRead = 0; safeHavensRead < safeHavensToBeRead; safeHavensRead++) {
            String str = content[5 + creaturesToBeRead + itemsToBeRead + safeHavensRead];
            String[] arrOfStr = str.split(" : ");
            int xGotten = Integer.parseInt(arrOfStr[0]);
            int yGotten = Integer.parseInt(arrOfStr[1]);
            safeHavens.add(new SafeHaven(xGotten, yGotten));
        }
        if (numberOfCreaturesRead <= 2) {
            System.out.println("threw here >=2");
            throw new InvalidTWDInitialFileException(errorLine, false, allCreaturesValid);
        }
        if (!allCreaturesValid) {
            System.out.println("threw here invalid");
            throw new InvalidTWDInitialFileException(errorLine, true, allCreaturesValid);
        }
    }       //DONE

    public int[] getWorldSize() {
        return this.worldSize;
    }      //DONE

    public int getInitialTeam() {
        return this.startingTeam;
    }     //DONE

    public List<Creature> getCreatures() {
        return creatures;
    }      //DONE

    public boolean move(int xO, int yO, int xD, int yD) {

        int creatureRange = 0;
        String direction = "";
        int playingTeamId = -1;
        String playingCreatureType = null;
        int playingCreatureItemTypeId = -1;
        String targetIsAlive = null;
        int targetCreatureItemTypeId = -1;
        boolean playingPickedUpThisRound = false;
        int targetCreatureType = -1;
        int targetCreatureItemId = 1;
        int playingCreatureItemId = 1;

        //Quem está a jogar?
        for (Creature creature : creatures) {
            if (creature.getX() == xO && creature.getY() == yO) {
                playingTeamId = creature.getTeamId();
                creatureRange = creature.getRange();
                direction = creature.getDirection(xD, yD);
                playingCreatureType = creature.getTypeInfo();
                playingCreatureItemTypeId = creature.getItem();
                playingCreatureItemId = creature.getItemIdEquipped();
            }
        }

        //empty pos?
        if (playingTeamId == -1) {
            return false;
        }

        //opposite team moves?
        if (this.currentTeamId != playingTeamId) {
            return false;
        }

        //mexer a creature de forma correcta
        for (Creature creature : creatures) {
            if (creature.getX() == xO && creature.getY() == yO) {
                if (!creature.moveCreature(xD, yD, isDay())) {
                    return false;
                }
            }
        }

        //Tentativa de movimento para fora do mapa
        if (xD > worldSize[0] || xD < (0)) {
            return false;
        }
        if (yD > worldSize[1] || yD < (0)) {
            return false;
        }

        //Destino Ocupado? Caminho Livre?
        boolean humanKilled = false;
        boolean humanDefended = false;
        for (int i = 1; i <= creatureRange; i++) {
            //colado
            if (xD == (xO + i) && yD == yO) {
                break;
            }
            if (xD == (xO - i) && yD == yO) {
                break;
            }
            if (yD == (yO + i) && xD == xO) {
                break;
            }
            if (yD == (yO - i) && xD == xO) {
                break;
            }
            if (yD == (yO - i) && xD == xO - i) {
                break;
            }
            if (yD == (yO - i) && xD == xO + i) {
                break;
            }
            if (yD == (yO + i) && xD == xO - i) {
                break;
            }
            if (yD == (yO + i) && xD == xO + i) {
                break;
            }
            switch (direction) {
                case "direita":
                    for (Creature creature : creatures) {
                        if (creature.getX() == (xO + i) && creature.getY() == yO) {
                            return false;
                        }
                    }// lateral -> dir
                    break;
                case "esquerda":
                    for (Creature creature : creatures) {
                        if (creature.getX() == (xO - i) && creature.getY() == yO) {
                            return false;
                        }
                    }// lateral <- esq
                    break;
                case "baixo":
                    for (Creature creature : creatures) {
                        if (creature.getY() == (yO + i) && creature.getX() == xO) {
                            return false;
                        }
                    }// cima
                    break;
                case "cima":
                    for (Creature creature : creatures) {
                        if (creature.getY() == (yO - i) && creature.getX() == xO) {
                            return false;
                        }
                    }// baixo
                    break;
                case "cima esquerda":
                    for (Creature creature : creatures) {
                        if (creature.getY() == (yO - i) && creature.getX() == xO - i) {
                            return false;
                        }
                    }// diag: cima esq
                    break;
                case "cima direita":
                    for (Creature creature : creatures) {
                        if (creature.getY() == (yO - i) && creature.getX() == xO + i) {
                            return false;
                        }
                    }// diag: cima dir
                    break;
                case "baixo esquerda":
                    for (Creature creature : creatures) {
                        if (creature.getY() == (yO + i) && creature.getX() == xO - i) {
                            return false;
                        }
                    }// diag: baixo esq
                    break;
                case "baixo direita":
                    for (Creature creature : creatures) {
                        if (creature.getY() == (yO + i) && creature.getX() == xO + i) {
                            return false;
                        }
                    }// diag: baixo dir
                    break;
            } // creatures
            switch (direction) {
                case "direita":
                    for (Item item : items) {
                        if (item.getX() == (xO + i) && item.getY() == yO) {
                            return false;
                        }
                    }// lateral -> dir
                    break;
                case "esquerda":
                    for (Item item : items) {
                        if (item.getX() == (xO - i) && item.getY() == yO) {
                            return false;
                        }
                    }// lateral <- esq
                    break;
                case "baixo":
                    for (Item item : items) {
                        if (item.getY() == (yO + i) && item.getX() == xO) {
                            return false;
                        }
                    }// cima
                    break;
                case "cima":
                    for (Item item : items) {
                        if (item.getY() == (yO - i) && item.getX() == xO) {
                            return false;
                        }
                    }// baixo
                    break;
                case "cima esquerda":
                    for (Item item : items) {
                        if (item.getY() == (yO - i) && item.getX() == xO - i) {
                            return false;
                        }
                    }// diag: cima esq
                    break;
                case "cima direita":
                    for (Item item : items) {
                        if (item.getY() == (yO - i) && item.getX() == xO + i) {
                            return false;
                        }
                    }// diag: cima dir
                    break;
                case "baixo esquerda":
                    for (Item item : items) {
                        if (item.getY() == (yO + i) && item.getX() == xO - i) {
                            return false;
                        }
                    }// diag: baixo esq
                    break;
                case "baixo direita":
                    for (Item item : items) {
                        if (item.getY() == (yO + i) && item.getX() == xO + i) {
                            return false;
                        }
                    }// diag: baixo dir
                    break;
            } // items
            switch (direction) {
                case "direita":
                    for (SafeHaven safeHaven : safeHavens) {
                        if (safeHaven.getX() == (xO + i) && safeHaven.getY() == yO) {
                            return false;
                        }
                    }// lateral -> dir
                    break;
                case "esquerda":
                    for (SafeHaven safeHaven : safeHavens) {
                        if (safeHaven.getX() == (xO - i) && safeHaven.getY() == yO) {
                            return false;
                        }
                    }// lateral <- esq
                    break;
                case "baixo":
                    for (SafeHaven safeHaven : safeHavens) {
                        if (safeHaven.getY() == (yO + i) && safeHaven.getX() == xO) {
                            return false;
                        }
                    }// cima
                    break;
                case "cima":
                    for (SafeHaven safeHaven : safeHavens) {
                        if (safeHaven.getY() == (yO - i) && safeHaven.getX() == xO) {
                            return false;
                        }
                    }// baixo
                    break;
                case "cima esquerda":
                    for (SafeHaven safeHaven : safeHavens) {
                        if (safeHaven.getY() == (yO - i) && safeHaven.getX() == xO - i) {
                            return false;
                        }
                    }// diag: cima esq
                    break;
                case "cima direita":
                    for (SafeHaven safeHaven : safeHavens) {
                        if (safeHaven.getY() == (yO - i) && safeHaven.getX() == xO + i) {
                            return false;
                        }
                    }// diag: cima dir
                    break;
                case "baixo esquerda":
                    for (SafeHaven safeHaven : safeHavens) {
                        if (safeHaven.getY() == (yO + i) && safeHaven.getX() == xO - i) {
                            return false;
                        }
                    }// diag: baixo esq
                    break;
                case "baixo direita":
                    for (SafeHaven safeHaven : safeHavens) {
                        if (safeHaven.getY() == (yO + i) && safeHaven.getX() == xO + i) {
                            return false;
                        }
                    }// diag: baixo dir
                    break;
            } // SafeHavens
        }

        for (Creature creature : creatures) {
            if (creature.getX() == xD && creature.getY() == yD) {
                if (creature.getTeamId() == 10) {
                    targetIsAlive = "true";
                } else {
                    targetIsAlive = "false";
                }
                targetCreatureItemTypeId = creature.getItem();
                targetCreatureType = creature.getCreatureTypeId();
                targetCreatureItemId = creature.getItemIdEquipped();
                // descobrir target

                if (targetIsAlive.equals("false") && playingTeamId == 10) { //  (HUMANO -> ZOMBIE)
                    switch (playingCreatureItemTypeId) {
                        case 1:
                            if (playingCreatureType.equals("Criança (Vivo)") && targetCreatureType != 0) {
                                return false;
                            }
                        case 2:
                        case 6:
                        case 10:
                            //zombue killed
                            for (Item item : items) {
                                if (item.getId() == playingCreatureItemId) {
                                    item.useItem();
                                }
                            }
                            // Stastics killed
                            for (Creature targetCreatureForStatistic : creatures) {
                                if (targetCreatureForStatistic.getX() == xO && targetCreatureForStatistic.getY() == yO) {
                                    targetCreatureForStatistic.addKilledZombie();
                                }
                            }
                            creature.setX(-1);
                            creature.setY(-1);
                            break;
                        default:
                            return false;
                    }
                }
                if (targetIsAlive.equals("false") && playingTeamId == 20) { // (ZOMBIE -> ZOMBIE)
                    return false; // Zombie contra zombie nao pode
                }
                if (targetIsAlive.equals("true") && playingTeamId == 10) { //  (HUMANO -> HUMANO)
                    return false; // humano contra humano n pode
                }

                if (targetIsAlive.equals("true") && playingTeamId == 20) { // (ZOMBIE -> HUMANO)
                    boolean lastMinuteBug = false;
                    if (targetCreatureType == 9) {
                        return false;
                    }
                    switch (targetCreatureItemTypeId) {
                        case 5:
                            lastMinuteBug = true;
                            if (!(playingCreatureType.equals("Zombie Vampiro"))) {
                                humanKilled = true;
                                humanTransformed = true;
                                break;
                            }
                        case 4:
                            if (!lastMinuteBug) {
                                if (!(playingCreatureType.equals("Idoso (Zombie)"))) {
                                    humanKilled = true;
                                    humanTransformed = true;
                                    break;
                                }
                            }
                        case 0:
                        case 3:
                        case 7:
                        case 8:
                            //humano defende ataque
                            //yes
                            for (Item item : items) {
                                if (item.getId() == targetCreatureItemId) {
                                    item.useItem();
                                    //add statistic item
                                    item.addSavedTimes();
                                }
                            }
                            humanDefended = true;
                            break;
                        case 1:
                            if (targetCreatureType == 5 && !(playingCreatureType.equals("Criança (Zombie)"))) {
                                humanKilled = true;
                                humanTransformed = true;
                                break;
                            }
                        case 2:
                        case 6:
                        case 10:
                            //humano mata zombie em retaliacao
                            for (Item item : items) {
                                if (item.getId() == targetCreatureItemId) {
                                    item.useItem();
                                    //add statistic item
                                    item.addSavedTimes();
                                }
                            }
                            //add statistic killed
                            for (Creature targetCreatureForStatistic : creatures) {
                                if (targetCreatureForStatistic.getX() == xD && targetCreatureForStatistic.getY() == yD) {
                                    targetCreatureForStatistic.addKilledZombie();
                                }
                            }
                            //zombie killed
                            for (Creature creatureToBeRemoved : creatures) {
                                if (creatureToBeRemoved.getX() == xO && creatureToBeRemoved.getY() == yO) {
                                    creatureToBeRemoved.setX(-1);
                                    creatureToBeRemoved.setY(-1);
                                }
                            }
                            break;
                        default:
                            humanKilled = true;
                            humanTransformed = true;
                    }
                }
            }
        } //battle


        //Equipamentos
        //Remove broken itens
        for (Item item : items) {
            switch (item.getTypeId()) {
                case 0:
                case 2:
                case 7:
                case 8:
                    if (item.getItemUses() <= 0) {
                        for (Creature creature : creatures) {
                            if (creature.getItemIdEquipped() == item.getId()) {
                                if (creature.getItem() == 9 || creature.getItem() == 8) {
                                    //meh
                                } else {
                                    creature.setItem(-1);
                                    creature.setItemIdEquipped(0);
                                }
                            }
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        //PICK UP / DESTROY
        int placeHasItemId;
        int placeHasItemType;

        for (Item item : items) {
            if (item.getX() == xD && item.getY() == yD && item.getItemAvailable()) {

                placeHasItemId = item.getId();
                placeHasItemType = item.getTypeId();

                if (playingTeamId == 20) { //  ZOMBIE
                    if (playingCreatureType.equals("Coelho (Zombie)")) {
                        return false;
                    }
                    if (placeHasItemType == 5 && playingCreatureType.equals("Zombie Vampiro")) {
                        return false; //Vampiro nao pode ir para casas com alho
                    }
                    if (placeHasItemType == 8) {
                        return false; //Zombie nao pode ir para casas com veneno
                    }
                    //Destroy item
                    // must be removed?
                    //AS
                    item.itemUpdate(-1, -1, false);
                    for (Creature creature : creatures) {
                        if (creature.getX() == xO && creature.getY() == yO) {
                            creature.addItemsDestroyed();
                        }
                    }
                } else { //  VIVO
                    if (playingCreatureType.equals("Coelho (Vivo)")) {
                        return false;
                    }
                    if (placeHasItemType == 9 && playingCreatureItemTypeId != 8) {
                        return false; //nao pode apanhar antidoto se n tiver frasco de veneno
                    }
                    if (placeHasItemType == 11 && playingCreatureItemTypeId != 2) {
                        return false; //nao pode apanhar bala se n tiver pistola
                    }
                    for (Creature creature : creatures) {
                        if (creature.getX() == xO && creature.getY() == yO) {
                            if (creature.getItem() != -1) {
                                //human drops
                                for (Item itemToChange : items) {
                                    if (itemToChange.getId() == creature.itemIdEquipped) {
                                        itemToChange.itemUpdate(xO, yO, true);
                                    }
                                }
                            }
                            //human pick up
                            creature.setItem(placeHasItemType);
                            creature.setItemIdEquipped(placeHasItemId);
                            creature.addNumberOfItemsPickedUp();
                            if (item.getTypeId() == 8) { // get veneno
                                if (item.getItemUses() == 1) {
                                    creature.setCreatureIsPoisoned(3); // 3 turns to live
                                }
                                item.useItem();
                            }
                            if (item.getTypeId() == 9) { // antidoto da
                                creature.setCreatureIsPoisoned(5); // 5 means safe
                            }
                            if (playingCreatureType.equals("Idoso (Vivo)")) {
                                item.itemUpdate(xD, yD, false);
                                playingPickedUpThisRound = true;
                            }else {
                                // must be removed?
                                item.itemUpdate(-1, -1, false);
                            }
                        }
                    }
                }
            }
        }

        //old man drops on move
        if (playingCreatureType.equals("Idoso (Vivo)") && !playingPickedUpThisRound) {
            for (Creature creature : creatures) {
                if (creature.getX() == xO && creature.getY() == yO) {
                    creature.setItem(-1);
                    creature.setItemIdEquipped(0);
                }
            }
            for (Item item : items) {
                if (item.getX() == xO && item.getY() == yO) {
                    item.setItemAvailable(true);
                }
            }
        }

        //Safe Haven
        boolean isInSafeHaven = false;
        for (SafeHaven safeHaven : safeHavens) {
            if (safeHaven.getX() == xD && safeHaven.getY() == yD) {
                if (playingTeamId == 10) {
                    isInSafeHaven = true;
                } else {
                    return false; // zombie cant go in
                }
            }
        }

        // count poison turns
        // kill poisoned humans
        for (Creature creature : creatures) {
            if (creature.getCreatureIsPoisoned() != 5) {
                creature.decreaseTimeInCreatureIsPoisoned();
            }
            if (creature.getCreatureIsPoisoned() <= 0 && !creature.getIsInSafeHaven()) {
                creature.setCreatureIsPoisoned(5);
                creature.setCreatureDiedByPoison(true);
                creature.setItem(-1);
                creature.setItemIdEquipped(0);
                creature.setX(-1);
                creature.setY(-1);
            }
        }

        //change team
        if (this.currentTeamId == 10) {
            this.currentTeamId = 20;
        } else {
            this.currentTeamId = 10;
        }

        //add turn played
        this.numberOfTurnsPlayed++;

        if (!humanKilled) {
            this.numberOfTurnsWithNoTransformations++;
        } else {
            this.numberOfTurnsWithNoTransformations = 0;
        }

        //update pos
        for (Creature creature : creatures) {
            if (creature.getX() == xO && creature.getY() == yO) {
                if (isInSafeHaven) {
                    creature.enterSafeHaven();
                    creature.setX(-1);
                    creature.setY(-1);
                    idsInSafeHaven.add(creature.getId());
                    namesInSafeHaven.add(creature.getName());
                } else {
                    if (humanKilled) { // human dead
                        for (Creature creatureToChange : creatures) {
                            if (creatureToChange.getX() == xD && creatureToChange.getY() == yD) {
                                creatureToChange.setTeamId(20);
                                creatureToChange.changeTypeIdToOthers();
                                creatureToChange.setItem(-1);
                                creatureToChange.setItemIdEquipped(0);
                            }
                        }
                        // Stastics killed add on zombie
                        for (Creature targetCreatureForStatistic : creatures) {
                            if (targetCreatureForStatistic.getX() == xO && targetCreatureForStatistic.getY() == yO) {
                                targetCreatureForStatistic.addKilledAlive();
                            }
                        }
                    } else if (humanDefended) {
                        return true;
                    } else {
                        creature.setX(xD);
                        creature.setY(yD);
                    }
                }
            }
        }

        return true;
    }

    public boolean gameIsOver() {
        //segunda observação end game
        boolean aconteceu = false;
        List<Creature> zombiesLeftList = new ArrayList<>();
        for(Creature creature : creatures) {
            if (creature.getTeamId() == 20 ){
                if (creature.getX() != -1 && !creature.getIsInSafeHaven()
                        && !creature.getCreatureDiedByPoison()){
                    zombiesLeftList.add(creature);
                }

            }
        }
        for(Creature creature : zombiesLeftList) {
            if (creature.getCreatureTypeId() == 4 && isDay == true && zombiesLeftList.size() == 1){
                return true;
            }
        }
        List<Creature> humansLeftList = new ArrayList<>();
        for(Creature creature : creatures) {
            if (creature.getTeamId() == 10 ){
                if (creature.getX() != -1 && !creature.getIsInSafeHaven()
                        && !creature.getCreatureDiedByPoison()){
                    humansLeftList.add(creature);
                }

            }
        }
        for(Creature creature : humansLeftList) {
            if (creature.getCreatureTypeId() == 8 && isDay == false && humansLeftList.size() == 1){
                return true;
            }
        }
        // 3 dias e 3 noite sem ningume morrer
        if (numberOfTurnsWithNoTransformations == 12) {
            //getGameResults().forEach(System.out::println);
            return true;
        }
        //zombies vivos?
        boolean zombiesLeft = false;
        for (Creature creature : creatures) {
            if ((creature.getCreatureTypeId() < 5 || creature.getCreatureTypeId() == 13) && !creature.getIsInSafeHaven()
                    && !creature.getCreatureDiedByPoison() && creature.getX() != -1 && creature.getY() != -1) {
                zombiesLeft = true;
                break;
            }
        }
        if (!zombiesLeft) {
            return true;
        }
        // alguem vivo?
        boolean humansLeft = false;
        for (Creature creature : creatures) {
            if ((creature.getCreatureTypeId() >= 5 || creature.getCreatureTypeId() == 12) && !creature.getIsInSafeHaven()
                    && !creature.getCreatureDiedByPoison()) {
                humansLeft = true;
                break;
            }
        }
        if (!humansLeft) {
            return true;
        }

        return false;
    } //DONE

    public List<String> getAuthors() {
        List<String> authors = new ArrayList<String>();
        authors.add("Tomás Cruz a21803655");
        authors.add("Afonso Costa a21902703");
        return authors;
    }       //DONE

    public int getCurrentTeamId() {
        return this.currentTeamId;
    }        //DONE

    public int getElementId(int x, int y) {
        for (Creature creature : creatures) {
            if (creature.getX() == x && creature.getY() == y) {
                return creature.getId();
            }
        }
        for (Item item : items) {
            if (item.getX() == x && item.getY() == y) {
                return item.getId();
            }
        }
        //vazio e porta
        return 0;
    }                  //DONE

    public List<String> getGameResults() {
        List<String> results = new ArrayList<>();
        results.add("Nr. de turnos terminados:");
        results.add(String.valueOf(numberOfTurnsPlayed));
        results.add("");
        results.add("Ainda pelo bairo:");
        results.add("");
        results.add("OS VIVOS");
        for (Creature creature : creatures) {
            if (creature.getTeamId() == 10 && !creature.getIsInSafeHaven()) {
                results.add((creature.getId()) + " " + creature.getName());
            }
        }
        results.add("");
        results.add("OS OUTROS");
        for (Creature creature : creatures) {
            if (creature.getTeamId() == 20) {
                results.add(creature.getId() + " (antigamente conhecido como " + creature.getName() + ")");
            }
        }
        results.add("");
        results.add("Num safe haven:");
        results.add("");
        results.add("OS VIVOS");
        for (int i = 0; i < idsInSafeHaven.size(); i++) {
            results.add((idsInSafeHaven.get(i)) + " " + namesInSafeHaven.get(i));
        }
        results.add("");
        results.add("Envenenados / Destruidos");
        results.add("");
        results.add("OS VIVOS");
        for (Creature creature : creatures) {
            if (creature.getTeamId() == 10 && !creature.getIsInSafeHaven() && creature.getX() == -1 && creature.getY() == -1) {
                results.add((creature.getId()) + " " + creature.getName());
            }
        }
        results.add("");
        results.add("OS OUTROS");
        for (Creature creature : creatures) {
            if (creature.getTeamId() == 20 && creature.getX() == -1 && creature.getY() == -1) {
                results.add(creature.getId() + " (antigamente conhecido como " + creature.getName() + ")");
            }
        }
        return results;
    }

    public boolean isDay() {
        /*
        System.out.println("nextLine = " + nextLine);
        System.out.println("counter = " + counter);
        System.out.println("dia = " + isDay);
        System.out.println(" ");
        */
        if (this.nextLine == 0) {
            this.counter += 1;
            if (this.counter == 4) {
                this.counter = 0;
                this.nextLine = 1;
                this.isDay = true;

            } else {
                this.isDay = true;
            }
        } else if (this.nextLine == 1) {
            this.counter += 1;
            if (this.counter == 4) {
                this.counter = 0;
                this.nextLine = 0;
                this.isDay = false;
            } else {
                this.isDay = false;
            }
        } return this.isDay;
    }

    public int getEquipmentId(int creatureId) {
        for (Creature creature : creatures) {
            if (creature.getId() == creatureId) {
                return creature.getItemIdEquipped();
            }
        }
        return 0;
    }             //DONE

    public List<Integer> getIdsInSafeHaven() {
        return idsInSafeHaven;
    }             //DONE

    public boolean isDoorToSafeHaven(int x, int y) {
        for (SafeHaven safeHaven : safeHavens) {
            if (safeHaven.getX() == x && safeHaven.getY() == y) {
                return true;
            }
        }
        return false;
    }             //DONE

    public int getEquipmentTypeId(int equipmentId) {
        for (Item item : items) {
            if (item.getId() == equipmentId) {
                return item.getTypeId();
            }
        }
        return 0;
    }              //DONE

    public String getEquipmentInfo(int equipmentId) {
        String equipmentInfoUses = "";
        int equipmentTypeId = -1;
        for (Item item : items) {
            if (item.getId() == equipmentId) {
                equipmentTypeId = item.getTypeId();
                equipmentInfoUses += item.getItemUses();
            }
        }
        String equipmentInfo = "";
        switch (equipmentTypeId) {
            case 0:
                equipmentInfo += "Escudo de Madeira | ";
                equipmentInfo += equipmentInfoUses;
                break;
            case 1:
                equipmentInfo += "Espada Hattori Hanzo";
                break;
            case 2:
                equipmentInfo += "Pistola Walther PPK | ";
                equipmentInfo += equipmentInfoUses;
                break;
            case 3:
                equipmentInfo += "Escudo Táctico";
                break;
            case 4:
                equipmentInfo += "Revista Maria";
                break;
            case 5:
                equipmentInfo += "Cabeça de Alho";
                break;
            case 6:
                equipmentInfo += "Estaca de Madeira";
                break;
            case 7:
                equipmentInfo += "Garrafa de Lixívia (1 litro) | ";
                equipmentInfo += equipmentInfoUses;
                break;
            case 8:
                equipmentInfo += "Veneno | ";
                equipmentInfo += equipmentInfoUses;
                break;
            case 9:
                equipmentInfo += "Antídoto | ";
                equipmentInfo += equipmentInfoUses;
                break;
            case 10:
                equipmentInfo += "Beskar Helmet";
                break;
            case 11:
                equipmentInfo += "Bala";
                break;
        }
        return equipmentInfo;
    }    //DONE


    public boolean saveGame(File fich) {
        //Create file if needed
        try {
            File myObj = new File(String.valueOf(fich));
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("Erro a criar.");
            return false;
        }
        // write info
        try {
            FileWriter myWriter = new FileWriter(fich);
            String newLine = System.getProperty("line.separator");
            myWriter.write(this.worldSize[0] + " " + this.worldSize[1]);
            myWriter.write(newLine);
            myWriter.write(String.valueOf(this.currentTeamId));
            myWriter.write(newLine);
            myWriter.write(String.valueOf(this.creatures.size()));
            for (Creature creature : creatures) {
                myWriter.write(newLine);
                myWriter.write(creature.getId() + " : " + creature.getCreatureTypeId() + " : " + creature.getName() + " : " + creature.getX() + " : " + creature.getY());
                // ^ <ID Criatura> : <ID tipo> : <Nome Criatura> : <x> : <y> Change to save all info
            }
            myWriter.write(newLine);
            myWriter.write(String.valueOf(this.items.size()));
            for (Item item : items) {
                myWriter.write(newLine);
                myWriter.write(item.getId() + " : " + item.getTypeId() + " : " + item.getX() + " : " + item.getY());
                // ^ <ID Equipamento> : <ID Tipo> : <x> : <y> Change to save all info
            }
            myWriter.write(newLine);
            myWriter.write(String.valueOf(this.safeHavens.size()));
            for (SafeHaven safeHaven : safeHavens) {
                myWriter.write(newLine);
                myWriter.write(safeHaven.getX() + " : " + safeHaven.getY());
                // ^ <x> : <y> Change to save all info
            }
            myWriter.close();
            System.out.println("done writing");
            //done
        } catch (IOException e) {
            System.out.println("Erro escrever");
            return false;
        }
        return true;
    } // must change

    public boolean loadGame(File fich) {
        try {
            // count lines
            Scanner sc1 = new Scanner(fich);
            int countFileLines = 0;
            while (sc1.hasNextLine()) {
                String keeper = sc1.nextLine();
                countFileLines++;
            }

            String[] content = new String[countFileLines];
            Scanner sc = new Scanner(fich);
            for (int i = 0; sc.hasNextLine(); i++) {
                content[i] = sc.nextLine();
            }

            String[] worldSizeSplit = content[0].split(" ");
            this.worldSize[0] = Integer.parseInt(worldSizeSplit[0]);
            this.worldSize[1] = Integer.parseInt(worldSizeSplit[0]);
            this.startingTeam = Integer.parseInt(String.valueOf(content[1]));

            int creaturesToBeRead = Integer.parseInt(String.valueOf(content[2]));
            int itemsToBeRead = Integer.parseInt(String.valueOf(content[2 + creaturesToBeRead + 1]));
            int safeHavensToBeRead = Integer.parseInt(String.valueOf(content[2 + creaturesToBeRead + 1 + itemsToBeRead + 1]));

            for (int creaturesRead = 0; creaturesRead < creaturesToBeRead; creaturesRead++) {
                String str = content[3 + creaturesRead];
                String[] arrOfStr = str.split(" : ");
                int idGotten = Integer.parseInt(arrOfStr[0]);
                int typeIdGotten = Integer.parseInt(arrOfStr[1]);
                String creatureNameGotten = arrOfStr[2];
                int xGotten = Integer.parseInt(arrOfStr[3]);
                int yGotten = Integer.parseInt(arrOfStr[4]);
                switch (typeIdGotten) {
                    case 0:
                        creatures.add(new Crianca(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 20));
                        break;
                    case 1:
                        creatures.add(new Adulto(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 20));
                        break;
                    case 2:
                        creatures.add(new Militar(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 20));
                        break;
                    case 3:
                        creatures.add(new Idoso(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 20));
                        break;
                    case 4:
                        creatures.add(new ZombieVampiro(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 20));
                        break;
                    case 5:
                        creatures.add(new Crianca(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 10));
                        break;
                    case 6:
                        creatures.add(new Adulto(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 10));
                        break;
                    case 7:
                        creatures.add(new Militar(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 10));
                        break;
                    case 8:
                        creatures.add(new Idoso(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 10));
                        break;
                    case 9:
                        creatures.add(new Cao(idGotten, typeIdGotten, creatureNameGotten, xGotten, yGotten, 10));
                        break;
                }
                currentTeamId = startingTeam;
            }

            for (int itemsRead = 0; itemsRead < itemsToBeRead; itemsRead++) {
                String str = content[4 + creaturesToBeRead + itemsRead];
                String[] arrOfStr = str.split(" : ");
                int idGotten = Integer.parseInt(arrOfStr[0]);
                int typeGotten = Integer.parseInt(arrOfStr[1]);
                int xGotten = Integer.parseInt(arrOfStr[2]);
                int yGotten = Integer.parseInt(arrOfStr[3]);
                items.add(new Item(idGotten, typeGotten, xGotten, yGotten));
            }

            for (int safeHavensRead = 0; safeHavensRead < safeHavensToBeRead; safeHavensRead++) {
                String str = content[5 + creaturesToBeRead + itemsToBeRead + safeHavensRead];
                String[] arrOfStr = str.split(" : ");
                int xGotten = Integer.parseInt(arrOfStr[0]);
                int yGotten = Integer.parseInt(arrOfStr[1]);
                safeHavens.add(new SafeHaven(xGotten, yGotten));
            }
            return true;
        } catch (java.io.FileNotFoundException e) {
            return false;
        }
    } // must change

    public String[] popCultureExtravaganza() {
        return new String[]{"Resident Evil", "Evil Dead", "I Am Legend", "I Am Legend", "Dragon Ball", "World War Z", "Mandalorians"
                , "1972", "Kill Bill", "1978", "Bond, James Bond.", "Lost", "Chocho", "Farrokh Bulsara"};
    }
    //end

    // 3 parte

    public Map<String, List<String>> getGameStatistics() {
        Map<String, List<String>> statistics = new HashMap<String, List<String>>();
        //_________________________________________________________________________________________________

        List<String> listos3ZombiesMaisTramados = creatures.stream()
                .filter(creature -> creature.getTeamId() == 20 && creature.getKilledAlive() != 0)
                .sorted(Comparator.comparing(Creature::getKilledAlive)
                        .reversed()
                        .thenComparing(Creature::getId))
                .limit(3)
                .map(Creature::toStringOs3ZombiesMaisTramados)
                .collect(toList());

        statistics.put("os3ZombiesMaisTramados", listos3ZombiesMaisTramados);

        //_________________________________________________________________________________________________

        List<String> listos3VivosMaisDuros = creatures.stream()
                .filter(creature -> creature.getTeamId() == 10 && creature.getKilledZombie() != 0)
                .sorted(Comparator.comparing(Creature::getKilledZombie)
                        .reversed()
                        .thenComparing(Creature::getId))
                .limit(3)
                .map(Creature::toStringOs3VivosMaisDuros)
                .collect(toList());

        statistics.put("os3VivosMaisDuros", listos3VivosMaisDuros);

        //_________________________________________________________________________________________________

        List<String> listtiposDeEquipamentoMaisUteis = items.stream()
                .filter(item -> item.getSavedTimes() != 0)
                .sorted(Comparator.comparing(Item::getSavedTimes)
                        .reversed()
                        .thenComparing(Item::getId))
                .map(Item::toStringTiposDeEquipamentoMaisUteis)
                .collect(toList());

        statistics.put("tiposDeEquipamentoMaisUteis", listtiposDeEquipamentoMaisUteis);

        // IMPLEMENT UP here mf
        //_________________________________________________________________________________________________
        // FIX
        List<String> listTiposDeZombieESeusEquipamentosDestruidos =
                creatures.stream()
                        .filter(creature -> creature.getTeamId() == 20) // team id 20 = Zombies
                        .sorted(Comparator.comparing(Creature::getItemsDestroyed).reversed())
                        .map(creature -> (creature.getTypeInfoForStatistics() + "")) // ter que fazer as contas
                        .collect(toList());
        statistics.put("tiposDeZombieESeusEquipamentosDestruidos", listTiposDeZombieESeusEquipamentosDestruidos); // ter que juntar os tipo

        //_________________________________________________________________________________________________

        List<String> listCriaturasMaisEquipadas = creatures.stream()
                .filter(creature -> !creature.getCreatureOut())
                .sorted(Comparator.comparing(Creature::getInfoForCriaturasMaisEquipadas)
                        .reversed()
                        .thenComparing(Creature::getId))
                .limit(5)
                .map(Creature::toStringForCriaturasMaisEquipadas)
                .collect(toList());

        statistics.put("criaturasMaisEquipadas", listCriaturasMaisEquipadas);

        //_________________________________________________________________________________________________
        return statistics;
    }

}
