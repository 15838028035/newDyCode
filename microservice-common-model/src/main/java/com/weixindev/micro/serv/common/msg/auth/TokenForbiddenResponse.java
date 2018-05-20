package com.weixindev.micro.serv.common.msg.auth;

import com.weixindev.micro.serv.common.constant.RestCodeConstants;
import com.weixindev.micro.serv.common.msg.BaseResponse;

public class TokenForbiddenResponse  extends BaseResponse {
    public TokenForbiddenResponse(String message) {
        super(RestCodeConstants.TOKEN_FORBIDDEN_CODE, message);
    }
}
