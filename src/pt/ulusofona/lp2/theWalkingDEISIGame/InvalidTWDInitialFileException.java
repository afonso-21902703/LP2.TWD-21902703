package pt.ulusofona.lp2.theWalkingDEISIGame;

public class InvalidTWDInitialFileException extends Exception {

    private String linhaErro;
    private boolean numberOfCreaturesRead;
    private  boolean allCreaturesValid;

    InvalidTWDInitialFileException(String linhaErro, boolean numberOfCreaturesRead, boolean allCreaturesValid){
        this.linhaErro = linhaErro;
        this.numberOfCreaturesRead = numberOfCreaturesRead;
        this.allCreaturesValid = allCreaturesValid;
    }

    /*
    InvalidTWDInitialFileException(boolean allCreaturesValid){
        this.allCreaturesValid = allCreaturesValid;
    }

    InvalidTWDInitialFileException(String linhaErro){
        this.linhaErro = linhaErro;
    }

    InvalidTWDInitialFileException(int numberOfCreaturesRead){
        this.numberOfCreaturesRead = numberOfCreaturesRead;
    } */

    public boolean validNrOfCreatures() {
        return this.numberOfCreaturesRead;
    }

    public boolean validCreatureDefinition(){
        return allCreaturesValid;
    }

    public String getErroneousLine(){
        return this.linhaErro;
    }


}
