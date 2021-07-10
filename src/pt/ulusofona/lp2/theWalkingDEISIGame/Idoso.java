package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Idoso extends Creature {

    private int range = 1;

    Idoso(int id, int creatureTypeId, String creatureName, int x, int y, int teamdId) {

        this.id = id;
        this.creatureTypeId = creatureTypeId;
        this.creatureName = creatureName;
        this.x = x;
        this.y = y;
        this.teamId = teamdId;
    }

    public String getImagePNG() {
        if(this.teamId == 10) {
            return "idoso.png";
        }else {
            return "idosoZombie.png";
        }
    }

    public Boolean moveCreature(int xD, int yD, boolean isDay) {
        if (!isDay && this.teamId == 10) {
            return false;
        }
        for (int i = 0; i <= range; i++) {
            if (xD == this.x + i && yD == this.y) {
                return true; // lateral -> dir
            } else if (xD == this.x - i && yD == this.y) {
                return true; // lateral <- esq
            } else if (yD == this.y + i && xD == this.x) {
                return true; // baixo
            } else if (yD == this.y - i && xD == this.x) {
                return true;  // cima
            }
        }
        return false;
    }

    public int getRange() {
        return range;
    }

    public String getDirection(int xD, int yD) {

        for (int i = 0; i <= range; i++) {
            if (xD == this.x + i && yD == this.y) {
                return "direita"; // lateral -> dir
            } else if (xD == this.x - i && yD == this.y) {
                return "esquerda"; // lateral <- esq
            } else if (yD == this.y + i && xD == this.x) {
                return "baixo"; // baixo
            } else if (yD == this.y - i && xD == this.x) {
                return "cima";  // cima
            }
        }
        return "";
    }
}
