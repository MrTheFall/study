package common.utils;

import java.io.Serializable;

public enum AccessType implements Serializable {
    WRITE,
    READ,
    READ_WRITE,
    NONE
}
