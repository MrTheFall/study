package common.network.requests;

import common.utils.Commands;

public class GroupCountingByCreationDateRequest extends Request{
    public GroupCountingByCreationDateRequest() {
        super(Commands.GROUP_COUNTING_BY_CREATION_DATE);
    }
}
