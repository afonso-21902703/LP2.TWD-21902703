package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Item {

    // <ID Equipamento> : <ID Tipo> : <x> : <y>

    private int id;
    private int typeId;
    private int x;
    private int y;
    private int itemUses = 3;

    // 3 parte
    private int savedTimes = 0;

    public int getSavedTimes() {
        return savedTimes;
    }

    public void addSavedTimes() {
        this.savedTimes ++;
    }

    public String toStringTiposDeEquipamentoMaisUteis() {
        return this.id + ":" + this.savedTimes;
    }

    // 3 parte


    private boolean itemAvailable = true;

    Item(int id, int typeId, int x, int y) {
        this.id = id;
        this.typeId = typeId;
        this.x = x;
        this.y = y;

        switch (typeId){
            case 0:
                itemUses = 1;
                break;
            case 2:
                itemUses = 3;
                break;
            case 7:
                itemUses = 3;
                break;
            case 8:
                itemUses = 1;
                break;
            case 9:
                itemUses = 1;
                break;
        }
    }

    public void itemUpdate(int x, int y, boolean itemAvailable) {
        this.x = x;
        this.y = y;
        this.itemAvailable = itemAvailable;
    }
    public void increaseItemUses() {
        this.itemUses = 3;
    }
    public void setItemAvailable(boolean itemAvailable) {
        this.itemAvailable = itemAvailable;
    }

    public boolean getItemAvailable() {
        return this.itemAvailable;
    }

    public int getTypeId() {
        return this.typeId;
    }

    public int getItemUses() {
        return this.itemUses;
    }

    public int useItem() {
        return this.itemUses--;
    }

    public int getId() {
        return this.id;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }


}
