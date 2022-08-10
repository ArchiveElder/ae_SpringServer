package com.ae.ae_SpringServer.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResultResponse<T>  {
    private T data;
}
