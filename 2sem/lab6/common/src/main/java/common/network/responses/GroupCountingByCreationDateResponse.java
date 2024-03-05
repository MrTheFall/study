package common.network.responses;

import common.models.Dragon;
import common.utils.Commands;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class GroupCountingByCreationDateResponse extends Response {
        public final Map<LocalDateTime, List<Dragon>> groups;

    public GroupCountingByCreationDateResponse(Map<LocalDateTime, List<Dragon>> groups, String error) {
        super(Commands.SHOW, error);
        this.groups = groups;
    }
}
