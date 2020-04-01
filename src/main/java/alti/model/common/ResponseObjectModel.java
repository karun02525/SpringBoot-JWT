package alti.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseObjectModel {
    private boolean status;
    private String message;
    private Object data;
}
