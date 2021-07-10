package pt.ulusofona.lp2.theWalkingDEISIGame;

public abstract class Creature {

    int id;
    String creatureName;
    int creatureTypeId;
    int x;
    int y;
    int teamId;

    int itemIdEquipped = 0; // 0 = no item
    private int itemTypeIdEquipped = -1; // -1 = no item

    private int numberOfItemsPickedUp = 0;
    private int itemsDestroyed = 0;

    private boolean isInSafeHaven = false;

    private int creatureIsPoisoned = 5; // 5 means its not
    private boolean creatureDiedByPoison = false;

    //3 parte
    private int killedAlive = 0;
    private int killedZombie = 0;

    public int getKilledAlive() {
        return killedAlive;
    }

    public int getKilledZombie() {
        return killedZombie;
    }

    public void addKilledAlive() {
        this.killedAlive++;
    }

    public void addKilledZombie() {
        this.killedZombie++;
    }

    // 3 parte

    // FUNCS OBRIGATORIAS 2 PARTE

    public int getId() {
        return this.id;
    }

    public abstract String getImagePNG();

    public String toString() {
        if (this.teamId == 10 && isInSafeHaven) {
            return this.id + " | " + getTypeInfo() + " | " + getTeamName() + " | " + this.creatureName + " " + this.numberOfItemsPickedUp + " @ " + "A salvo";
        } else if (this.teamId == 10 && creatureDiedByPoison) {
            return this.id + " | " + getTypeInfo() + " | " + getTeamName() + " | " + this.creatureName + " " + this.numberOfItemsPickedUp + " @ " + "Envenenado";
        } else if (this.teamId == 10) {
            return this.id + " | " + getTypeInfo() + " | " + getTeamName() + " | " + this.creatureName + " " + this.numberOfItemsPickedUp + " @ " + "(" + this.x + "," + " " + this.y + ")";
        } else if (this.teamId == 20 && this.x == -1 && this.y == -1) {
            return this.id + " | " + getTypeInfo() + " | " + getTeamName() + " | " + this.creatureName + " " + this.itemsDestroyed + " @ " + "RIP";
        } else {
            return this.id + " | " + getTypeInfo() + " | " + getTeamName() + " | " + this.creatureName + " " + this.itemsDestroyed + " @ " + "(" + this.x + "," + " " + this.y + ")";
        }
    }

    // FIM FUNCS OBRIGATORIAS 2 PARTE

    public abstract Boolean moveCreature(int xD, int yD, boolean isDay);

    public abstract int getRange();

    public abstract String getDirection(int xD, int yD);

    public boolean getIsInSafeHaven() {
        return this.isInSafeHaven;
    }

    public int getCreatureIsPoisoned() {
        return this.creatureIsPoisoned;
    }

    public void setCreatureDiedByPoison(boolean wasPoisoned) {
        this.creatureDiedByPoison = wasPoisoned;
    }

    public boolean getCreatureDiedByPoison() {
        return this.creatureDiedByPoison;
    }

    public void setCreatureIsPoisoned(int poisoned) {
        this.creatureIsPoisoned = poisoned;
    }

    public void decreaseTimeInCreatureIsPoisoned() {
        this.creatureIsPoisoned--;
    }

    public void enterSafeHaven() {
        this.isInSafeHaven = true;
    }

    public void changeTypeIdToOthers() {
        String info = "";
        switch (this.creatureTypeId) {
            case 5:
                creatureTypeId = 0;
                break;
            case 6:
                creatureTypeId = 1;
                break;
            case 7:
                creatureTypeId = 2;
                break;
            case 8:
                creatureTypeId = 3;
                break;
            default:

                break;
        }
    }

    public String getTypeInfo() {
        String info = "";
        switch (this.creatureTypeId) {
            case 0:
                info += "Criança (Zombie)";
                break;
            case 1:
                info += "Adulto (Zombie)";
                break;
            case 2:
                info += "Militar (Zombie)";
                break;
            case 3:
                info += "Idoso (Zombie)";
                break;
            case 4:
                info += "Zombie Vampiro";
                break;
            case 5:
                info += "Criança (Vivo)";
                break;
            case 6:
                info += "Adulto (Vivo)";
                break;
            case 7:
                info += "Militar (Vivo)";
                break;
            case 8:
                info += "Idoso (Vivo)";
                break;
            case 9:
                info += "Cão";
                break;
            case 12:
                info += "Coelho";
                break;
            case 13:
                info += "Zombie Coelho";
                break;
        }
        return info;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    private String getTeamName() {
        if (teamId == 10) {
            return "Os Vivos";
        } else {
            return "Os Outros";
        }
    }

    public int getCreatureTypeId() {
        return this.creatureTypeId;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return this.creatureName;
    }

    public int getTeamId() {
        return this.teamId;
    }

    public void setItem(int item) {
        this.itemTypeIdEquipped = item;
    }

    public int getItem() {
        return this.itemTypeIdEquipped;
    }

    public int getItemIdEquipped() {
        return this.itemIdEquipped;
    }

    public void setItemIdEquipped(int itemEquipped) {
        this.itemIdEquipped = itemEquipped;
    }

    public void addNumberOfItemsPickedUp() {
        numberOfItemsPickedUp++;
    }

    public void addItemsDestroyed() {
        itemsDestroyed++;
    }

    //3o parte
    public String getTypeInfoForStatistics() {
        String info = "";
        switch (this.creatureTypeId) {
            case 0:
                info += "Criança";
                break;
            case 1:
                info += "Adulto";
                break;
            case 2:
                info += "Militar";
                break;
            case 3:
                info += "Idoso";
                break;
            case 4:
                info += "Zombie Vampiro";
                break;
            case 5:
                info += "Criança";
                break;
            case 6:
                info += "Adulto";
                break;
            case 7:
                info += "Militar";
                break;
            case 8:
                info += "Idoso";
                break;
            case 9:
                info += "Cão";
                break;
        }
        return info;
    }

    public int getItemsDestroyed() {
        return itemsDestroyed;
    }

    public String toStringZedsItemsDestroyedList() {
        return getTypeInfoForStatistics() + ":" + this.creatureTypeId + ":" + this.itemsDestroyed;
    }

    public int getInfoForCriaturasMaisEquipadas() {
        if (this.teamId == 10) {
            return numberOfItemsPickedUp;
        } else {
            return itemsDestroyed;
        }
    }

    public String toStringForCriaturasMaisEquipadas() {
        return this.id + ":" + this.creatureName + ":" + getInfoForCriaturasMaisEquipadas();
    }

    public boolean getCreatureOut() {
        if (this.creatureDiedByPoison) {
            return true;
        } else if (this.isInSafeHaven) {
            return true;
        } else {
            return (this.x == -1 && this.y == -1);
        }
    }

    public String toStringOs3ZombiesMaisTramados() {
        return this.id + ":" + this.creatureName + ":" + this.getKilledAlive();
    }

    public String toStringOs3VivosMaisDuros() {
        return this.id + ":" + this.creatureName + ":" + this.getKilledZombie();
    }
}
