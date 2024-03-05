package common.network.responses;


//collectionManager.collectionType(), collectionManager.collectionSize(), lastSaveTimeString, lastInitTimeString

import common.models.Dragon;
import common.utils.Commands;

public class InfoResponse extends Response {
    public final String collectionType;
    public final int collectionSize;
    public final String lastSaveTimeString;
    public final String lastInitTimeString;


    public InfoResponse(String collectionType, int collectionSize, String lastSaveTimeString, String lastInitTimeString, String error) {
        super(Commands.INFO, error);
        this.collectionType = collectionType;
        this.collectionSize = collectionSize;
        this.lastSaveTimeString = lastSaveTimeString;
        this.lastInitTimeString = lastInitTimeString;
    }
}