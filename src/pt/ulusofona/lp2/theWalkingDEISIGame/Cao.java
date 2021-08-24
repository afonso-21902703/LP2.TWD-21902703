package pt.ulusofona.lp2.theWalkingDEISIGame;

public class Cao extends Creature {

    private int range = 2;

    Cao(int id, int creatureTypeId, String creatureName, int x, int y, int teamdId) {

        this.id = id;
        this.creatureTypeId = creatureTypeId;
        this.creatureName = creatureName;
        this.x = x;
        this.y = y;
        this.teamId = teamdId;
    }

    public String getImagePNG() {
        return "dog.png";
    }

    public Boolean moveCreature(int xD, int yD, boolean isDay) {
        for (int i = 0; i <= range; i++) {
            if (yD == this.y - i && xD == this.x - i) {
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

    public int getRange() {
        return range;
    }

    public String getDirection(int xD, int yD) {

        for (int i = 0; i <= range; i++) {
            if (yD == this.y - i && xD == this.x - i) {
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
}
