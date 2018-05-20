package com.weixindev.micro.serv.common.msg.auth;

import com.weixindev.micro.serv.common.constant.RestCodeConstants;
import com.weixindev.micro.serv.common.msg.BaseResponse;

public class TokenErrorResponse extends BaseResponse {
    public TokenErrorResponse(String message) {
        super(RestCodeConstants.TOKEN_ERROR_CODE, message);
    }
}
