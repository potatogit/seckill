package com.potato.Exception;

import com.potato.dto.SeckillExecution;

public class RepeatSeckillException extends SeckillException {
    public RepeatSeckillException(String message, Throwable cause) {
        super(message, cause);
    }

    public RepeatSeckillException(String message) {

        super(message);
    }
}
