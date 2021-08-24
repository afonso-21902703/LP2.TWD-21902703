package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Adulto extends Creature {

    private int range = 2;

    Adulto(int id, int creatureTypeId, String creatureName, int x, int y, int teamdId) {

        this.id = id;
        this.creatureTypeId = creatureTypeId;
        this.creatureName = creatureName;
        this.x = x;
        this.y = y;
        this.teamId = teamdId;
    }

    public String getImagePNG() {
        if(this.teamId == 10) {
            return "adulto.png";
        }else {
            return "adultoZombie.png";
        }
    }

    public Boolean moveCreature(int xD, int yD, boolean isDay) {

        for (int i = 0; i <= range; i++) {
            if (xD == this.x + i && yD == this.y) {
                return true; // lateral -> dir
            } else if (xD == this.x - i && yD == this.y) {
                return true; // lateral <- esq
            } else if (yD == this.y + i && xD == this.x) {
                return true; // baixo
            } else if (yD == this.y - i && xD == this.x) {
                return true;  // cima
            } else if (yD == this.y - i && xD == this.x - i) {
                return true; // diag: cima esq
            } else if (yD == this.y - i && xD == this.x + i) {
                return true; // diag: cima dir
            } else if (yD == this.y + i && xD == this.x - i) {
                return true; // diag: baixo esq
            } else if (yD == this.y + i && xD == this.x + i) {
                return true; // diag: baixo dir
            }
        }

        return false;
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
            } else if (yD == this.y - i && xD == this.x - i) {
                return "cima esquerda"; // diag: cima esq
            } else if (yD == this.y - i && xD == this.x + i) {
                return "cima direita"; // diag: cima dir
            } else if (yD == this.y + i && xD == this.x - i) {
                return "baixo esquerda"; // diag: baixo esq
            } else if (yD == this.y + i && xD == this.x + i) {
                return "baixo direita"; // diag: baixo dir
            }
        }
        return "";
    }


    public int getRange() {
        return range;
    }

}
